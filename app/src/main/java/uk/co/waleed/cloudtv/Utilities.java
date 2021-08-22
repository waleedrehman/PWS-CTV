package uk.co.waleed.cloudtv;

import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by waleed on 13/08/2016.
 * Tried to have this so that it could be used in LearnActivity and QuestionActivity class
 * so that the app could be Object Oriented with no repetative code
 * Ran into errors - left each class with independent code for getting data from db/php server.
 */
public class Utilities {

    public Utilities(){

    }

    public String getData(String urlString){
        String str = "";
        try
        {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlString);
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            str += readStream(is);
        }
        catch (Exception ex){
            Log.e("PHP", ""+ex.getMessage());
            str += "PHP error - " + ex.getMessage();
        }
        return str;
    }

    //Reference http://stackoverflow.com/questions/8376072/whats-the-readstream-method-i-just-can-not-find-it-anywhere
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
