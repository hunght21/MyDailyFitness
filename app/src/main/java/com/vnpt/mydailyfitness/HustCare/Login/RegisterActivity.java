package com.vnpt.mydailyfitness.HustCare.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.vnpt.mydailyfitness.R;

public class RegisterActivity extends AppCompatActivity {
    ImageView imageView;
    EditText edtEmail,edtPassword,edtName,edtMobile;
    Button btnRegister;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        initUi();
        initListener();
        imageView.setOnClickListener(view -> onLoginClick(imageView));
    }
    private void initListener() {
        btnRegister.setOnClickListener(view -> onClickRegister());
    }
    private void onClickRegister(){
        String strEmail=edtEmail.getText().toString().trim();
        String strPassword=edtPassword.getText().toString().trim();
        progressDialog.show();
        User user=new User(strEmail,strPassword);
        if(user.isValidEmail()&&user.isValidPassword()){
            FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this, "SignUp Success",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        } else{
            Toast.makeText(RegisterActivity.this, "Email or Password Invalid",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }
    private void initUi(){
        imageView=findViewById(R.id.image2);
        edtEmail=findViewById(R.id.editTextEmail);
        edtPassword=findViewById(R.id.editTextPassword);
        btnRegister=findViewById(R.id.btn_register);
        progressDialog=new ProgressDialog(this);
    }
}