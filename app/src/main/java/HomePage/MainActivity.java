package HomePage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vnpt.mydailyfitness.HustCare.Adapter.AllDayAdapter;
import com.vnpt.mydailyfitness.HustCare.Calories.DBAdapter;
import com.vnpt.mydailyfitness.HustCare.Calories.DBSetupInsert;
import com.vnpt.mydailyfitness.HustCare.Calories.FoodFragment;
import com.vnpt.mydailyfitness.HustCare.Calories.FragmentActivity;
import com.vnpt.mydailyfitness.HustCare.Calories.Fragment_Calculate;
import com.vnpt.mydailyfitness.HustCare.Calories.ProfileFragment;
import com.vnpt.mydailyfitness.HustCare.Calories.SignUp;
import com.vnpt.mydailyfitness.HustCare.Database.DbOperation;
import com.vnpt.mydailyfitness.HustCare.Exercise.Fragment_Workout;
import com.vnpt.mydailyfitness.HustCare.Exercise.MainExerciseActivity;
import com.vnpt.mydailyfitness.HustCare.Exercise.WorkoutData;
import com.vnpt.mydailyfitness.HustCare.Introduction.OnBoardingActivity;
import com.vnpt.mydailyfitness.HustCare.Login.LoginActivity;
import com.vnpt.mydailyfitness.HustCare.Utils.Constants;
import com.vnpt.mydailyfitness.R;

import java.util.ArrayList;
import java.util.List;

