package com.quicktapsurvey.uallas.ctvnewsreader.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.quicktapsurvey.uallas.ctvnewsreader.utils.XMLUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Uallas on 28/03/2018.
 */

public class News implements Parcelable{

    private Double id;
    private String title;
    private String link;
    private String description;
    private String creditLine;
    private Date date;
    private String image;
    private String imageDescription;
    private Boolean read;

    public News() {
    }

    protected News(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readDouble();
        }
        title = in.readString();
        link = in.readString();
        description = in.readString();
        creditLine = in.readString();
        image = in.readString();
        imageDescription = in.readString();
        read = in.readInt() == 1 ? true : false;
    }

    // instatiate an object from XML
    public News(XmlPullParser parser) throws IOException, XmlPullParserException, ParseException {
        parser.require(XmlPullParser.START_TAG, "", "item");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            if (parser.getName().equals("title")) {
                this.title = XMLUtil.readText(parser, "title");
            } else if (parser.getName().equals("link")) {
                this.link = XMLUtil.readText(parser,"link");
            } else if (parser.getName().equals("description")) {
                this.description = XMLUtil.readText(parser,"description");
            } else if (parser.getName().equals("creditLine")) {
                this.creditLine = XMLUtil.readText(parser,"creditLine");
            } else if (parser.getName().equals("guid")) {
                this.id = Double.parseDouble(XMLUtil.readText(parser,"guid"));
            } else if (parser.getName().equals("pubDate")) {
                final SimpleDateFormat timeFormatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
                this.date = timeFormatter.parse(XMLUtil.readText(parser,"pubDate"));
            } else if (parser.getName().equals("enclosure")) {
                this.image = parser.getAttributeValue(null, "url");
                this.imageDescription = XMLUtil.readText(parser,"enclosure");
            } else {
                XMLUtil.skip(parser);
            }
        }
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(String creditLine) {
        this.creditLine = creditLine;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(id);
        }
        parcel.writeString(title);
        parcel.writeString(link);
        parcel.writeString(description);
        parcel.writeString(creditLine);
        parcel.writeString(image);
        parcel.writeString(imageDescription);
        parcel.writeInt(read ? 1 : 0);
    }
}
