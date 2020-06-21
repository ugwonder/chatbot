package com.mgbachi_ugo.chatbot.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mgbachi_ugo.chatbot.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}

class ReceivedViewHolder extends ViewHolder{
    TextView receivedView;
    public ReceivedViewHolder(@NonNull View itemView) {
        super(itemView);
        receivedView = itemView.findViewById(R.id.leftMessage);
    }
}

class SentViewHolder extends ViewHolder{
    TextView sentView;
    public SentViewHolder(@NonNull View itemView) {
        super(itemView);
        sentView = itemView.findViewById(R.id.rightMessage);
    }
}