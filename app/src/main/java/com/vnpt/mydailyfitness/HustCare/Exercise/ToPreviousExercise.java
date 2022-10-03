package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class ToPreviousExercise implements View.OnClickListener {


    private final /* synthetic */ MainExerciseActivity f5a;

    public /* synthetic */ ToPreviousExercise(MainExerciseActivity mainExcerciseActivity) {
        this.f5a = mainExcerciseActivity;
    }

    public final void onClick(View view) {
        this.f5a.previousExercise(view);
    }
}
