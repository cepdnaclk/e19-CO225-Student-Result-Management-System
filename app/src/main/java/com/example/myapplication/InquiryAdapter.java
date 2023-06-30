package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
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
        return new InquiryViewHolder(LayoutInflater.from(context).inflate(R.layout.inquiry_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryViewHolder holder, int position) {
    holder.sender.setText(inquiryList.get(position).getSender());
    holder.title.setText(inquiryList.get(position).getTitle());
    holder.body.setText(inquiryList.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        System.out.println(inquiryList.size());
        return inquiryList.size();

    }


    public void setInquiryList(List<Inquiry> inquiryList) {
        this.inquiryList = inquiryList;
        notifyDataSetChanged();
    }
}
