package com.vnpt.mydailyfitness.HustCare.Helper;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public final  class CustomDialog implements  MaterialDialog.SingleButtonCallback {
    public static final  CustomDialog customDialog = new CustomDialog();

    private CustomDialog() {
    }
    @Override
    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
        dialog.dismiss();
    }
}
