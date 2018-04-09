package com.quicktapsurvey.uallas.ctvnewsreader;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.quicktapsurvey.uallas.ctvnewsreader.data.local.DBHelper;
import com.quicktapsurvey.uallas.ctvnewsreader.data.local.controller.NewsDao;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class NewsDatabaseTest {

    private Context context;
    private NewsDao dbDao;

    @Before
    public void createDb() {
        context = getTargetContext();
        context.deleteDatabase(DBHelper.DATABASE_NAME);
        dbDao = new NewsDao(context);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        assertEquals("com.quicktapsurvey.uallas.ctvnewsreader", context.getPackageName());
    }

    @Test
    public void loadNews() {
        List<News> newsList = dbDao.load();

        assertNotNull(newsList);
    }

    @Test
    public void loadNewsById() {
        News news = dbDao.loadById(1.0);

        assertNotNull(news);
    }

    @Test
    public void newsIsInTable() {
        boolean isInTable = dbDao.isInTable(1.0);

        assertFalse(isInTable);
    }

    @Test
    public void insertNews() {
        News news = new News();
        news.setId(1.0);
        news.setTitle("Title");
        news.setLink("http://");
        news.setDescription("Desrciption");
        news.setImage("image.jpg");
        news.setImageDescription("ImageDescription");
        news.setCreditLine("João");
        news.setDate(new Date());

        Long result = dbDao.insert(news);

        assertThat(result, Matchers.greaterThan(new Long(0)));
    }

    @Test
    public void updateNews() {
        News news = new News();
        news.setId(1.0);
        news.setTitle("Title");
        news.setLink("http://");
        news.setDescription("Desrciption");
        news.setImage("image.jpg");
        news.setImageDescription("ImageDescription");
        news.setCreditLine("João");
        news.setDate(new Date());

        int result = dbDao.update(news);

        assertThat(result, Matchers.greaterThan(-1));
    }

    @Test
    public void deleteNews() {
        int result = dbDao.delete(1.0);

        assertThat(result, Matchers.greaterThan(-1));
    }

}
