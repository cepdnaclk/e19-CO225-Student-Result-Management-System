package com.example.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InquiryViewHolder extends RecyclerView.ViewHolder {

    TextView sender, title, body;

    public InquiryViewHolder(@NonNull View itemView) {
        super(itemView);

        sender = itemView.findViewById(R.id.sender);
        title = itemView.findViewById(R.id.title);
        body = itemView.findViewById(R.id.body);
    }
}
