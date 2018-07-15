package com.ceyentra.tattle.adaptor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ceyentra.tattle.R;
import com.ceyentra.tattle.model.ChatMessage;
import com.ceyentra.tattle.model.User;
import com.github.siyamed.shapeimageview.CircularImageView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GroupChatRecyclerAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    ArrayList<ChatMessage> messageList = null;
    Context context = null;
    Activity activity;
    private String searchText="SEARCHTEXT";
    public GroupChatRecyclerAdaptor(ArrayList<ChatMessage> messageList, Context context, Activity activity) {
        this.context = context;
        this.messageList = messageList;
        this.activity = activity;
    }
    public GroupChatRecyclerAdaptor(ArrayList<ChatMessage> messageList, Context context, Activity activity, String searchText) {
        this.context = context;
        this.messageList = messageList;
        this.activity = activity;
        this.searchText=searchText;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = messageList.get(position);
        if (chatMessage != null) {
            return chatMessage.getType();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sent_chat_layout,parent,false);
                GroupChatRecyclerAdaptor.SentRecyclerViewHolder recyclerViewHolder = new GroupChatRecyclerAdaptor.SentRecyclerViewHolder(view, context, messageList);
                return recyclerViewHolder;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_received_layout,parent,false);
                GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder receivedrecyclerViewHolder = new GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder(view, context, messageList);
                return receivedrecyclerViewHolder;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_group_chat_layout,parent,false);
                GroupChatRecyclerAdaptor.InfoRecyclerViewHolder infoRecyclerViewHolder = new GroupChatRecyclerAdaptor.InfoRecyclerViewHolder(view, context, messageList);
                return infoRecyclerViewHolder;
            case 3:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.anonymous_row_layout,parent,false);
                GroupChatRecyclerAdaptor.AnonymousRecyclerViewHolder anonymousRecyclerViewHolder = new GroupChatRecyclerAdaptor.AnonymousRecyclerViewHolder(view, context, messageList);
                return anonymousRecyclerViewHolder;
            default:
                return  null;
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        long val = Long.valueOf(message.getDate());
        Date toDate = new Date(System.currentTimeMillis());
        Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("MMM  dd");
        if (df2.format(date).equals(df2.format(toDate))) {
            df2 = new SimpleDateFormat("HH:mma");
        }else {
            df2 = new SimpleDateFormat("MMM  dd -HH:mma");
        }
        switch (message.getType()) {
            case 0:
                //This if condition check user's search msg contains in chat list and highlight the search text

                ((GroupChatRecyclerAdaptor.SentRecyclerViewHolder)holder).messageTxt.setText(message.getBody());
                ((GroupChatRecyclerAdaptor.SentRecyclerViewHolder)holder).dateTxt.setText(df2.format(date));
                ((GroupChatRecyclerAdaptor.SentRecyclerViewHolder)holder).contactIcon.setImageResource(User.getAvatar(message.avatar));
                ((SentRecyclerViewHolder)holder).card_view.setCardBackgroundColor(Color.parseColor(User.getChatColor(message.avatar)));
                break;
            case 1:
                //This if condition check user's search msg contains in chat list and highlight the search text

                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).messageTxt.setText(message.getBody());
                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).dateTxt.setText(df2.format(date));
                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).addressTxt.setText(message.getAddress());
                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).addressTxt.setTextColor(Color.parseColor(User.getChatColor(message.avatar)));
                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).contactIcon.setImageResource(User.getAvatar(message.avatar));
                ((GroupChatRecyclerAdaptor.ReceivedRecyclerViewHolder)holder).card_view.setCardBackgroundColor(Color.parseColor(User.getChatColor(message.avatar)));

                break;
            case 2:
                ((GroupChatRecyclerAdaptor.InfoRecyclerViewHolder)holder).messageTxt.setText(message.getBody());
                break;
            case 3:
                ((GroupChatRecyclerAdaptor.AnonymousRecyclerViewHolder)holder).messageTxt.setText(message.getBody());
                ((GroupChatRecyclerAdaptor.AnonymousRecyclerViewHolder)holder).dateTxt.setText(df2.format(date));


        }
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
    @Override
    public void onClick(View view) {

    }


    public class ReceivedRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView messageTxt;
        TextView dateTxt;
        TextView addressTxt;
        CircularImageView contactIcon;
        ArrayList<ChatMessage> messageList = null;
        CardView card_view;
        Context context = null;
        public ReceivedRecyclerViewHolder(View itemView, Context context, ArrayList<ChatMessage> messageList) {
            super(itemView);
            this.context =context;
            this.messageList = messageList;
            messageTxt = (TextView) itemView.findViewById(R.id.chatText);
            dateTxt = (TextView) itemView.findViewById(R.id.date_txt);
            addressTxt = (TextView) itemView.findViewById(R.id.address_txt);
            contactIcon = (CircularImageView) itemView.findViewById(R.id.contact_icon_symbol);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

    }

    public class InfoRecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView messageTxt;
        ArrayList<ChatMessage> messageList = null;
        Context context = null;
        public InfoRecyclerViewHolder(View itemView, Context context, ArrayList<ChatMessage> messageList) {
            super(itemView);
            this.context =context;
            this.messageList = messageList;
            messageTxt = (TextView) itemView.findViewById(R.id.info_txt);
        }

    }

    public class SentRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageTxt;
        TextView dateTxt;
        CircularImageView contactIcon;
        ArrayList<ChatMessage> messageList = null;
        Context context = null;
        CardView card_view;
        public SentRecyclerViewHolder(View itemView, Context context, ArrayList<ChatMessage> messageList) {
            super(itemView);
            this.context =context;
            this.messageList = messageList;
            messageTxt = (TextView) itemView.findViewById(R.id.chatText);
            dateTxt = (TextView) itemView.findViewById(R.id.date_txt);
            contactIcon = (CircularImageView) itemView.findViewById(R.id.contact_icon_symbol);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public class AnonymousRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView messageTxt;
        TextView dateTxt;
        ArrayList<ChatMessage> messageList = null;
        Context context = null;
        CardView card_view;
        public AnonymousRecyclerViewHolder(View itemView, Context context, ArrayList<ChatMessage> messageList) {
            super(itemView);
            this.context =context;
            this.messageList = messageList;
            messageTxt = (TextView) itemView.findViewById(R.id.chatText);
            dateTxt = (TextView) itemView.findViewById(R.id.date_txt);
            card_view = (CardView) itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View view) {
        }
    }
}

