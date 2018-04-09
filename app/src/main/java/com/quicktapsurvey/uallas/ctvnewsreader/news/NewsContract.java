package com.quicktapsurvey.uallas.ctvnewsreader.news;

import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

import java.util.List;

/**
 * Created by Uallas on 27/03/2018.
 */

public interface NewsContract {

    interface View {

        void onRefreshList(List<News> news);
        void showLoading();
        void hideLoading();
        void showEmptyResults();
        void hideEmptyResults();
        void openNewsDetails(News item);
        void goToTop();
    }

    interface Presenter  {

        void start();
        void loadNews(boolean forceUpdate);
        void markNewsAsRead(Double id);
        void onTaskFinished(List<News> listNews);
    }
}
