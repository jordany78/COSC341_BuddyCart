package com.example.cosc341_buddycart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Followed, adapted, and heavily modified sendbird's awesome tutorial: https://sendbird.com/developer/tutorials/android-chat-tutorial-building-a-messaging-ui

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Message> messageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        String currentUserId = "2";//FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (message.getSenderId().equals(currentUserId)) {
            // If current user sent msg
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If other user sent msg
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_bubble_sender, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_bubble_reciever, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageSender);
            timeText = itemView.findViewById(R.id.timeStampSender);
        }

        void bind(Message message) {

            messageText.setText(message.getMessage());
            timeText.setText(android.text.format.DateFormat.format("hh:mm:ss", message.getTimestamp()));
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageReciever);
            timeText = itemView.findViewById(R.id.timestampReciever);
            nameText = itemView.findViewById(R.id.userNameReciever);
        }

        void bind(Message message) {
            messageText.setText(message.getMessage());
            timeText.setText(android.text.format.DateFormat.format("hh:mm:ss", message.getTimestamp()));
            nameText.setText(message.getSenderName());
        }
    }
}


