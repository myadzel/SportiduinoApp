package org.sportiduino.app;

import static org.sportiduino.app.ViewUtils.initCaretEndOnFocus;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceDialogFragmentCompat;

public class ChipDataUrlPreferenceDialog extends PreferenceDialogFragmentCompat {

    private static final String SAVE_STATE_TEXT = "ChipDataUrlPreferenceDialogFragment.text";
    private EditText chipDataUrl;

    public static ChipDataUrlPreferenceDialog newInstance(String key) {
        final ChipDataUrlPreferenceDialog
                fragment = new ChipDataUrlPreferenceDialog();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    private ChipDataUrlPreference getChipDataUrlPreference() {
        return (ChipDataUrlPreference) getPreference();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putCharSequence(SAVE_STATE_TEXT, chipDataUrl.getText().toString());
    }

    @Override
    protected void onBindDialogView(@NonNull View view) {
        super.onBindDialogView(view);

        chipDataUrl = view.findViewById(R.id.pref_chip_data_url);

        initCaretEndOnFocus(chipDataUrl);
    }

    @Nullable
    @Override
    protected View onCreateDialogView(@NonNull Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.preference_chip_data_url, null);

        chipDataUrl = view.findViewById(R.id.pref_chip_data_url);

        ChipDataUrlPreference preference = getChipDataUrlPreference();

        chipDataUrl.setText(preference.getValue());

        return view;
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            String value = chipDataUrl.getText().toString();

            ChipDataUrlPreference preference = getChipDataUrlPreference();

            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }
}
