package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class Learn {
    private int ID;
    private String IMAGEURL;
    private String TOPIC;
    private String INFORMATION;
    public Learn() {
        ID = 0;
        IMAGEURL = "";
        TOPIC = "";
        INFORMATION = "";
    }
    public Learn(String iMAGEURL, String tOPIC, String iNFORMATION) {
        IMAGEURL = iMAGEURL;
        TOPIC = tOPIC;
        INFORMATION = iNFORMATION;
    }
    public int getID() {
        return ID;
    }
    public String getIMAGEURL() {
        return IMAGEURL;
    }

    public Bitmap getBitmap() {
        Bitmap b = null;
        try
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(getIMAGEURL());
            InputStream is = new BufferedInputStream(url.openStream());
            b = BitmapFactory.decodeStream(is);

        }
        catch(Exception e)
        {
            Log.e("Learn--", ""+e.getMessage());
        }
        return b;
    }

    public String getTOPIC() {
        return TOPIC;
    }
    public String getINFORMATION() {
        return INFORMATION;
    }
    public void setID(int id) {
        ID = id;
    }
    public void setIMAGEURL(String imageURL) {
        IMAGEURL = imageURL;
    }
    public void setTOPIC(String topic) {
        TOPIC = topic;
    }
    public void setINFORMATION(String information) {
        INFORMATION = information;
    }
}