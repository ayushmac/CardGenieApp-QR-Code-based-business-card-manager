package com.example.cardgenieapp;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.cardgenieapp.model.PersonalCardDetailsModel;
import com.example.cardgenieapp.model.SignupModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfile extends AppCompatActivity {
    String uidfetched;
    CircleImageView update_profile_pic;
    TextInputEditText update_usrname,update_fullname,update_phone1,update_phone2;
    String[] items = {"A+","A-","B+","B-","0+","0-","AB+","AB-"};
    AutoCompleteTextView update_b_grp,update_dob;
    ArrayAdapter<String> adapterItems;
    FirebaseUser user;
    Button upd_btn,upd_profbtn;
    ActivityResultLauncher<String> cropImage;
    StorageReference fileRef;
    Uri uri;
    String email,imagelink,userId,bloodType,d_o_b,phone_no2,phone_no1,full_name,usr_name,pswd;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
    StorageReference reference1 = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(Html.fromHtml("<font color= #ffffff >" + " "+"Edit Profile" + "</font>"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#FF039BE5"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);
        setContentView(R.layout.activity_update_profile);
        FirebaseApp.initializeApp(this);

        update_profile_pic = findViewById(R.id.upd_profile_pic);
        update_usrname = findViewById(R.id.upd_nicknamefield);
        update_fullname = findViewById(R.id.upd_fullnamefield);
        update_phone1 = findViewById(R.id.upd_phonefield);
        update_phone2 = findViewById(R.id.upd_phonefield2);
        update_b_grp = findViewById(R.id.upd_bloodtype);
        upd_btn = findViewById(R.id.updatebtn);
        upd_profbtn = findViewById(R.id.updateprofbtn);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_view_bloodtype,items);
        update_b_grp.setAdapter(adapterItems);
        update_b_grp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_b_grp.setText("");
            }
        });

        update_dob = findViewById(R.id.upd_dob);
        update_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        cropImage = registerForActivityResult(new ActivityResultContracts.GetContent(), result->{
            Intent intent = new Intent(UpdateProfile.this.getApplicationContext(),UcropperActivity.class);
            intent.putExtra("SendImageData",result.toString());
            startActivityForResult(intent,100);
        });

        update_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ImagePermission();
                    upd_profbtn.setVisibility(View.VISIBLE);
            }
        });

        //fetching details from database first
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignupModel userDetails = snapshot.getValue(SignupModel.class);
                if(user != null){
                    usr_name = userDetails.getUsername();
                    full_name= userDetails.getFull_name();
                    phone_no1= userDetails.getPhone_no();
                    phone_no2= userDetails.getPhone_no2();
                    d_o_b = userDetails.getDate_of_birth();
                    bloodType = userDetails.getBlood_type();
                    imagelink = userDetails.getProfile_pic_url();
                    email = userDetails.getEmail();
                    pswd = userDetails.getPassword();
                    Picasso.get().load(imagelink).into(update_profile_pic);
                    update_fullname.setText(full_name);
                    update_usrname.setText(usr_name);
                    update_phone1.setText(phone_no1);
                    update_phone2.setText(phone_no2);
                    update_dob.setText(d_o_b);
                    update_b_grp.setText(bloodType);

                    update_b_grp.setAdapter(adapterItems);
                    update_b_grp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String item = adapterView.getItemAtPosition(i).toString();
                            //Toast.makeText(getApplicationContext(),"Item : "+item,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfile.this,"Something wrong happenned !",Toast.LENGTH_LONG).show();
            }
        });


        upd_profbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   updatePhoto();
            }
        });


        upd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });

    }

    private void updateDetails() {
        final Map<String,Object> map1 = new HashMap<>();
        map1.put("blood_type",update_b_grp.getText().toString().trim());
        map1.put("date_of_birth",update_dob.getText().toString().trim());
        map1.put("full_name",update_fullname.getText().toString().trim());
        map1.put("phone_no",update_phone1.getText().toString().trim());
        map1.put("phone_no2",update_phone2.getText().toString().trim());
        map1.put("username",update_usrname.getText().toString().trim());
        /*
        PersonalCardDetailsModel UserDetails2 = new PersonalCardDetailsModel(usr_name,full_name,phone_no1,phone_no2,d_o_b,bloodType,email,imagelink);
        FirebaseDatabase.getInstance().getReference("PersonalCardDetails").child(userId+"personal").setValue(UserDetails2).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateProfile.this,"Personal Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();

                    //progressupload.setVisibility(View.INVISIBLE);
                }
                else{
                    //Toast.makeText(SignUpScreen.this,"User registration unsuccessful !",Toast.LENGTH_LONG).show();
                    //progressupload.setVisibility(View.INVISIBLE);
                }
            }
        });*/

        reference.child(userId).updateChildren(map1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateProfile.this,"Data Updated Successfully !",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this,"Data Updation Failure !",Toast.LENGTH_LONG).show();
                    }
                });

        final Map<String,Object> map2 = new HashMap<>();
        map2.put("blood_type",update_b_grp.getText().toString().trim());
        map2.put("date_of_birth",update_dob.getText().toString().trim());
        map2.put("email",email);
        map2.put("full_name",update_fullname.getText().toString().trim());
        map2.put("img_url",imagelink);
        map2.put("phone_no",update_phone1.getText().toString().trim());
        map2.put("phone_no2",update_phone2.getText().toString().trim());
        map2.put("username",update_usrname.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference("PersonalCardDetails").child(userId+"personal").updateChildren(map2)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //Toast.makeText(UpdateProfile.this,"Personal Details Also Updated Successfully !",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this,"Data Updation Failure !",Toast.LENGTH_LONG).show();
                    }
                });


    }

    private void updatePhoto() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Updating");
        pd.setMessage("Updating profile photo");
        pd.show();

        //delete previous image
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl(imagelink);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Toast.makeText(getApplicationContext(), "User Previous Profile Image deleted successfully !",Toast.LENGTH_SHORT).show();
            }
        });

        //add image new image
        userId = user.getUid();
        fileRef = reference1.child(userId+"."+getFilesExtention(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //email,imagelink,userId,bloodType,d_o_b,phone_no2,phone_no1,full_name,usr_name,pswd
                        //update_usrname,update_fullname,update_phone1,update_phone2,update_b_grp,update_dob;


                        SignupModel UserDetails = new SignupModel(userId,uri.toString(),usr_name,full_name,phone_no1,phone_no2,d_o_b,bloodType,email,pswd);
                        FirebaseDatabase.getInstance().getReference("UserLoginDetails").child(userId).setValue(UserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(UpdateProfile.this,"Details Uploaded Successfully !!",Toast.LENGTH_LONG).show();
                                    upd_profbtn.setVisibility(View.GONE);
                                }
                                else{
                                    //Toast.makeText(UpdateProfile.this,"User registration unsuccessful !",Toast.LENGTH_LONG).show();
                                    //progressupload.setVisibility(View.INVISIBLE);
                                }
                            }
                        });



                        upd_profbtn.setVisibility(View.GONE);
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Profile photo Updated Successfully !",Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Couldn't Update Profile Photo!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = (100 * snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                pd.setMessage("Updating profile photo : "+progress+"%");
            }
        });

    }

    private void showDatePickerDialog() {
        MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Birth Date").build();
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                update_dob.setText(""+materialDatePicker.getHeaderText());
            }
        });
        materialDatePicker.show(getSupportFragmentManager(),"TAG");
    }

    private void ImagePermission() {
        Dexter.withContext(UpdateProfile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                cropImage.launch("image/*");
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(UpdateProfile.this,"Permission Denied !",Toast.LENGTH_SHORT).show();
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
            update_profile_pic.setImageURI(uri);
        }
    }

    private String getFilesExtention(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}