package com.example.cardgenieapp;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPersonalCard extends AppCompatActivity {

    private Button signupbtn;
    TextView have_acc_already;
    FirebaseAuth mauth;
    Uri uri;
    String[] items = {"A+","A-","B+","B-","0+","0-","AB+","AB-"};
    AutoCompleteTextView bloodtypedrop,birth_date;
    ArrayAdapter<String> adapterItems;
    private CircleImageView profile_pic;
    TextInputLayout usrnamelayout,fullnamelayout,emailayout,passwordlayout,phone1layout,phone2layout;
    TextInputEditText usrnametxt,fullnametxt,emailtxt,passwordtxt,phonetxt,phonetxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_card);
    }
}