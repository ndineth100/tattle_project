package com.ceyentra.tattle.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceyentra.tattle.model.ChatMessage;
import com.ceyentra.tattle.adaptor.GroupChatRecyclerAdaptor;
import com.ceyentra.tattle.R;
import com.ceyentra.tattle.TattleApplication;
import com.ceyentra.tattle.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GroupChatActivity extends AbstractActivity {
    //private String username;
    boolean isAnonymous = false;
    boolean anonymousLimitOver = false;
    private RecyclerView recyclerView;
    private GroupChatRecyclerAdaptor recyclerAdaptor;
    private RecyclerView.LayoutManager chatlayoutManager;
    private ImageView imageView;
    private EditText msgTxt;
    private Context context;
    private Socket mSocket;
    public static String mUsername = "laky";
    public static int avatar = 1;
    private ArrayList<ChatMessage> messageList = new ArrayList<>();
    private Boolean isConnected = true;
    private ImageView sticker_btn;
    private TextView text_limit;
    int textCount = 0;
    //private String last_message;

    private int currentPosition = 1;
    private int lastPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        context = this;
        User user = new User();
        mUsername = user.getRandomUserName();
        avatar = user.getRandomAvatar();
        imageView = (ImageView) findViewById(R.id.send_btn);
        msgTxt = (EditText) findViewById(R.id.msgTxt);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!msgTxt.getText().toString().isEmpty()) {
                    sendSMS(msgTxt.getText().toString());
                    msgTxt.setText("");
                }
            }
        });
        text_limit = (TextView) findViewById(R.id.limit_txt);
        sticker_btn = (ImageView) findViewById(R.id.sticker_btn);
        sticker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!anonymousLimitOver) {
                    if (isAnonymous) {
                        isAnonymous = false;
                        sticker_btn.setImageResource(R.drawable.hacker_off);
                        text_limit.setVisibility(View.GONE);
                    } else {
                        isAnonymous = true;
                        sticker_btn.setImageResource(R.drawable.hacker_on);
                        text_limit.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(context, "Your daily limit of anonymous messages is reached.", Toast.LENGTH_LONG).show();
                }
            }
        });


        msgTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && s.length() > 0){
                    text_limit.setText(String.valueOf(s.length())+"/100");
                    if (s.length() > 100) {
                        msgTxt.setText(s.toString().substring(0,100));
                    }
                }
            }
        });
        //initialize();


        initialize();
        if(null!=mUsername) {
            JSONObject userJson = new JSONObject();
            try {
                userJson.put("name", mUsername);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // perform the user login attempt.
            mSocket.emit("add user", userJson);
        }
        configureRecyclerView(messageList);
    }

    private void initialize() {

        TattleApplication app = (TattleApplication) getApplication();
        mSocket = app.getSocket();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("newMessage", onNewMessage);
        mSocket.on("join", onUserJoined);
        mSocket.on("user left", onUserLeft);
        mSocket.on("typing", onTyping);
        mSocket.on("stop typing", onStopTyping);
        mSocket.connect();
    }

    private void configureRecyclerView(ArrayList<ChatMessage> messageList) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        chatlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(chatlayoutManager);
        recyclerAdaptor = new GroupChatRecyclerAdaptor(messageList, this, this);
        recyclerView.setAdapter(recyclerAdaptor);
        chatlayoutManager.scrollToPosition(0);
        recyclerView.setNestedScrollingEnabled(false);
        attachRecycleItemTouchHelper();
    }

    private void configureRecyclerViewWithSearch(ArrayList<ChatMessage> messageList, String searchText) {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        chatlayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(chatlayoutManager);
        recyclerAdaptor = new GroupChatRecyclerAdaptor(messageList, this, this, searchText);
        recyclerView.setAdapter(recyclerAdaptor);
        chatlayoutManager.scrollToPosition(0);
        recyclerView.setNestedScrollingEnabled(true);
    }

    public void sendSMS(String msg) {
        if (msg != null | !msg.isEmpty()) {
            JSONObject messageJson = new JSONObject();
            int type = 0;
            try {
                messageJson.put("text", msg);
                messageJson.put("avatar", avatar);
                if (isAnonymous) {
                    messageJson.put("anonymous", 1);
                    type = 3;
                    anonymousLimitOver = true;
                    isAnonymous = false;
                    sticker_btn.setImageResource(R.drawable.hacker_off);
                    text_limit.setVisibility(View.GONE);
                } else {
                    messageJson.put("anonymous", 0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // perform the sending message attempt.
            mSocket.emit("createMessage", messageJson);
            addMessage(new ChatMessage(type, mUsername, msg, String.valueOf(new Date().getTime()), avatar));
            text_limit.setText("0/100");
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mSocket.emit("disconnect");
        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("newMessage", onNewMessage);
        mSocket.off("join", onUserJoined);
        mSocket.off("user left", onUserLeft);
        mSocket.off("typing", onTyping);
        mSocket.off("stop typing", onStopTyping);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.group_chat_menu, menu);

        //create search view when click the search icon in tool bar
        MenuItem search_item = menu.findItem(R.id.search_chat);

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) search_item.getActionView();
        searchView.setFocusable(false);

        // set white color to search view text and hint text
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text))
                .setTextColor(getResources().getColor(R.color.colorIcon));
        searchView.setQueryHint(Html.fromHtml("<font color = #FFFFFF>" +
                getResources().getString(R.string.text_hint_searchview) + "</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }



    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        if(null!=mUsername) {
                            JSONObject userJson = new JSONObject();
                            try {
                                userJson.put("name", mUsername);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // perform the user login attempt.
                            mSocket.emit("add user", userJson);
                        }
                        Toast.makeText(getApplicationContext(),
                                R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                    Toast.makeText(getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    String createdAt;
                    int avatar;
                    try {
                        username = data.getString("from");
                        message = data.getString("text");
                        createdAt = data.getString("createdAt");
                        avatar = data.getInt("avatar");
                    } catch (JSONException e) {
                        return;
                    }

//                    removeTyping(username);
                    int type = 1;
                    if (username.trim().toLowerCase().equals("anonymous")) type = 3;
                    else if (username.trim().toLowerCase().equals("admin")) type = 2;
                    addMessage(new ChatMessage(type ,username, message, createdAt, avatar));
                }
            });
        }
    };
    private void addMessage(ChatMessage chatMessage) {
        messageList.add(chatMessage);
        recyclerAdaptor.notifyItemInserted(messageList.size() - 1);
        scrollToBottom();
    }

    private void scrollToBottom() {
        recyclerView.scrollToPosition(recyclerAdaptor.getItemCount() - 1);
    }

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        JSONArray messages = data.getJSONArray("messageList");
                        messageList = new ArrayList<>();
                        for (int i = messages.length()-1; i >= 0; i--) {
                            JSONObject messageObject = messages.getJSONObject(i);
                            String message;
                            String createdAt;
                            int avatar;
                            username = messageObject.getString("from");
                            message = messageObject.getString("text");
                            createdAt = messageObject.getString("createdAt");
                            avatar = messageObject.getInt("avatar");
                            int type = 1;
                            if (username.trim().toLowerCase().equals("anonymous")) type = 3;
                            else if (username.trim().toLowerCase().equals("admin")) type = 2;
                            messageList.add(new ChatMessage(type,username, message, createdAt, avatar));
                        }

                        configureRecyclerView(messageList);
                        scrollToBottom();
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    int numUsers;
                    try {
                        username = data.getString("username");
                        numUsers = data.getInt("numUsers");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            mSocket.emit("stop typing");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void attachRecycleItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                recyclerAdaptor.notifyItemRemoved(position);    //item removed from recylcerview
                messageList.remove(position);  //then remove item
                return;
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        ItemTouchHelper.SimpleCallback simpleCallbackright = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition(); //get position which is swipe

                recyclerAdaptor.notifyItemRemoved(position);    //item removed from recylcerview
                messageList.remove(position);  //then remove item
                return;
            }
        };
        ItemTouchHelper itemTouchHelperright = new ItemTouchHelper(simpleCallbackright);
        itemTouchHelperright.attachToRecyclerView(recyclerView);
    }
}

