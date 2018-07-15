package com.ceyentra.tattle.view;

import android.support.v7.app.AppCompatActivity;

import com.ceyentra.tattle.TattleApplication;

public abstract class AbstractActivity extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        TattleApplication.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TattleApplication.activityPaused();
    }
}
