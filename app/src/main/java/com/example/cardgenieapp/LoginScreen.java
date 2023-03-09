package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {
    private TextView donthaveacc;
    FirebaseUser user;
    FirebaseAuth mAuth;
    Button loginbtn;
    private TextInputEditText email,pswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        donthaveacc =findViewById(R.id.dont_have_accnt);
        loginbtn = findViewById(R.id.loginbtn);
        email = findViewById(R.id.emailfield);
        pswd = findViewById(R.id.pswdfield);

        donthaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this,SignUpScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt = email.getText().toString().trim();
                String pswdtxt = pswd.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(emailtxt,pswdtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginScreen.this,"Login Success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginScreen.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }
}