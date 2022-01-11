package org.sportiduino.app;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import org.sportiduino.app.databinding.FragmentWriteCardBinding;
import org.sportiduino.app.sportiduino.Card;
import org.sportiduino.app.sportiduino.CardMifareClassic;
import org.sportiduino.app.sportiduino.CardMifareUltralight;
import org.sportiduino.app.sportiduino.CardType;
import org.sportiduino.app.sportiduino.Util;

public class FragmentWriteCard extends NfcFragment {
    private FragmentWriteCardBinding binding;
    private byte[] password = {0, 0, 0};

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentWriteCardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textViewInfo.setText("Bring card...");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String passwordStr = sharedPref.getString("password", new Password().toString());
        Password password = Password.fromString(passwordStr);
        this.password = new byte[]{
            (byte) password.getValue(2),
            (byte) password.getValue(1),
            (byte) password.getValue(0)
        };
    }

    @Override
    public void onNewTagDetected(Tag tag) {
        String[] techList = tag.getTechList();
        Card card = null;
        for (String s : techList) {
            if (s.equals(MifareClassic.class.getName())) {
                card = new CardMifareClassic(MifareClassic.get(tag));
            } else if (s.equals(MifareUltralight.class.getName())) {
                card = new CardMifareUltralight(MifareUltralight.get(tag));
            }
            if (card != null) {
                card.type = CardType.MASTER_GET_STATE;
                new WriteCardTask(card, setText, password).execute();
                break;
            }
        }
    }

    public Util.Callback setText = (str) -> binding.textViewInfo.setText(str);
}
