package com.mgbachi_ugo.chatbot.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mgbachi_ugo.chatbot.R;
import com.mgbachi_ugo.chatbot.model.MessageMutator;


import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    public static final int RECEIVED = 1;
    public static final int SENT = 2;

    private List<Integer> viewTypeList;
    private List<MessageMutator> messageList;

    public RecyclerViewAdapter(List<Integer> viewTypeList, List<MessageMutator> messageList) {
        this.viewTypeList = viewTypeList;
        this.messageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.received_message_layout,null);
            return new ReceivedViewHolder(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_message_layout,null);
            return new SentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int viewType = viewTypeList.get(position);
        MessageMutator messageMutator = messageList.get(position);
        if(viewType == RECEIVED){
            ReceivedViewHolder receivedViewHolder = (ReceivedViewHolder) holder;
            receivedViewHolder.receivedView.setText(messageMutator.getMessage());
        }else {
            SentViewHolder sentViewHolder = (SentViewHolder) holder;
            sentViewHolder.sentView.setText(messageMutator.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return viewTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewTypeList.get(position);

    }
}
