package com.point2points.fishregister.QRScanner.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.point2points.fishregister.R;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Edwin on 4/2/2018.
 */

public class FormatSelectorDialogFragment extends DialogFragment {
    public interface FormatSelectorDialogListener {
        public void onFormatSaved(ArrayList<Integer> selectedIndices);
    }

    private ArrayList<Integer> mSelectedIndices;
    private FormatSelectorDialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static FormatSelectorDialogFragment newInstance(FormatSelectorDialogListener listener, ArrayList<Integer> selectedIndices) {
        FormatSelectorDialogFragment fragment = new FormatSelectorDialogFragment();
        if (selectedIndices == null) {
            selectedIndices = new ArrayList<Integer>();
        }
        fragment.mSelectedIndices = new ArrayList<Integer>(selectedIndices);
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mSelectedIndices == null || mListener == null) {
            dismiss();
            return null;
        }

        String[] formats = new String[ZXingScannerView.ALL_FORMATS.size()];
        boolean[] checkedIndices = new boolean[ZXingScannerView.ALL_FORMATS.size()];
        int i = 0;
        for (BarcodeFormat format : ZXingScannerView.ALL_FORMATS) {
            formats[i] = format.toString();
            if (mSelectedIndices.contains(i)) {
                checkedIndices[i] = true;
            } else {
                checkedIndices[i] = false;
            }
            i++;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Set dialog title
        builder.setTitle(R.string.choose_formats)
                .setMultiChoiceItems(formats, checkedIndices, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b) {
                            //If user checked the item. add it to the selected item
                            mSelectedIndices.add(i);
                        } else if (mSelectedIndices.contains(i)) {
                            mSelectedIndices.remove(mSelectedIndices.indexOf(i));
                        }
                    }
                })
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mListener != null) {
                            mListener.onFormatSaved(mSelectedIndices);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
