package com.quicktapsurvey.uallas.ctvnewsreader.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.quicktapsurvey.uallas.ctvnewsreader.R;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsActivity extends AppCompatActivity {

    private NewsFragment newsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_activity);

        newsFragment = (NewsFragment) getSupportFragmentManager().findFragmentById(R.id.fl_news_fragment_frame);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ImageView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((NewsContract.View) newsFragment).goToTop();
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if(newsFragment == null) {
            newsFragment = new NewsFragment();

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fl_news_fragment_frame, newsFragment);
            fragmentTransaction.commit();
        }

    }



}