import Reminder.CalculatorFragment;
import Reminder.FragmentReminder;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    Button btnSignOut;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar mToolbar;
    private DbOperation mDbOperation;
    private ArrayList<String> arrayList;
    private List<WorkoutData> workoutDataList;
    private AllDayAdapter allDayAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView1,textView2;
    private MainExerciseActivity mainExerciseActivity;
    private ImageView userImage;
    private TextView userEmail,userName;

    private static final int FRAGMENT_HOME=1;
    private static final int FRAGMENT_WORKOUT=2;
    private static final int FRAGMENT_CALCULATOR=3;
    private static final int FRAGMENT_REMINDER=4;
    private static final int FRAGMENT_NUTRITION=5;

    private int currentFragment=FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        this.drawerLayout=findViewById(R.id.drawer_layout);
        this.bottomNavigationView=findViewById(R.id.nav_view);
        this.mToolbar=initToolbar();
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.navigationView=findViewById(R.id.nav_views);
        navigationView.setNavigationItemSelectedListener(this);
        this.arrayList = new ArrayList<>();
        this.progressBar=findViewById(R.id.progress);
        this.textView1=findViewById(R.id.daysLeft);
        this.textView2=findViewById(R.id.percentScore);
        this.recyclerView=findViewById(R.id.workout_rcl);
        this.userEmail=navigationView.getHeaderView(0).findViewById(R.id.user_email);
        this.userName=navigationView.getHeaderView(0).findViewById(R.id.user_name);
        this.userImage=navigationView.getHeaderView(0).findViewById(R.id.user_image);
        showUserInformation();


        DBAdapter db = new DBAdapter(this);
        db.open();
        int numberRows = db.count("food");

        if(numberRows < 1){
            // Run setup
            Toast.makeText(this, "Loading setup...", Toast.LENGTH_LONG).show();
            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllCategories();
            setupInsert.insertAllFood();
            Toast.makeText(this, "Setup completed!", Toast.LENGTH_LONG).show();

        }

        /* Check if there is user in the user table */
        // Count rows in user table
        numberRows = db.count("users");
        db.close();

        //Toast.makeText(this,"datava", Toast.LENGTH_SHORT).show();

        if(numberRows < 1){
            // Sign up
            // Toast.makeText(this, "You are only few fields away from signing up...", Toast.LENGTH_LONG).show();

            Intent i = new Intent(MainActivity.this, SignUp.class);
            startActivity(i);
        }




        for(int i=1;i <= Constants.TOTAL_DAYS;i++){
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("Day ");
            stringBuilder.append(i);
            this.arrayList.add(stringBuilder.toString());
        }
        this.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String str="";
                switch(item.getItemId()){
                    case R.id.navigation_home:
                        mToolbar.setTitle(getString(R.string.app_name));
                        MainActivity.this.openHomeFragment();
                        break;
                    case R.id.navigation_workout:
                        mToolbar.setTitle("Your Workouts");
                        MainActivity.this.openWorkoutFragment();
                        break;
                    case R.id.action_cal:
                        mToolbar.setTitle("Calculator");
                        MainActivity.this.openCalculatorFragment();
                        break;
                    case R.id.navigation_remider:
                        mToolbar.setTitle("Reminder");
                        MainActivity.this.openReminderFragment();
                        break;
                    case R.id.action_food:
                        mToolbar.setTitle("Your Nutrition");
                        startActivity(new Intent(MainActivity.this, FragmentActivity.class));
                        break;
                }
                return true;
            }
        });
        replaceFragment(new MainFragment(MainActivity.this));

    }
    private void showUserInformation(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            if(name==null){
                userEmail.setVisibility(View.GONE);
            } else {
                userName.setVisibility(View.VISIBLE);
                userName.setText(name);
            }
            userEmail.setText(email);
            Glide.with(this).load(photoUrl).error(R.drawable.avatar_circle_default).into(userImage);

        }
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            MainActivity.this.finish();
        }
        super.onBackPressed();
    }

    private androidx.appcompat.widget.Toolbar initToolbar() {
        androidx.appcompat.widget.Toolbar toolbar2=findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar2);
        return toolbar2;
    }
    public void openHomeFragment(){
        if(currentFragment!=FRAGMENT_HOME){
            replaceFragment(new MainFragment(MainActivity.this));
            currentFragment=FRAGMENT_HOME;
        }
    }
    public void openWorkoutFragment(){
        if(currentFragment!=FRAGMENT_WORKOUT){
            replaceFragment(new Fragment_Workout());
            currentFragment=FRAGMENT_WORKOUT;
        }
    }
    public void openCalculatorFragment(){
        if(currentFragment!=FRAGMENT_CALCULATOR){
            replaceFragment(new Fragment_Calculate());
            currentFragment=FRAGMENT_CALCULATOR;
        }
    }
    public void openReminderFragment(){
        if(currentFragment!=FRAGMENT_REMINDER){
            replaceFragment(new FragmentReminder());
            currentFragment=FRAGMENT_REMINDER;
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment,fragment);
        transaction.commit();
    }
    public void openRestartDialog(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_restart);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else{
            dialog.setCancelable(false);
        }
        TextView cancelDialog=dialog.findViewById(R.id.cancel_dialog);
        TextView okDialog= dialog.findViewById(R.id.ok_dialog);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.restartExercise();
                dialog.dismiss();
                mainExerciseActivity.finish();

            }
        });
        dialog.show();

    }
    public void restartExercise(){
        this.mDbOperation=new DbOperation(this);
        for(int i=0;i<30;i++){
            String str=this.arrayList.get(i);
            this.mDbOperation.updateExcProgress(str,0.0f);
            this.mDbOperation.updateExcCounter(str,0);
        }
        List<WorkoutData> list=this.workoutDataList;
        if(list != null){
            list.clear();
        }
        this.workoutDataList=this.mDbOperation.getAllDayData();
        this.allDayAdapter = new AllDayAdapter(this, this.workoutDataList);
        this.recyclerView.setAdapter(this.allDayAdapter);
        this.progressBar.setProgress(0);
        this.textView2.setText("0%");
        TextView textView = this.textView1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.TOTAL_DAYS);
        stringBuilder.append("Days left");
        textView.setText(stringBuilder.toString());
    }


    public void clear() {
        int size = workoutDataList.size();
        workoutDataList.clear();
        allDayAdapter.notifyItemRangeRemoved(0,size);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;
        if(id==R.id.restart_progress){
            openRestartDialog(Gravity.CENTER);

        } else if(id==R.id.user_profile){
            Intent intent=new Intent(this, ProfileFragment.class);
            startActivity(intent);
            MainActivity.this.finish();

        } else if(id==R.id.sign_out){
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}