package org.sportiduino.app;

import static org.sportiduino.app.sportiduino.Util.getUrlWithoutUserInfo;
import static org.sportiduino.app.sportiduino.Util.getUserInfoFromUrl;
import static org.sportiduino.app.sportiduino.Util.isValidUrl;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.sportiduino.app.databinding.FragmentReadCardBinding;
import org.sportiduino.app.sportiduino.Card;
import org.sportiduino.app.sportiduino.CardAdapter;
import org.sportiduino.app.sportiduino.CardMifareClassic;
import org.sportiduino.app.sportiduino.CardMifareUltralight;
import org.sportiduino.app.sportiduino.ReadWriteCardException;
import org.sportiduino.app.sportiduino.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FragmentReadCard extends NfcFragment implements IntentReceiver {
    private FragmentReadCardBinding binding;
    private View currentView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentReadCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.currentView = view;

        binding.textViewNfcInfo.setText(R.string.bring_card);
    }

    @Override
    public void onNewTagDetected(Tag tag) {
        StringBuilder tagInfo = new StringBuilder();
        byte[] tagId = tag.getId();
        for (byte b : tagId) {
            tagInfo.append(Integer.toHexString(b & 0xFF)).append(" ");
        }
        String tagIdStr = tagInfo.toString();
        binding.textViewTagId.setText(String.format(getString(R.string.tag_id_s), tagIdStr));
        binding.layoutTagInfo.setVisibility(View.VISIBLE);

        String[] techList = tag.getTechList();
        CardAdapter adapter = null;
        for (String s : techList) {
            if (s.equals(MifareClassic.class.getName())) {
                adapter = new CardMifareClassic(MifareClassic.get(tag));
            } else if (s.equals(MifareUltralight.class.getName())) {
                NtagAuthKey authKey = NtagAuthKeyManager.getAuthKey(requireActivity());
                adapter = new CardMifareUltralight(MifareUltralight.get(tag), authKey);
            }
            if (adapter != null) {
                new ReadCardTask(adapter).execute();
                break;
            }
        }
    }

    class ReadCardTask extends AsyncTask<Void, Void, Boolean> {
        CardAdapter cardAdapter;
        Card card;
        byte[][] buffer;

        ReadCardTask(CardAdapter adapter) {
            this.cardAdapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            binding.textViewTagType.setText("");
            binding.textViewInfo.setVisibility(View.GONE);
            binding.textViewInfo.setText("");
            binding.textViewNfcInfo.setText(R.string.reading_card_dont_remove_it);
            binding.progressBar.setVisibility(View.VISIBLE);

            binding.textViewRequestInfo.setVisibility(View.GONE);
            binding.textViewRequestInfo.setText("");
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean status = true;
            try {
                cardAdapter.connect();
                card = Card.detectCard(cardAdapter);
                buffer = card.read();
            } catch (ReadWriteCardException e) {
                status = false;
            } finally {
                cardAdapter.close();
            }
            return status;
        }

        private void handleChipDataUrl() {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getContext()));
            String chipDataUrl = sharedPref.getString("chip_data_url", null);

            if (isValidUrl(chipDataUrl)) {
                binding.textViewRequestInfo.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));

                JSONObject data = new JSONObject();
                try {
                    data.put("data", card.parseData(buffer));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getUrlWithoutUserInfo(chipDataUrl), data, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.textViewRequestInfo.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        binding.textViewRequestInfo.setText(error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Accept", "application/json");

                        String credentials = getUserInfoFromUrl(chipDataUrl);
                        if (credentials != null) {
                            String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                            headers.put("Authorization", auth);
                        }

                        return headers;
                    }
                };

                requestQueue.add(jsonObjectRequest);
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewRequestInfo.setVisibility(View.GONE);
            if (result && buffer != null) {
                binding.textViewTagType.setText(String.format(getString(R.string.tag_type_s), card.adapter.tagType.name()));
                binding.textViewInfo.setVisibility(View.VISIBLE);
                binding.textViewInfo.setText(card.parseData(buffer));
                binding.textViewNfcInfo.setText(Util.ok(getString(R.string.card_read_successfully), currentView));

                handleChipDataUrl();
            } else {
                binding.textViewNfcInfo.setText(Util.error(getString(R.string.reading_card_failed), currentView));
            }
        }
    }
}
