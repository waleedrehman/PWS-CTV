package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.waleed.cloudtv.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ResultActivity extends Activity {

    private int score,totalQuestions;
    private String time,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textResult = (TextView) findViewById(R.id.textResult);
        Bundle b = getIntent().getExtras();
        score = b.getInt("score");
        time = b.getString("time");
        totalQuestions = b.getInt("totalQuestions");
        textResult.setText("Your score is " + " " + score + " Out of " + totalQuestions+ ".\n" +
                "Thanks for playing my game." + "\n" +
                "Time Remaining " + time + " .");
        //https://www.ledhubuk.com/android/insert-highscores.php?name="waleed"&score=20&time="00:00:26"
    }
    public void playagain(View v) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
        finish();
    }

    public void submit(View v){
        //implement sending data using php
        EditText nameEdit = (EditText) findViewById(R.id.name);
        name = nameEdit.getText().toString();
        Context context = getApplicationContext();
        CharSequence text = "";
        int duration = Toast.LENGTH_LONG;
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://www.ledhubuk.com/android/insert-highscores.php?name=" + name + "&score=" + score + "&time=" + time);
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            text = text + readStream(is);
        }
        catch (Exception ex) {
            Log.e("PHP", ""+ex.getMessage());
            text = text + "PHP error - " + ex.getMessage();
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }

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
