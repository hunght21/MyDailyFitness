package com.vnpt.mydailyfitness.HustCare.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import HomePage.MainActivity;
import com.vnpt.mydailyfitness.R;

public class LoginActivity extends AppCompatActivity implements myLogin {
    private static final String TAG = "LoginActivity";
    public static final int RC_SIGN_IN = 147;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView imageView;
    private EditText edtEmail,edtPassword;
    private Button btnLogin;
    private LoginButton fbLoginButton;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private CallbackManager fbCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_login);
        initUi();
        initListener();
        imageView.setOnClickListener(view -> onRegisterClick(imageView));
    }
    public void initListener() {
        btnLogin.setOnClickListener(view -> onClickLogin());
    }
    @SuppressLint("SetTextI18n")
    private void onClickLogin(){
                String strEmail=edtEmail.getText().toString().trim();
                String strPassword=edtPassword.getText().toString().trim();
                User user=new User(strEmail,strPassword);
// ...
// Initialize Firebase Auth
                if(user.isValidEmail()&&user.isValidPassword()){
                    mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "Login Success",
                                            Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                   startActivity(intent);
                                   finishAffinity();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Login Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                } else{
                    Toast.makeText(LoginActivity.this, "Email or Password Invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
    public void onRegisterClick(View View){
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
    }
    public void initUi(){
        imageView=findViewById(R.id.image1);
        edtEmail=findViewById(R.id.textEmail);
        edtPassword=findViewById(R.id.textPassword);
        btnLogin=findViewById(R.id.btn_login);
    }

}