package com.example.cardgenieapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ScanQrCodeActivity extends AppCompatActivity {
    private CodeScanner codeScanner;
    static private EditText codeData;
    TextView alreadyqr;
    private CodeScannerView scannerView;
    DatabaseReference reference;
    static String uidfetch;
    private static String foundedval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_scan_qr_code);

        codeData = findViewById(R.id.fetchUid);
        scannerView = findViewById(R.id.scanner_view);
        alreadyqr = findViewById(R.id.alreadyhaveqr);

        //if already having qr select from gallery
        alreadyqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ScanQrCodeActivity.this,OpenQr.class));
                browsephoto();
            }
        });

        //providing permissions to qr scanner
        int PERMISSION_ALL =1;
        String[] PERMISSIONS={
                android.Manifest.permission.CAMERA
        };

        if(!hasPermissions(this,PERMISSIONS)){
            ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSION_ALL);
        }
        else{
            runCodeScanner();
        }

        //listener for hidden edittext
        codeData.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {


            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //checking from database if any user exists
                callDatabase();

                String uidfetchfromedittxt = codeData.getText().toString().trim();

                String findgaming = "gaming";
                boolean gaming = uidfetchfromedittxt.contains(findgaming);

                String findpersonal = "personal";
                boolean personal = uidfetchfromedittxt.contains(findpersonal);

                String findsocial = "social";
                boolean social = uidfetchfromedittxt.contains(findsocial);

                String findbusiness = "business";
                boolean business = uidfetchfromedittxt.contains(findbusiness);

                //after scanning if user exists direct to user's card.
                if(personal){
                    Intent intent = new Intent(ScanQrCodeActivity.this,PersonalId.class);
                    intent.putExtra("uidkey",codeData.getText().toString().trim());
                    startActivity(intent);
                }
                else if(gaming){
                    Intent intent = new Intent(ScanQrCodeActivity.this,GamingCard.class);
                    intent.putExtra("uidkey",codeData.getText().toString().trim());
                    startActivity(intent);
                }
                else if(social){
                    Intent intent = new Intent(ScanQrCodeActivity.this,SocialMediaCard.class);
                    intent.putExtra("uidkey",codeData.getText().toString().trim());
                    startActivity(intent);
                }
                else if(business){
                    Intent intent = new Intent(ScanQrCodeActivity.this,BusinessCard.class);
                    intent.putExtra("uidkey",codeData.getText().toString().trim());
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(ScanQrCodeActivity.this,VisitProfile.class);
                    intent.putExtra("uidkey",codeData.getText().toString().trim());
                    startActivity(intent);
                }

            }
        });



    }

    //browse qr code from gallery
    private void browsephoto() {
        Intent photopicker = new Intent(Intent.ACTION_PICK);
        photopicker.setType("image/*");
        startActivityForResult(photopicker,1000);

    }

    //checking from database
    private void callDatabase() {
        reference = FirebaseDatabase.getInstance().getReference().child("UserLoginDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //int count;
                for(DataSnapshot dataSnapshot1 :snapshot.getChildren()){
                    String value = String.valueOf(dataSnapshot1.child("uid").getValue()).toString().trim();

                    String data = codeData.getText().toString().trim();

                    if(value.equals(data)){
                        foundedval = data;
                        break;
                    }else{
                        continue;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScanQrCodeActivity.this, "User does not exist !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //qr scanner code to decode qr
    public void runCodeScanner(){
        codeScanner = new CodeScanner(this,scannerView);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data1 = result.getText();
                        codeData.setText(data1);
                    }
                });
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions){
        if(context != null && permissions !=null){
            for (String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();

    }

    @Override
    public void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }

    //code to decode qr from image
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                try {
                    Bitmap bMap = selectedImage;
                    String contents = null;
                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Reader reader = new MultiFormatReader();
                    Result result = reader.decode(bitmap);
                    contents = result.getText();
                    Log.i("Founded value : ",contents);

                    if(!contents.isEmpty()){
                        //Toast.makeText(getApplicationContext(),contents,Toast.LENGTH_LONG).show();
                        codeData.setText(contents);
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"no qr found",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ScanQrCodeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Toast.makeText(ScanQrCodeActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
}