package com.quicktapsurvey.uallas.ctvnewsreader.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quicktapsurvey.uallas.ctvnewsreader.R;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Uallas on 29/03/2018.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;
    private NewsContract.View viewNews;

    public NewsListAdapter(List<News> newsList, Context context, NewsContract.View viewNews) {
        this.newsList = newsList;
        this.context = context;
        this.viewNews = viewNews;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public CardView cvItem;
        public TextView tvTitle;
        public ImageView ivImage;
        public TextView tvDate;
        public TextView tvDescription;
        public ImageView ivVisualized;

        public ViewHolder(View itemView) {
            super(itemView);
            cvItem = itemView.findViewById(R.id.cv_item);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivVisualized = itemView.findViewById(R.id.iv_visualized);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        final ViewHolder holder = new ViewHolder(itemView);

        holder.cvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewNews.openNewsDetails(newsList.get(holder.getAdapterPosition()));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final News news = newsList.get(position);

        holder.tvTitle.setText(news.getTitle());
        if(news.getRead()) {
            holder.ivVisualized.setImageResource(R.drawable.ic_eye_outline_grey600_24dp);
        } else {
            holder.ivVisualized.setImageResource(R.drawable.ic_eye_black_24dp);
        }
        holder.ivImage.setContentDescription(news.getImageDescription());
        if(news.getImage().isEmpty()) {
            holder.ivImage.setImageResource(R.drawable.logo);
        } else {
            Glide.with(context).load(news.getImage()).into(holder.ivImage);
        }
        holder.tvDescription.setText(news.getDescription());
        final SimpleDateFormat timeFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm");
        holder.tvDate.setText(timeFormatter.format(news.getDate()));

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
