package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.cardgenieapp.model.SignupModel;
import com.example.cardgenieapp.model.SocialMediaDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AddSocialMedia extends AppCompatActivity {
    TextInputEditText bio_edittext,insta_link_edittxt, facebook_link_edittxt, reddit_link_edittxt, twitter_link_edittxt, linkedin_link_edittxt, youtube_link_edittxt, twitch_link_edittxt, github_link_edittxt, whatsapp_link_edittxt, spotify_link_edittxt;
    Button AddSocialBtn,Editbtnvisible;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Social_Media_Details");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid()+"social";
    String bio_txt,insta_link_txt, facebook_link_txt, reddit_link_txt, twitter_link_txt, linkedin_link_txt, youtube_link_txt, twitch_link_txt, github_link_txt, whatsapp_link_txt, spotify_link_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Upload Social Media Details" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_cloud_upload_24);
        setContentView(R.layout.activity_add_social_media);
        //Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();
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
        bio_edittext = findViewById(R.id.add_bio);



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
                    Toast.makeText(AddSocialMedia.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent( AddSocialMedia.this,ManageSocialMedia.class));
                    finish();
                }
                else{
                    Toast.makeText(AddSocialMedia.this,"Details Upload Unsuccessful :( ",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}