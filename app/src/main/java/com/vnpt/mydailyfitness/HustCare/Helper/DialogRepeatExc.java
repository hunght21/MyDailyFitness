package com.vnpt.mydailyfitness.HustCare.Helper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.vnpt.mydailyfitness.HustCare.Exercise.Workout;

/* compiled from: lambda */
public final /* synthetic */ class DialogRepeatExc implements MaterialDialog.SingleButtonCallback {


    private final /* synthetic */ Workout workoutActivity;
    private final /* synthetic */ int b;

    public /* synthetic */ DialogRepeatExc(Workout workoutActivity, int i) {
        this.workoutActivity = workoutActivity;
        this.b = i;
    }

    public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.workoutActivity.repeatExcInform(this.b, materialDialog, dialogAction);
    }
}
