package com.quicktapsurvey.uallas.ctvnewsreader.news;

import android.content.Context;

import com.quicktapsurvey.uallas.ctvnewsreader.data.local.controller.NewsDao;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;
import com.quicktapsurvey.uallas.ctvnewsreader.data.remote.NewsRequestTask;

import java.util.List;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsPresenter implements NewsContract.Presenter {

    private static final String URL_NEWS = "https://www.ctvnews.ca/rss/ctvnews-ca-top-stories-public-rss-1.822009";

    private NewsContract.View view;
    private Context context;
    private NewsRequestTask newsRequestTask;
    private NewsDao dbDao;

    public NewsPresenter(NewsContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void start() {
        dbDao = new NewsDao(context);
        // first of all it will load the news server
        view.showLoading();
        loadNews(true);
    }

    @Override
    public void loadNews(boolean forceUpdate) {

        // load from repository or get from server
        if(forceUpdate) {
            newsRequestTask = new NewsRequestTask(this, context);
            newsRequestTask.execute(URL_NEWS);
        } else {
            List<News> newsList = dbDao.load();
            view.onRefreshList(newsList);
        }
    }

    @Override
    public void markNewsAsRead(Double id) {
        dbDao.markAsRead(id);
    }

    // Receives the response from the AsyncTask and send it to the database
    // and then send it to the view
    @Override
    public void onTaskFinished(List<News> listNews) {
        if(listNews.size() > 0) {
            view.hideEmptyResults();
        } else {
            view.showEmptyResults();
        }

        view.onRefreshList(listNews);
        view.hideLoading();
    }

}
