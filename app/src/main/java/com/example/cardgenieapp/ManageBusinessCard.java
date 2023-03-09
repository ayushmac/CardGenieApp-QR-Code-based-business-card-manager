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
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageBusinessCard extends AppCompatActivity {
    MaterialButton add_details,delete_details,view_details,edit_details;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("User_Business_Card_Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Manage Business Card" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_business_24);
        setContentView(R.layout.activity_manage_business_card);


        add_details = findViewById(R.id.add_business_card_btn);
        edit_details = findViewById(R.id.edit_business_card_btn);
        delete_details = findViewById(R.id.delete_business_ard_btn);
        view_details = findViewById(R.id.view_business_card_btn);
        view_details.setVisibility(View.GONE);

        //edit button visibility
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(userId+"business")) {
                    edit_details.setVisibility(View.VISIBLE);
                    view_details.setVisibility(View.VISIBLE);
                }
                else{
                    edit_details.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //add button visibility
        DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference("User_Business_Card_Details");
        rootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(userId+"business")) {
                    add_details.setVisibility(View.GONE);
                    delete_details.setVisibility(View.VISIBLE);
                }
                else{
                    add_details.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        add_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ManageBusinessCard.this,AddBusinessCard.class));
            }
        });


        edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageBusinessCard.this,EditBusinessCard.class));
            }
        });

        delete_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ManageBusinessCard.this).setTitle("Delete Business Card Details").setMessage("Are you sure you want to delete ?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rootRef.child(userId+"business").removeValue();
                        delete_details.setVisibility(View.GONE);
                        edit_details.setVisibility(View.GONE);
                        view_details.setVisibility(View.GONE);
                        add_details.setVisibility(View.VISIBLE);
                    }
                }).setNegativeButton("No",null).show();

            }
        });

        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageBusinessCard.this,BusinessCard.class);
                intent.putExtra("uidkey",userId);
                startActivity(intent);
            }
        });
    }
}