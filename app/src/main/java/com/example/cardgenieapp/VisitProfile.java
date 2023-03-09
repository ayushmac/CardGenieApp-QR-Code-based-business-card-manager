package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardgenieapp.model.SignupModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reference;
    CircleImageView visitprofilepic;
    TextView usernametxt;
    Button personalcardbtn,socialmediacardbtn,gamingcardbtn,businesscardbtn;
    String uidfetch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_visit_profile);

        visitprofilepic = findViewById(R.id.visit_welcome_profile_logo);
        usernametxt = findViewById(R.id.visit_username_txt);
        personalcardbtn = findViewById(R.id.personal_card_btn);
        socialmediacardbtn = findViewById(R.id.social_media_card_btn);
        gamingcardbtn = findViewById(R.id.gaming_card_btn);
        businesscardbtn = findViewById(R.id.business_card_btn);
        uidfetch = getIntent().getStringExtra("uidkey");
        /*
        */

        //for greeting user on home page
        reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
        reference.child(uidfetch).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignupModel userDetails = snapshot.getValue(SignupModel.class);
                String usrname = userDetails.getUsername();
                usernametxt.setText(usrname);
                String imagelink = userDetails.getProfile_pic_url();
                Picasso.get().load(imagelink).into(visitprofilepic);
                Toast.makeText(getApplicationContext(), "User found!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        personalcardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitProfile.this,PersonalId.class);
                intent.putExtra("uidkey",uidfetch+"personal");
                startActivity(intent);
                finish();
            }
        });

        socialmediacardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitProfile.this,SocialMediaCard.class);
                intent.putExtra("uidkey",uidfetch);
                startActivity(intent);
            }
        });

        gamingcardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitProfile.this,GamingCard.class);
                intent.putExtra("uidkey",uidfetch);
                startActivity(intent);
            }
        });

        businesscardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitProfile.this,BusinessCard.class);
                intent.putExtra("uidkey",uidfetch+"business");
                startActivity(intent);
            }
        });








    }
}