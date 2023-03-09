package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardgenieapp.model.GamingCardDetailsModel;
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

public class GamingCard extends AppCompatActivity {
    String uidfetched;
    CircleImageView profilepic;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("User_Gaming_Card_Details");
    MaterialButton btn_insta_link,btn_facebook_link,btn_youtube_link,btn_twitch_link,btn_discord_link,btn_show_gameqr;
    String biotext,insta,facebook_link ,reddit_link,twitter_link,linkedin_link ,youtube_link ,twitch_link , discord_link , whatsapp_link , spotify_link ;
    int count =0;
    TextView fullname,bio,biotitle,scanmetitle;
    ImageView idqrcode;
    String new_uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        /*
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Gaming Card" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_games_24);*/
        setContentView(R.layout.activity_gaming_card);

        uidfetched = getIntent().getStringExtra("uidkey");
        biotitle = findViewById(R.id.mybiotitle);
        bio = findViewById(R.id.mybiotxt);
        fullname = findViewById(R.id.myname);
        btn_insta_link = findViewById(R.id.insta_link_btn);
        btn_facebook_link = findViewById(R.id.facebook_link_btn);
        btn_discord_link = findViewById(R.id.discord_link_btn);
        btn_twitch_link = findViewById(R.id.twitch_link_btn);
        btn_youtube_link = findViewById(R.id.youtube_link_btn);
        profilepic = findViewById(R.id.welcome_profile_logo);
        btn_show_gameqr = findViewById(R.id.show_gameqr_btn);


        String uid_trimgaming = "gaming";
         new_uid = uidfetched.replaceAll(uid_trimgaming, "");
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
        reference2.child(new_uid+"gaming").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               GamingCardDetailsModel userDetails = snapshot.getValue(GamingCardDetailsModel.class);
                biotext = userDetails.getBio();
                insta = userDetails.getInsta_link();
                facebook_link = userDetails.getFacebook_link();
                youtube_link = userDetails.getYoutube_link();
                twitch_link = userDetails.getTwitch_link();
                discord_link = userDetails.getDiscord_link();

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


                //for twitch
                if(twitch_link.isEmpty()){
                    btn_twitch_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_twitch_link.setVisibility(View.VISIBLE);
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

                //for discord
                if(discord_link.isEmpty()){
                    btn_discord_link.setVisibility(View.GONE);
                    count++;
                }
                else{
                    btn_discord_link.setVisibility(View.VISIBLE);
                }


                if(count == 5){
                    Toast.makeText(GamingCard.this, "Add Gaming Card details first !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(GamingCard.this,AddGamingCard.class));
                    finish();
                }
                else{
                    Toast.makeText(GamingCard.this, "Welcome !", Toast.LENGTH_SHORT).show();
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


        //github btn
        btn_discord_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl(discord_link);
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

        btn_show_gameqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
                /*
                btn_show_gameqr.setVisibility(View.GONE);
                scanmetitle.setVisibility(View.VISIBLE);
                idqrcode.setVisibility(View.VISIBLE);
                QRGEncoder qrgEncoder = new QRGEncoder(new_uid+"gaming", null, QRGContents.Type.TEXT, 800);
                qrgEncoder.setColorBlack(Color.WHITE);
                qrgEncoder.setColorWhite(Color.BLACK);
                idqrcode.setImageBitmap(qrgEncoder.getBitmap());*/
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
        QRGEncoder qrgEncoder = new QRGEncoder(new_uid+"gaming", null, QRGContents.Type.TEXT, 800);
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