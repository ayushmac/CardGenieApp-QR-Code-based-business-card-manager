package com.example.cardgenieapp;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    CircleImageView profilepic;
    TextView nametextview,emailtextview,c_notextview;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        profilepic =itemView.findViewById(R.id.profilepicvisit);
        nametextview = itemView.findViewById(R.id.nametext);
        emailtextview = itemView.findViewById(R.id.emailtext);
        c_notextview = itemView.findViewById(R.id.contacttext);
    }
}
