package com.example.cardgenieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.cardgenieapp.model.SignupModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class SearchUsers extends AppCompatActivity {
    EditText inputsearchuser;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<SignupModel> options;
    FirebaseRecyclerAdapter<SignupModel,MyViewHolder> adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_users);
        reference = FirebaseDatabase.getInstance().getReference().child("UserLoginDetails");
        inputsearchuser = findViewById(R.id.searchuser);
        recyclerView = findViewById(R.id.users);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        LoadData("");

        inputsearchuser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString()!=null){
                    LoadData(s.toString());
                }
                else{
                    LoadData("");
                }
            }
        });


    }

    private void LoadData(String data) {
        Query query = reference.orderByChild("full_name").startAt(data).endAt(data+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<SignupModel>().setQuery(query,SignupModel.class).build();
        adapter = new FirebaseRecyclerAdapter<SignupModel, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull SignupModel model) {
                holder.nametextview.setText(model.getFull_name());
                holder.emailtextview.setText(model.getEmail());
                holder.c_notextview.setText(model.getPhone_no());
                Picasso.get().load(model.getProfile_pic_url()).into(holder.profilepic);
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}