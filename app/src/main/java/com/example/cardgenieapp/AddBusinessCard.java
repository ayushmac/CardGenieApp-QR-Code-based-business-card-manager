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

import com.example.cardgenieapp.model.BusinessCardDetailsModel;
import com.example.cardgenieapp.model.SocialMediaDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBusinessCard extends AppCompatActivity {
    TextInputEditText full_nameedittxt,phone_noteditxt,phone_no2edittxt,emailedittxt,designationedittxt,linkedinlinkedittxt,addressedittxt;
    Button AddBusinessBtn,Editbtnvisible;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User_Business_Card_Details");
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userId = user.getUid()+"business";
    String full_nametxt,phone_notxt,phone_no2txt,emailtxt,designationtxt,linkedinlinktxt,addresstxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + "   "+"&nbsp Upload Business Card Details" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        // Set BackgroundDrawable
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        getSupportActionBar().setIcon(R.drawable.ic_baseline_cloud_upload_24);
        setContentView(R.layout.activity_add_business_card);

        full_nameedittxt = findViewById(R.id.add_fullnamefield);
        phone_noteditxt =findViewById(R.id.add_phonefield);
        phone_no2edittxt = findViewById(R.id.add_phonefield2);
        emailedittxt = findViewById(R.id.add_emailfield);
        designationedittxt =findViewById(R.id.add_designation_field) ;
        linkedinlinkedittxt = findViewById(R.id.add_linkedin_link);
        addressedittxt = findViewById(R.id.add_address);

        AddBusinessBtn = findViewById(R.id.add_business_card_btn);

        AddBusinessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoDatabase();
            }
        });

    }

    private void addtoDatabase() {
        full_nametxt = full_nameedittxt.getText().toString().trim();
        phone_notxt = phone_noteditxt.getText().toString().trim();
        phone_no2txt = phone_no2edittxt.getText().toString().trim();
        emailtxt = emailedittxt.getText().toString().trim();
        designationtxt = designationedittxt.getText().toString().trim();
        linkedinlinktxt = linkedinlinkedittxt.getText().toString().trim();
        addresstxt = addressedittxt.getText().toString().trim();


        BusinessCardDetailsModel socialmedia = new BusinessCardDetailsModel(full_nametxt,phone_notxt, phone_no2txt, emailtxt,designationtxt, linkedinlinktxt , addresstxt );
        reference.child(userId).setValue(socialmedia).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddBusinessCard.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent( AddBusinessCard.this,ManageBusinessCard.class));
                    finish();
                }
                else{
                    Toast.makeText(AddBusinessCard.this,"Details Upload Unsuccessful :( ",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}