package com.example.myapplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryViewHolder> {
    Context context;
    List<Inquiry> inquiryList;

    public InquiryAdapter(Context context, List<Inquiry> inquiryList) {
        this.context = context;
        this.inquiryList = inquiryList;
    }



    @NonNull
    @Override
    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inquiry_view, parent, false);
        InquiryViewHolder viewHolder = new InquiryViewHolder(view);

        // Set the click listener for the card view
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Inquiry inquiry = inquiryList.get(position);

                    // Create an intent to open the InquiryDetailsActivity
                    Intent intent = new Intent(context, InquiryDetailsActivity.class);
                    intent.putExtra("sender", inquiry.getSender());
                    intent.putExtra("title", inquiry.getTitle());
                    intent.putExtra("body", inquiry.getBody());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this line

                    // Start the activity
                    context.startActivity(intent);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryViewHolder holder, int position) {
        holder.sender.setText(inquiryList.get(position).getSender());
        holder.title.setText(inquiryList.get(position).getTitle());
        holder.body.setText(inquiryList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
        notifyDataSetChanged();
    }
    }
