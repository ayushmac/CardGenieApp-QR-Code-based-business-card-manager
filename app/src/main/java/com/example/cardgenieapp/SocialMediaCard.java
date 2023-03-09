package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardgenieapp.model.SignupModel;
import com.example.cardgenieapp.model.SocialMediaDetailsModel;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class SocialMediaCard extends AppCompatActivity {
    String uidfetched;
    CircleImageView profilepic;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("User_Social_Media_Details");
    MaterialButton btn_insta_link,btn_facebook_link,btn_reddit_link,btn_twitter_link,btn_linkedin_link, btn_youtube_link,btn_twitch_link,btn_github_link,btn_whatsapp_link,btn_spotify_link,btnshowsocialqr;
    String biotext,insta,facebook_link ,reddit_link,twitter_link,linkedin_link ,youtube_link ,twitch_link , github_link , whatsapp_link , spotify_link ;
    int count =0;
    TextView fullname,bio,biotitle;
    String new_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
       /* getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Social Media Card" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_people_alt_24);*/
        setContentView(R.layout.activity_social_media_card);
        uidfetched = getIntent().getStringExtra("uidkey");
        biotitle = findViewById(R.id.mybiotitle);
        bio = findViewById(R.id.mybiotxt);
        fullname = findViewById(R.id.myname);
        btn_insta_link = findViewById(R.id.insta_link_btn);
        btn_facebook_link = findViewById(R.id.facebook_link_btn);
        btn_github_link = findViewById(R.id.github_link_btn);
        btn_linkedin_link = findViewById(R.id.linkedin_link_btn);
        btn_reddit_link = findViewById(R.id.reddit_link_btn);
        btn_twitch_link = findViewById(R.id.twitch_link_btn);
        btn_twitter_link = findViewById(R.id.twitter_link_btn);
        btn_youtube_link = findViewById(R.id.youtube_link_btn);
        btn_spotify_link = findViewById(R.id.spotify_link_btn);
        btn_whatsapp_link = findViewById(R.id.whataspp_link_btn);
        profilepic = findViewById(R.id.welcome_profile_logo);
        btnshowsocialqr = findViewById(R.id.show_socialqr_btn);

        String uid_trimsocial ="social";
        new_uid = uidfetched.replaceAll(uid_trimsocial, "");

        //display name first
        reference.child(new_uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignupModel userDetails = snapshot.getValue(SignupModel.class);
                String full_nametxt= userDetails.getFull_name();
                String imagelink = userDetails.getProfile_pic_url();
                fullname.setText(full_nametxt);
                Picasso.get().load(imagelink).into(profilepic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //fetching details from database
        reference2.child(new_uid+"social").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SocialMediaDetailsModel userDetails = snapshot.getValue(SocialMediaDetailsModel.class);
                biotext = userDetails.getBio();
                 insta = userDetails.getInsta_link();
                 facebook_link = userDetails.getFacebook_link();
                reddit_link= userDetails.getReddit_link();
                 twitter_link= userDetails.getTwitter_link();
                 linkedin_link = userDetails.getLinkedin_link();
                youtube_link = userDetails.getYoutube_link();
                 twitch_link = userDetails.getTwitch_link();
                 github_link = userDetails.getGithub_link();
                 whatsapp_link = userDetails.getWhatsapp_link();
                 spotify_link = userDetails.getSpotify_link();

                 bio.setText(biotext);

                //for instagram

                if(biotext.isEmpty()){
                    bio.setVisibility(View.GONE);
                    biotitle.setVisibility(View.GONE);
                    count++;
                }
                else{
                    bio.setVisibility(View.VISIBLE);
                    biotitle.setVisibility(View.GONE);
                }
                if(insta.isEmpty()){
                    btn_insta_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_insta_link.setVisibility(View.VISIBLE);
                }

                //for facebook
                if(facebook_link.isEmpty()){
                    btn_facebook_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_facebook_link.setVisibility(View.VISIBLE);
                }

                //for reddit
                if(reddit_link.isEmpty()){
                    btn_reddit_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_reddit_link.setVisibility(View.VISIBLE);
                }

                //for twitter
                if(twitter_link.isEmpty()){
                    btn_twitter_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_twitter_link.setVisibility(View.VISIBLE);
                }

                //for twitch
                if(twitch_link.isEmpty()){
                    btn_twitch_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_twitch_link.setVisibility(View.VISIBLE);
                }

                // for linkedin
                if(linkedin_link.isEmpty()){
                    btn_linkedin_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_linkedin_link.setVisibility(View.VISIBLE);
                }

                //for whatsapp
                if(whatsapp_link.isEmpty()){
                    btn_whatsapp_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_whatsapp_link.setVisibility(View.VISIBLE);
                }

                //for youtube
                if(youtube_link.isEmpty()){
                    btn_youtube_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_youtube_link.setVisibility(View.VISIBLE);
                    count++;
                }

                //for github
                if(github_link.isEmpty()){
                    btn_github_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_github_link.setVisibility(View.VISIBLE);
                }

                //for spotify
                if(spotify_link.isEmpty()){
                    btn_spotify_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_spotify_link.setVisibility(View.VISIBLE);
                }

                if(count == 11){
                    Toast.makeText(SocialMediaCard.this, "Add social media details first !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SocialMediaCard.this,AddSocialMedia.class));
                    finish();
                }
                else{
                    Toast.makeText(SocialMediaCard.this, "Welcome !", Toast.LENGTH_SHORT).show();
                }



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //instagram btn
        btn_insta_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(insta);
            }
        });

        //facebook btn
        btn_facebook_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(facebook_link);
            }
        });

        //reddit btn
        btn_reddit_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(reddit_link);
            }
        });

        //whatsapp btn
        btn_whatsapp_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(whatsapp_link);
            }
        });

        //github btn
        btn_github_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(github_link);
            }
        });

        //youtube
        btn_youtube_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(youtube_link);
            }
        });

        //twitch
        btn_twitch_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(twitch_link);
            }
        });

        //twitter
        btn_twitter_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(twitter_link);
            }
        });

        //spotify
        btn_spotify_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(spotify_link);
            }
        });

        //linkedin
        btn_linkedin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(linkedin_link);
            }
        });

        btnshowsocialqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });



    }

    private void gotoUrl(String link) {
        Uri uri = Uri.parse(link);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void showDialog(){
        Button close;
        ImageView showqr;
        Dialog my_Dialog = new Dialog(this);
        my_Dialog.setContentView(R.layout.customqrpopup);
        close = (Button) my_Dialog.findViewById(R.id.btnqrclose);
        showqr = (ImageView) my_Dialog.findViewById(R.id.id_myqrcode);
        QRGEncoder qrgEncoder = new QRGEncoder(new_uid+"social", null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        showqr.setImageBitmap(qrgEncoder.getBitmap());

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_Dialog.dismiss();
            }
        });

        my_Dialog.show();

    }


}