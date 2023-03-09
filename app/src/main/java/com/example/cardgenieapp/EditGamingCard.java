package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cardgenieapp.model.GamingCardDetailsModel;
import com.example.cardgenieapp.model.SocialMediaDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditGamingCard extends AppCompatActivity {

    TextInputEditText bio_edittext,insta_link_edittxt, facebook_link_edittxt,  youtube_link_edittxt, twitch_link_edittxt,discord_link_edittxt;
    Button AddGamingBtn;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Gaming_Card_Details");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid()+"gaming";
    String bio_txt,insta_link_txt, facebook_link_txt, youtube_link_txt, twitch_link_txt, discord_link_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Update Gaming Card Details" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_edit_note_24);
        setContentView(R.layout.activity_edit_gaming_card);


        insta_link_edittxt = findViewById(R.id.add_insta_link);
        facebook_link_edittxt = findViewById(R.id.add_facebook_link);
        twitch_link_edittxt = findViewById(R.id.add_twitch_link);
        youtube_link_edittxt = findViewById(R.id.add_youtube_link);
        discord_link_edittxt = findViewById(R.id.add_discord_link);
        AddGamingBtn = findViewById(R.id.add_gaming_card_details_btn);
        bio_edittext = findViewById(R.id.add_bio);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GamingCardDetailsModel userDetails = snapshot.getValue(GamingCardDetailsModel.class);
                if(user != null){
                    String bio = userDetails.getBio();
                    String insta = userDetails.getInsta_link();
                    String facebook_link = userDetails.getFacebook_link();
                    String youtube_link = userDetails.getYoutube_link();
                    String twitch_link = userDetails.getTwitch_link();
                    String discord_link = userDetails.getDiscord_link();
                    bio_edittext.setText(bio);
                    insta_link_edittxt.setText(insta);
                    facebook_link_edittxt.setText(facebook_link);
                    youtube_link_edittxt.setText(youtube_link);
                    twitch_link_edittxt.setText(twitch_link);
                    discord_link_edittxt.setText(discord_link);
                }
                else{
                    startActivity(new Intent(EditGamingCard.this,ManageGamingCard.class));
                    Toast.makeText(EditGamingCard.this,"No Details Available please Add Details first!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditGamingCard.this,"No Details Available please Add Details first!",Toast.LENGTH_LONG).show();
            }
        });

        AddGamingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoDatabase();
            }
        });


    }

    void addtoDatabase() {
        bio_txt = bio_edittext.getText().toString().trim();
        insta_link_txt = insta_link_edittxt.getText().toString().trim();
        facebook_link_txt =facebook_link_edittxt.getText().toString().trim();
        youtube_link_txt=youtube_link_edittxt.getText().toString().trim();
        twitch_link_txt=twitch_link_edittxt.getText().toString().trim();
        discord_link_txt=discord_link_edittxt.getText().toString().trim();

        GamingCardDetailsModel gaming = new GamingCardDetailsModel(bio_txt,insta_link_txt,facebook_link_txt,youtube_link_txt,twitch_link_txt,discord_link_txt);
        reference.child(userId).setValue(gaming).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditGamingCard.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent( EditGamingCard.this,ManageGamingCard.class));
                    finish();
                }
                else{
                    Toast.makeText(EditGamingCard.this,"Details Upload Unsuccessful :( ",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}