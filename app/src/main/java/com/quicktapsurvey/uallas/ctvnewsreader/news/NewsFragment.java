package com.quicktapsurvey.uallas.ctvnewsreader.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quicktapsurvey.uallas.ctvnewsreader.R;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsFragment extends Fragment implements NewsContract.View{

    private NewsPresenter presenter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView newsListView;
    private NewsListAdapter newsListAdapter;
    private List<News> newsList;
    private TextView noNewsToShow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsPresenter(this, getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        swipeRefresh = rootView.findViewById(R.id.swiperefresh);
        newsListView = rootView.findViewById(R.id.news_list);
        noNewsToShow = rootView.findViewById(R.id.tv_no_news);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews(true);
            }
        });

        newsList = new ArrayList<>();
        newsListAdapter = new NewsListAdapter(newsList, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        newsListView.setLayoutManager(layoutManager);
        newsListView.setAdapter(newsListAdapter);

        return rootView;
    }

    @Override
    public void onRefreshList(List<News> news) {
        newsList.clear();
        newsList.addAll(news);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showEmptyResults() {
        noNewsToShow.setVisibility(View.VISIBLE);
        newsListView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyResults() {
        noNewsToShow.setVisibility(View.GONE);
        newsListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openNewsDetails(News item) {
        presenter.markNewsAsRead(item.getId());

        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        intent.putExtra("news", item);
        getActivity().startActivity(intent);
    }

    @Override
    public void goToTop() {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(0);

        newsListView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.start();
    }
}
