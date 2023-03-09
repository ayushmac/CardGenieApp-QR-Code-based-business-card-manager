package com.example.cardgenieapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cardgenieapp.SignUpScreen;
import com.example.cardgenieapp.model.PersonalCardDetailsModel;
import com.example.cardgenieapp.model.SignupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpScreen extends AppCompatActivity {
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
    ActivityResultLauncher<String> cropImage;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("Signup Page");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up_screen);

        signupbtn = findViewById(R.id.signup_btn);
        have_acc_already = findViewById(R.id.loginheretxt);
        profile_pic = findViewById(R.id.profile_pic);

        usrnametxt = findViewById(R.id.signup_nicknamefield);
        fullnametxt = findViewById(R.id.signup_fullnamefield);
        emailtxt = findViewById(R.id.signup_emailfield);
        passwordtxt = findViewById(R.id.signup_pswdfield);
        phonetxt = findViewById(R.id.signup_phonefield);
        phonetxt2 = findViewById(R.id.signup_phonefield2);
        bloodtypedrop = findViewById(R.id.bloodtype);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_view_bloodtype,items);
        bloodtypedrop.setAdapter(adapterItems);
        bloodtypedrop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               String item = adapterView.getItemAtPosition(i).toString();
               //Toast.makeText(getApplicationContext(),"Item : "+item,Toast.LENGTH_SHORT).show();

            }
        });
        birth_date = findViewById(R.id.dob);

        birth_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });



        cropImage = registerForActivityResult(new ActivityResultContracts.GetContent(),result->{
            Intent intent = new Intent(SignUpScreen.this.getApplicationContext(),UcropperActivity.class);
            intent.putExtra("SendImageData",result.toString());
            startActivityForResult(intent,100);
        });

        have_acc_already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this,LoginScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        });

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePermission();
            }
        });



        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToFirebase(uri);
            }
        });

    }

    private void showDatePickerDialog() {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Birth Date").build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birth_date.setText(""+materialDatePicker.getHeaderText());
            }
        });
        materialDatePicker.show(getSupportFragmentManager(),"TAG");
    }


    private void uploadToFirebase(Uri uri) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading : ");
        dialog.show();
        mauth = FirebaseAuth.getInstance();
        String email = emailtxt.getText().toString().trim();
        String password = passwordtxt.getText().toString().trim();
        mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String fullname = fullnametxt.getText().toString().trim();
                    String username = usrnametxt.getText().toString().trim();
                    String email = emailtxt.getText().toString().trim();
                    String pswd = passwordtxt.getText().toString().trim();
                    String phone = phonetxt.getText().toString().trim();
                    String phone2 = phonetxt2.getText().toString().trim();
                    String birth_datetxt = birth_date.getText().toString().trim();
                    String b_grp = bloodtypedrop.getText().toString().trim();
                    StorageReference fileRef = reference.child(uid+"."+getFilesExtention(uri));

                    fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //insert operation
                                    PersonalCardDetailsModel UserDetails2 = new PersonalCardDetailsModel(username,fullname,phone,phone2,birth_datetxt,b_grp,email,uri.toString());
                                    FirebaseDatabase.getInstance().getReference("PersonalCardDetails").child(uid+"personal").setValue(UserDetails2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpScreen.this,"Personal Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                                                Toast.makeText(SignUpScreen.this,"User registered successfully !",Toast.LENGTH_LONG).show();
                                                //progressupload.setVisibility(View.INVISIBLE);
                                            }
                                            else{
                                                //Toast.makeText(SignUpScreen.this,"User registration unsuccessful !",Toast.LENGTH_LONG).show();
                                                //progressupload.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });

                                    SignupModel UserDetails = new SignupModel(uid,uri.toString(),username,fullname,phone,phone2,birth_datetxt,b_grp,email,pswd);
                                    FirebaseDatabase.getInstance().getReference("UserLoginDetails").child(uid).setValue(UserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(SignUpScreen.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                                                fullnametxt.setText("");
                                                usrnametxt.setText("");
                                                passwordtxt.setText("");
                                                phonetxt.setText("");
                                                emailtxt.setText("");
                                                birth_date.setText("");
                                                bloodtypedrop.setText("");
                                                dialog.dismiss();
                                                fullnametxt.setEnabled(true);
                                                usrnametxt.setEnabled(true);
                                                passwordtxt.setEnabled(true);
                                                emailtxt.setEnabled(true);
                                                birth_date.setEnabled(true);
                                                bloodtypedrop.setEnabled(true);
                                                profile_pic.setImageResource(R.drawable.profileicon);

                                                Toast.makeText(SignUpScreen.this,"User registered successfully !",Toast.LENGTH_LONG).show();
                                                //progressupload.setVisibility(View.INVISIBLE);
                                            }
                                            else{
                                                Toast.makeText(SignUpScreen.this,"User registration unsuccessful !",Toast.LENGTH_LONG).show();
                                                //progressupload.setVisibility(View.INVISIBLE);
                                            }
                                        }
                                    });

                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100 * snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                            dialog.setMessage("Uploading : "+progress+"%");
                            fullnametxt.setEnabled(false);
                            usrnametxt.setEnabled(false);
                            passwordtxt.setEnabled(false);
                            emailtxt.setEnabled(false);
                            birth_date.setEnabled(false);
                            bloodtypedrop.setEnabled(false);
                            //progressupload.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpScreen.this,"Image Upload Failed !!",Toast.LENGTH_LONG).show();
                        }
                    });



                    Snackbar.make(findViewById(R.id.signupscreenlayout),"User has now been authenticated",Snackbar.LENGTH_LONG).show();

                }
                else{
                    Snackbar.make(findViewById(R.id.signupscreenlayout),"Couldn't authenticate user as email and password entered is already used by another user!",Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void ImagePermission() {
        Dexter.withContext(SignUpScreen.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                cropImage.launch("image/*");
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(SignUpScreen.this,"Permission Denied !",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 101 ){
            String result = data.getStringExtra("CROP");
            uri = data.getData();
            if(result!=null){
                uri = Uri.parse(result);
            }
            profile_pic.setImageURI(uri);
        }
    }

    private String getFilesExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}