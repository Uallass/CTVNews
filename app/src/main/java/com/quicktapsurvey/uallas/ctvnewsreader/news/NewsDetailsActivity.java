package com.quicktapsurvey.uallas.ctvnewsreader.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.quicktapsurvey.uallas.ctvnewsreader.R;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

/**
 * Created by Uallas on 29/03/2018.
 */

public class NewsDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ProgressBar progressBar;
    private News news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details_activity);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        if(intent != null) {
            news = intent.getParcelableExtra("news");
        } else {
            Toast.makeText(this, getResources().getString(R.string.news_not_found), Toast.LENGTH_LONG);
            onBackPressed();
        }

        progressBar = findViewById(R.id.progress_bar);
        WebView webView = findViewById(R.id.wb_news_details);
        webView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
                progressBar.setProgress(progress);
                if(progress == 100) {
                    progressBar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Error! " + description, Toast.LENGTH_SHORT).show();
            }
        });


        webView.loadUrl(news.getLink());

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = news.getLink();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,news.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.choose_share)));

                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}
