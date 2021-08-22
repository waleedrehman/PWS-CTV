package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Highscores extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        TextView textResult = (TextView) findViewById(R.id.helpText);
        TextView title = (TextView) findViewById(R.id.title);
        title.setText("High Scores");
        String str = "Name" + "     " + "Score" + "     " + "Time Remaining" + "\n";
        //Reference http://stackoverflow.com/questions/21055147/get-php-echo-in-android
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://www.ledhubuk.com/android/highscores.php");
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            str += readStream(is);
        }
        catch (Exception ex) {
            Log.e("PHP", ""+ex.getMessage());
            str += "PHP error - " + ex.getMessage();
        }
        textResult.setText(str);
    }
    public void back(View o) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
        finish();
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
