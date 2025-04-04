package com.example.cosc341_buddycart;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.OnMapReadyCallback;


import java.util.List;

// Followed, adapted, and heavily modified sendbird's awesome tutorial: https://sendbird.com/developer/tutorials/android-chat-tutorial-building-a-messaging-ui

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private static final int VIEW_TYPE_MESSAGE_MAP = 3;
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
        String mapUserId = "3";
        String currentUserId = "2";//FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (message.getSenderId().equals(mapUserId)) {
            // If msg is a map msg
            return VIEW_TYPE_MESSAGE_MAP;
        } else if (message.getSenderId().equals(currentUserId)) {
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
        if (viewType == VIEW_TYPE_MESSAGE_MAP) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bubble_maps, parent, false);
            return new MapMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bubble_sender, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_bubble_reciever, parent, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = messageList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_MAP:
                ((MapMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
                break;
        }
    }


    private class MapMessageHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {
        TextView messageText, timeText;
        MapView mapView;
        GoogleMap googleMap;
        LatLng buddyLocation;
        LatLng remoteLocation;

        MapMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageMap);
            timeText = itemView.findViewById(R.id.timeStampMap);
            mapView = itemView.findViewById(R.id.mapView);

            // Fill the MapView
            if (mapView != null) {
                mapView.onCreate(null);
                mapView.getMapAsync(this);
            }
        }

        @Override
        public void onMapReady(GoogleMap map) {
            googleMap = map;

            googleMap.addMarker(new MarkerOptions().position(buddyLocation));

            // User location marker should look distinct (blue marker)
            googleMap.addMarker(new MarkerOptions().position(remoteLocation).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            // Move camera to buddy
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(buddyLocation, 15));

            // Draws line to show buddy and remote shopper distance
            PolylineOptions polylineOptions = new PolylineOptions().add(buddyLocation, remoteLocation).width(5).color(Color.GREEN);
            googleMap.addPolyline(polylineOptions);

        }


        void bind(Message message) {

            messageText.setText(message.getMessage());
            timeText.setText(android.text.format.DateFormat.format("hh:mm", message.getTimestamp()));
            buddyLocation = new LatLng(message.getBuddyLat(), message.getBuddyLng());
            remoteLocation = new LatLng(message.getRemoteLat(), message.getRemoteLng());

            if (mapView != null) {
                // Resumes map when opening again
                mapView.onResume();
            }
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
            timeText.setText(android.text.format.DateFormat.format("hh:mm", message.getTimestamp()));
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
            timeText.setText(android.text.format.DateFormat.format("hh:mm", message.getTimestamp()));
            nameText.setText(message.getSenderName());
        }
    }
}


