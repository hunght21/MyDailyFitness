package HomePage;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.vnpt.mydailyfitness.HustCare.Exercise.MainExerciseActivity;

/* compiled from: lambda */
public final /* synthetic */ class g implements MaterialDialog.SingleButtonCallback {


    private final /* synthetic */ MainExerciseActivity f6a;

    public /* synthetic */ g(MainExerciseActivity mainExcerciseActivity) {
        this.f6a = mainExcerciseActivity;
    }

    public final void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.f6a.a(materialDialog, dialogAction);
    }
}
