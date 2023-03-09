package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardgenieapp.model.BusinessCardDetailsModel;
import com.example.cardgenieapp.model.PersonalCardDetailsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessCard extends AppCompatActivity {
    String uidfetched;
    DatabaseReference reference,reference1;
    CircleImageView id_profile_pic;
    ImageView idqrcode;
    TextView fullname,phone,phone2,email,address,linkedinlink,designation;
    QRGEncoder qrgEncoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_personal_id);
        uidfetched = getIntent().getStringExtra("uidkey");
        setContentView(R.layout.activity_business_card);

        id_profile_pic = findViewById(R.id.identity_logo);
        fullname = findViewById(R.id.id_name_txt);
        phone = findViewById(R.id.id_phone_txt);
        phone2 =findViewById(R.id.id_phone_txt2);
        email = findViewById(R.id.id_email_txt);
        address = findViewById(R.id.id_address_txt);
        linkedinlink = findViewById(R.id.id_linkedinlink);
        designation = findViewById(R.id.id_designation_txt);
        idqrcode = findViewById(R.id.id_qrcode);

        String uid_trimbusiness= "business";
        String new_uid = uidfetched.replaceAll(uid_trimbusiness, "");

        reference = FirebaseDatabase.getInstance().getReference("PersonalCardDetails");
        reference.child(new_uid+"personal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PersonalCardDetailsModel userDetails = snapshot.getValue(PersonalCardDetailsModel.class);
                String imagelink = userDetails.getImg_url();
                Picasso.get().load(imagelink).into(id_profile_pic);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference1 = FirebaseDatabase.getInstance().getReference("User_Business_Card_Details");
        reference1.child(new_uid+"business").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               BusinessCardDetailsModel userDetails = snapshot.getValue(BusinessCardDetailsModel.class);
                String full_name= userDetails.getFull_name();
                String e_mail= userDetails.getEmail();
                String phone_no= userDetails.getPhone_no();
                String phone_no2= userDetails.getPhone_no2();
                String designationtxt = userDetails.getDesignation();
                String adddress = userDetails.getAddress();
                String linkedin = userDetails.getLinkedinlink();
                qrgEncoder = new QRGEncoder(uidfetched+"business", null, QRGContents.Type.TEXT, 800);
                qrgEncoder.setColorBlack(Color.WHITE);
                qrgEncoder.setColorWhite(Color.BLACK);
                idqrcode.setImageBitmap(qrgEncoder.getBitmap());
                fullname.setText(" "+full_name);
                email.setText(" "+e_mail);
                phone.setText(" +91"+phone_no);
                phone2.setText(" +91"+phone_no2);
                address.setText(adddress);
                linkedinlink.setText(linkedin);
                designation.setText(designationtxt);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}