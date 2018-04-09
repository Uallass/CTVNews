package com.quicktapsurvey.uallas.ctvnewsreader.data.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Xml;

import com.quicktapsurvey.uallas.ctvnewsreader.data.local.controller.NewsDao;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;
import com.quicktapsurvey.uallas.ctvnewsreader.news.NewsContract;
import com.quicktapsurvey.uallas.ctvnewsreader.utils.XMLUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsRequestTask extends AsyncTask {

    private NewsContract.Presenter presenter;
    private String stringUrl;
    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 20000;
    private static final int CONNECTION_TIMEOUT = 20000;
    private NewsDao dbController;

    public NewsRequestTask(NewsContract.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.dbController = new NewsDao(context);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String stringUrl = (String) params[0];
        InputStream inputStream = null;
        try {
            inputStream = getInputStreamFromUrl(stringUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<News> newsFromRemote = parseNewsXml(inputStream);

        // it will insert in the database just the news that aren't already in the database
        dbController.insertNewNews(newsFromRemote);

        List<News> newsFromTable = dbController.load();

        return newsFromTable;
    }

    public InputStream getInputStreamFromUrl(String stringUrl) throws IOException {

        InputStream inputStream;
        URL url;

        url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection)
                url.openConnection();
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setReadTimeout(READ_TIMEOUT);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);

        connection.connect();

        inputStream = connection.getInputStream();

        return inputStream;
    }

    public List<News> parseNewsXml(InputStream response) {
        // Parse the XML, find all the <item> and put in a List<News>
        XmlPullParser parser = Xml.newPullParser();
        List<News> listNews = new ArrayList<>();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(response, null);
            parser.nextTag();

            parser.require(XmlPullParser.START_TAG, "", "rss");
            parser.nextTag();
            parser.require(XmlPullParser.START_TAG, "", "channel");

            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the item tag
                if (name.equals("item")) {
                    listNews.add(new News(parser));
                } else {
                    XMLUtil.skip(parser);
                }
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return listNews;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        presenter.onTaskFinished((List<News>) o);

    }
}
