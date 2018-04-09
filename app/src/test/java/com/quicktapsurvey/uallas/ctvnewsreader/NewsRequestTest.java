package com.quicktapsurvey.uallas.ctvnewsreader;

import android.content.Context;

import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;
import com.quicktapsurvey.uallas.ctvnewsreader.data.remote.NewsRequestTask;
import com.quicktapsurvey.uallas.ctvnewsreader.news.NewsContract;
import com.quicktapsurvey.uallas.ctvnewsreader.news.NewsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
public class NewsRequestTest {

    @Mock
    private NewsContract.View newsView;

    private NewsPresenter newsPresenter;
    private Context context;

    @Before
    public void createPresenter() {
        this.context = mock(Context.class);
        this.newsPresenter = new NewsPresenter(newsView, context);

    }

    @Test
    public void getInputStream() throws Exception {
        NewsRequestTask newsRequestTask = new NewsRequestTask(newsPresenter, context);
        InputStream inputStream = newsRequestTask.getInputStreamFromUrl("https://www.ctvnews.ca/rss/ctvnews-ca-top-stories-public-rss-1.822009");
        assertNotNull(inputStream);

        List<News> newsList = newsRequestTask.parseNewsXml(inputStream);

        assertNotNull(newsList);
        assertTrue(newsList.size() == 10);

    }
}