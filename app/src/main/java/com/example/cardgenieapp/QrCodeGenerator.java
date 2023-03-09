package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardgenieapp.model.SignupModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;



public class QrCodeGenerator extends AppCompatActivity {
    private TextView uidtxt;
    private ImageView qrcodeview;
    private Button savemyqr_btn,shareqr_btn;
    FirebaseUser user;
    DatabaseReference reference;
    String userId;
    BitmapDrawable drawable;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    LinearLayout qrcodelayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_qr_code_generator);
        qrcodeview = findViewById(R.id.qrcode);
        uidtxt = findViewById(R.id.qrcodeuid);
        savemyqr_btn = findViewById(R.id.savemyqrbtn);
        qrcodelayout = findViewById(R.id.qrlayout);
        shareqr_btn = findViewById(R.id.sharemyqrbtn);

        //generate qr
        generateQr();

        //save qr
        savemyqr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage();
            }
        });

        //share qr
       shareqr_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               shareImage();
           }
       });

    }

    //share image methods
    private void shareImage() {
        qrcodelayout.setDrawingCacheEnabled(true);
        qrcodelayout.buildDrawingCache();
        qrcodelayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bmp = qrcodelayout.getDrawingCache();
        share(bmp);
    }

    private void share(Bitmap bmp){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root+"/Download/CardGenieAppQRs");
        String filename = "MyUniBizqr.jpg";
        File myfile = new File(file,filename);
        if(myfile.exists()){
            myfile.delete();
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(myfile);
            bmp.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            //Toast.makeText(this,"Image Saved!",Toast.LENGTH_SHORT).show();
            myfile.setReadable(true,true);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(myfile));
            intent.setType("image/*");
            startActivity(Intent.createChooser(intent,"Share via :"));
            qrcodelayout.setDrawingCacheEnabled(false);
        }catch (Exception e){
            Log.d("error : ",e.toString());
            Toast.makeText(this,"Error : "+e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    //save image methods
    private void saveImage() {
        qrcodelayout.setDrawingCacheEnabled(true);
        qrcodelayout.buildDrawingCache();
        qrcodelayout.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bmp = qrcodelayout.getDrawingCache();
        save(bmp);
    }

    private void save(Bitmap bmp) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File file = new File(root+"/Download/CardGenieAppQRs");
        String filename = "MyUniBizqr.jpg";
        File myfile = new File(file,filename);
        if(myfile.exists()){
            myfile.delete();
        }
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(myfile);
            bmp.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(this,"Image Saved!",Toast.LENGTH_SHORT).show();
            qrcodelayout.setDrawingCacheEnabled(false);

        }catch (Exception e){
            Toast.makeText(this,"Error : "+e.toString(),Toast.LENGTH_SHORT).show();

        }



    }

    //set and generate qr methods
    private void setQr(String uid) {
        qrgEncoder = new QRGEncoder(uid, null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.WHITE);
        qrgEncoder.setColorWhite(Color.BLACK);
        qrcodeview.setImageBitmap(qrgEncoder.getBitmap());

    }

    private void generateQr() {
        //for greeting user on home page
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserLoginDetails");
        userId = user.getUid();
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SignupModel userDetails = snapshot.getValue(SignupModel.class);
                if(user != null){
                    String uid = userDetails.getUid();
                    uidtxt.setText(uid);
                    setQr(uid);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QrCodeGenerator.this,"Something wrong happenned !",Toast.LENGTH_LONG).show();
            }
        });

    }

}