package com.ceyentra.tattle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ceyentra.tattle.adaptor.NewsFeedRecyclerAdaptor;
import com.ceyentra.tattle.model.NewsFeed;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerView;
    private NewsFeedRecyclerAdaptor recyclerAdaptor;
    private RecyclerView.LayoutManager newsFeedLayout;


    private ArrayList<NewsFeed> newsFeedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context= this;

        setSampleNews();
        configureRecyclerView();
    }

    private void configureRecyclerView(){

        recyclerView = (RecyclerView) findViewById(R.id.postRecyclerview);
        newsFeedLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(newsFeedLayout);
        recyclerAdaptor = new NewsFeedRecyclerAdaptor(newsFeedList,context);
        recyclerView.setAdapter(recyclerAdaptor);
        recyclerView.scrollToPosition(newsFeedList.size()-1);

    }

    private void setSampleNews(){
        NewsFeed newsFeed = new NewsFeed();
        newsFeed.setProfileName("Binura Salindra");
        newsFeed.setNoOfLikes(100);
        newsFeed.setPostLacation("Pettah, Sri Lanka");
        newsFeed.setNoOfShares(11);
        newsFeed.setPostTime("2 hrs ago");
        newsFeed.setPostDescription("Respect yourself.\n" +
                "Respect others.\n" +
                "Respect your environment.\n" +
                "Please! Put your litter in the bin.\n");
        newsFeed.setPostImageUrl(R.drawable.ic_pettah);
        newsFeed.setProfileImgUrl(R.drawable.ic_binura);
       // newsFeed.setProfileImgUrl();

        NewsFeed newsFeed1 = new NewsFeed();
        newsFeed1.setProfileName("Sandun Dilhan");
        newsFeed1.setNoOfLikes(17);
        newsFeed1.setPostLacation("Maradana,Sri Lanka");
        newsFeed1.setNoOfShares(2);
        newsFeed1.setPostTime("8 hrs ago");
        newsFeed1.setPostDescription("Do not throw yourself into Destruction with your own hands.");
        newsFeed1.setPostImageUrl(R.drawable.ic_maradana);
        newsFeed1.setProfileImgUrl(R.drawable.ic_sandun);

        NewsFeed newsFeed2 = new NewsFeed();
        newsFeed2.setProfileName("Tharindu");
        newsFeed2.setNoOfLikes(170);
        newsFeed2.setPostLacation("Anuradapura,SriLanka");
        newsFeed2.setNoOfShares(1);
        newsFeed2.setPostTime("4 hrs ago");
        newsFeed2.setPostDescription("Heritage!");
        newsFeed2.setPostImageUrl(R.drawable.ic_anuradapura);
        newsFeed2.setProfileImgUrl(R.drawable.ic_tharindu);

        NewsFeed newsFeed3 = new NewsFeed();
        newsFeed3.setProfileName("Anonymous");
        newsFeed3.setNoOfLikes(200);
        newsFeed3.setPostLacation("Kollupitiya,SriLanka");
        newsFeed3.setNoOfShares(9);
        newsFeed3.setPostTime("5 hrs ago");
        newsFeed3.setPostDescription("A bus stops on a pedestrian crossing to pick up a passenger.");
        newsFeed3.setPostImageUrl(R.drawable.ic_kollupitiya);
        newsFeed3.setProfileImgUrl(R.drawable.ic_profile);

        NewsFeed newsFeed4 = new NewsFeed();
        newsFeed4.setProfileName("Janitha Danajaya");
        newsFeed4.setNoOfLikes(100);
        newsFeed4.setPostLacation("Colombo Central Bus Stand");
        newsFeed4.setNoOfShares(19);
        newsFeed4.setPostTime("9 hrs ago");
        newsFeed4.setPostDescription("Had to wait 2 hours in this queue");
        newsFeed4.setPostImageUrl(R.drawable.ic_colombo);
        newsFeed4.setProfileImgUrl(R.drawable.ic_janitha);

        NewsFeed newsFeed5 = new NewsFeed();
        newsFeed5.setProfileName("Anonymous");
        newsFeed5.setNoOfLikes(122);
        newsFeed5.setPostLacation("Galle, Sri Lanka");
        newsFeed5.setNoOfShares(13);
        newsFeed5.setPostTime("9 hrs ago");
        newsFeed5.setPostDescription("Chance takers are accident makers");
        newsFeed5.setPostImageUrl(R.drawable.ic_galle);
        newsFeed5.setProfileImgUrl(R.drawable.ic_profile);

        NewsFeed newsFeed6 = new NewsFeed();
        newsFeed6.setProfileName("Sachin Thmalsha");
        newsFeed6.setNoOfLikes(197);
        newsFeed6.setPostLacation("Piliyandala, Sri Lanka");
        newsFeed6.setNoOfShares(10);
        newsFeed6.setPostTime("9 hrs ago");
        newsFeed6.setPostDescription("Residents walk along a flooded road in Piliyandala");
        newsFeed6.setPostImageUrl(R.drawable.ic_piliyandala);
        newsFeed6.setProfileImgUrl(R.drawable.ic_thamalsha);

        NewsFeed newsFeed7 = new NewsFeed();
        newsFeed7.setProfileName("Denver");
        newsFeed7.setNoOfLikes(230);
        newsFeed7.setPostLacation("Nelum Pokuna Mahinda Rajapaksa Theatre");
        newsFeed7.setNoOfShares(25);
        newsFeed7.setPostTime("9 hrs ago");
        newsFeed7.setPostDescription("Without music, life would be a mistake.");
        newsFeed7.setPostImageUrl(R.drawable.ic_nelum);
        newsFeed7.setProfileImgUrl(R.drawable.ic_denver);

        newsFeedList = new ArrayList<>();
        newsFeedList.add(newsFeed7);
        newsFeedList.add(newsFeed6);
        newsFeedList.add(newsFeed5);
        newsFeedList.add(newsFeed4);
        newsFeedList.add(newsFeed3);
        newsFeedList.add(newsFeed2);
        newsFeedList.add(newsFeed1);
        newsFeedList.add(newsFeed);
    }


}
