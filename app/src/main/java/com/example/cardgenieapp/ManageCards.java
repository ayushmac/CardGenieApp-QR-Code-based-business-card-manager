package com.example.cardgenieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;

public class ManageCards extends AppCompatActivity {
    MaterialButton manageSocialMediaBtn,manageGamingBtn,managePersonalBtn,manageBusinessBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Manage Cards" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_add_card_24);
        setContentView(R.layout.activity_manage_cards);
        manageSocialMediaBtn = findViewById(R.id.manage_social_media_btn);
        manageGamingBtn = findViewById(R.id.manage_gaming);
        managePersonalBtn = findViewById(R.id.manage_personal);
        manageBusinessBtn = findViewById(R.id.manage_business);

        manageSocialMediaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageCards.this,ManageSocialMedia.class));
            }
        });

        manageGamingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageCards.this,ManageGamingCard.class));
            }
        });

        managePersonalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageCards.this,ManagePersonalCard.class));
            }
        });

        manageBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageCards.this,ManageBusinessCard.class));
            }
        });





    }
}