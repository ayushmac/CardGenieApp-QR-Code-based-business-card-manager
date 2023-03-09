package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardgenieapp.model.PersonalCardDetailsModel;
import com.example.cardgenieapp.model.SignupModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalId extends AppCompatActivity {
     String uidfetched;
     DatabaseReference reference;
     CircleImageView id_profile_pic;
     ImageView idqrcode;
     TextView fullname,phone,phone2,email,uid,b_grp,Dob;
     QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_personal_id);
        uidfetched = getIntent().getStringExtra("uidkey");

        id_profile_pic = findViewById(R.id.identity_logo);
        fullname = findViewById(R.id.id_name_txt);
        phone = findViewById(R.id.id_phone_txt);
        phone2 =findViewById(R.id.id_phone_txt2);
        email = findViewById(R.id.id_email_txt);
        uid = findViewById(R.id.id_uid_txt);
        Dob = findViewById(R.id.id_date_birth);
        b_grp = findViewById(R.id.bloodgrptxt);
        idqrcode = findViewById(R.id.id_qrcode);

        String uid_trimpersonal= "personal";
        String new_uid = uidfetched.replaceAll(uid_trimpersonal, "");

        reference = FirebaseDatabase.getInstance().getReference("PersonalCardDetails");
        reference.child(new_uid+"personal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalCardDetailsModel userDetails = snapshot.getValue(PersonalCardDetailsModel.class);
                String full_name= userDetails.getFull_name();
                String e_mail= userDetails.getEmail();
                String phone_no= userDetails.getPhone_no();
                String phone_no2= userDetails.getPhone_no2();
                String d_o_b = userDetails.getDate_of_birth();
                String bloodType = userDetails.getBlood_type();
                String imagelink = userDetails.getImg_url();
                Picasso.get().load(imagelink).into(id_profile_pic);
                qrgEncoder = new QRGEncoder(uidfetched+"personal", null, QRGContents.Type.TEXT, 800);
                qrgEncoder.setColorBlack(Color.WHITE);
                qrgEncoder.setColorWhite(Color.BLACK);
                idqrcode.setImageBitmap(qrgEncoder.getBitmap());
                fullname.setText(" "+full_name);
                email.setText(" "+e_mail);
                phone.setText(" +91"+phone_no);
                phone2.setText(" +91"+phone_no2);
                Dob.setText(d_o_b);
                b_grp.setText(bloodType);
                //changed
                uid.setText("UID : "+uidfetched);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}