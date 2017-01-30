package com.example.leokallo.nopeustesteri;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by leokallo on 1.12.2016.
 */

public class PeliLoppuiDialogFragment extends DialogFragment {

    public interface PeliLoppuiDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    PeliLoppuiDialogListener mListener;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        try {
            mListener = (PeliLoppuiDialogListener)a;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + "tulee toteuttaa PeliLoppuiDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.game_lost)
                .setNegativeButton(R.string.close_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogNegativeClick(PeliLoppuiDialogFragment.this);
                    }
                })
                .setPositiveButton(R.string.new_game, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListener.onDialogPositiveClick(PeliLoppuiDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
