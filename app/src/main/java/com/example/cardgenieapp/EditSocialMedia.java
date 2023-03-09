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

public class EditSocialMedia extends AppCompatActivity {
    TextInputEditText bio_edittext,insta_link_edittxt, facebook_link_edittxt, reddit_link_edittxt, twitter_link_edittxt, linkedin_link_edittxt, youtube_link_edittxt, twitch_link_edittxt, github_link_edittxt, whatsapp_link_edittxt, spotify_link_edittxt;
    Button AddSocialBtn,Editbtnvisible;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Social_Media_Details");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid()+"social";
    String bio_txt,insta_link_txt, facebook_link_txt, reddit_link_txt, twitter_link_txt, linkedin_link_txt, youtube_link_txt, twitch_link_txt, github_link_txt, whatsapp_link_txt, spotify_link_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Update Social Media Details" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_edit_note_24);
        setContentView(R.layout.activity_edit_social_media);

        bio_edittext = findViewById(R.id.add_bio);
        insta_link_edittxt = findViewById(R.id.add_insta_link);
        facebook_link_edittxt = findViewById(R.id.add_facebook_link);
        reddit_link_edittxt = findViewById(R.id.add_reddit_link);
        twitch_link_edittxt = findViewById(R.id.add_twitch_link);
        twitter_link_edittxt = findViewById(R.id.add_twitter_link);
        linkedin_link_edittxt = findViewById(R.id.add_linkedin_link);
        youtube_link_edittxt = findViewById(R.id.add_youtube_link);
        github_link_edittxt = findViewById(R.id.add_github_link);
        whatsapp_link_edittxt = findViewById(R.id.add_whatsapp_link);
        spotify_link_edittxt = findViewById(R.id.add_spotify_link);
        AddSocialBtn = findViewById(R.id.add_social_media_details_btn);


        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SocialMediaDetailsModel userDetails = snapshot.getValue(SocialMediaDetailsModel.class);
                if(user != null){
                    String bio = userDetails.getBio();
                    String insta = userDetails.getInsta_link();
                    String facebook_link = userDetails.getFacebook_link();
                    String reddit_link= userDetails.getReddit_link();
                    String twitter_link= userDetails.getTwitter_link();
                    String linkedin_link = userDetails.getLinkedin_link();
                    String youtube_link = userDetails.getYoutube_link();
                    String twitch_link = userDetails.getTwitch_link();
                    String github_link = userDetails.getGithub_link();
                    String whatsapp_link = userDetails.getWhatsapp_link();
                    String spotify_link = userDetails.getSpotify_link();
                    bio_edittext.setText(bio);
                    insta_link_edittxt.setText(insta);
                    facebook_link_edittxt.setText(facebook_link);
                    reddit_link_edittxt.setText(reddit_link);
                    twitter_link_edittxt.setText(twitter_link);
                    linkedin_link_edittxt.setText(linkedin_link);
                    youtube_link_edittxt.setText(youtube_link);
                   twitch_link_edittxt.setText(twitch_link);
                   github_link_edittxt.setText(github_link);
                    whatsapp_link_edittxt.setText(whatsapp_link);
                    spotify_link_edittxt.setText(spotify_link);
                }
                else{
                    startActivity(new Intent(EditSocialMedia.this,ManageSocialMedia.class));
                    Toast.makeText(EditSocialMedia.this,"No Details Available please Add Details first!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditSocialMedia.this,"No Details Available please Add Details first!",Toast.LENGTH_LONG).show();
            }
        });

        AddSocialBtn.setOnClickListener(new View.OnClickListener() {
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
        reddit_link_txt=reddit_link_edittxt.getText().toString().trim();
        twitter_link_txt =twitter_link_edittxt.getText().toString().trim();
        linkedin_link_txt=linkedin_link_edittxt.getText().toString().trim();
        youtube_link_txt=youtube_link_edittxt.getText().toString().trim();
        twitch_link_txt=twitch_link_edittxt.getText().toString().trim();
        github_link_txt=github_link_edittxt.getText().toString().trim();
        whatsapp_link_txt=whatsapp_link_edittxt.getText().toString().trim();
        spotify_link_txt=spotify_link_edittxt.getText().toString().trim();

        SocialMediaDetailsModel socialmedia = new SocialMediaDetailsModel(bio_txt,insta_link_txt,facebook_link_txt,reddit_link_txt,twitter_link_txt,linkedin_link_txt,youtube_link_txt,twitch_link_txt,github_link_txt,whatsapp_link_txt, spotify_link_txt);
        reference.child(userId).setValue(socialmedia).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditSocialMedia.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent( EditSocialMedia.this,ManageSocialMedia.class));
                    finish();
                }
                else{
                    Toast.makeText(EditSocialMedia.this,"Details Upload Unsuccessful :( ",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}