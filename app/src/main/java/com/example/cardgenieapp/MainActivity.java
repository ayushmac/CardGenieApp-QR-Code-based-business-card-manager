package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardgenieapp.model.SignupModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Button searchusers,edit_btn,scanqrcode_btn,generateqr_btn,manage_digi_c_btn;
    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    TextView welcomemsg;
    CircleImageView welprofilepic;
    public static TextView Qruidfetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >"+"&nbsp Home" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_home_24);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        searchusers = findViewById(R.id.view_recent_users);
        mAuth = FirebaseAuth.getInstance();
        welcomemsg = findViewById(R.id.welcomemsg);
        welprofilepic = findViewById(R.id.welcome_profile_logo);
        edit_btn = findViewById(R.id.edit_profile_btn);
        scanqrcode_btn = findViewById(R.id.scanqr_btn);
        generateqr_btn = findViewById(R.id.shareqrbtn);
        manage_digi_c_btn = findViewById(R.id.manage_digi_cards_btn);


        //move to editpage
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UpdateProfile.class));
            }
        });

        //for scanning qrcode
        scanqrcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,ScanQrCodeActivity.class));
            }
        });


        //search users
        searchusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchUsers.class));
            }
        });

        //for generating and sharing qr code
        generateqr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,QrCodeGenerator.class));
            }
        });

        manage_digi_c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ManageCards.class));
            }
        });

        //for greeting user on home page
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
        userId = user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignupModel userDetails = snapshot.getValue(SignupModel.class);
                if(user != null){
                    String usrname = userDetails.getUsername();
                    String imagelink = userDetails.getProfile_pic_url();
                    Picasso.get().load(imagelink).into(welprofilepic);
                    welcomemsg.setText(usrname);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Something wrong happenned !",Toast.LENGTH_LONG).show();
            }
        });

    }

    //hamburger menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutopt:
                new AlertDialog.Builder(MainActivity.this).setTitle("Log Out").setMessage("Are you sure you want to log out ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        startActivity(new Intent(MainActivity.this,WelcomeScreen.class));
                        finish();
                    }
                }).setNegativeButton("No",null).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}