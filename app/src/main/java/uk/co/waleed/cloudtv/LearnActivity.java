package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LearnActivity extends Activity {

    List<Learn> learnList;
    int learnId = 0;
    Learn currentLearn;
    ImageView img;
    TextView topic,information;
    ScrollView learn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learn);

        //try to get image from url ...
        img = (ImageView) findViewById(R.id.imageView1);
        topic = (TextView) findViewById(R.id.topic);
        information = (TextView) findViewById(R.id.information);
        learn = (ScrollView) findViewById(R.id.scrollViewLearn);
        getData();
        currentLearn = learnList.get(learnId);
        setView();
    }

    public void back(View v) {
        learnId --;
        if (learnId < 0) {
            Intent intent = new Intent(this, Options.class);
            startActivity(intent);
            finish();
        }else {
            currentLearn = learnList.get(learnId);
            setView();
        }
    }

    public void next (View v) {
        learnId ++;
        if (learnId>=learnList.size()) {
            //cant go anywhere
            Toast toast = Toast.makeText(getApplicationContext(), "You have learnt everything from the database \nWell Done", Toast.LENGTH_LONG);
            toast.show();
            learnId--;
        }else {
            currentLearn = learnList.get(learnId);
            setView();
        }
    }

    public void setView(){
        learn.smoothScrollTo(0,0);
        img.setImageBitmap(currentLearn.getBitmap());
        topic.setText(currentLearn.getTOPIC());
        information.setText(currentLearn.getINFORMATION());
    }

    public void getData()
    {
        List<Learn> tempList = new ArrayList<Learn>();
        String str = "";
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("https://www.ledhubuk.com/android/learn.php");
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            str += readStream(is);
            Log.e("Debug -- ",str);
            Scanner scanner = new Scanner(str);
            scanner.useDelimiter("<br>");
            int id = 0;
            String imageURL = "";
            String topic = "";
            String information = "";

            Scanner scanner1 = new Scanner(str);
            scanner1.useDelimiter("<br>");
            while (scanner1.hasNext()) {
                String test = scanner.next();
                Log.e("Debug --", test);
                String[] result = test.split(";");
                for (int i = 0; i < result.length; i++) {
                    if (i == 0) {
                        id = Integer.parseInt(result[i]);
                    }
                    if (i == 1) {
                        imageURL = result[i];
                    }
                    if (i == 2) {
                        topic = result[i];
                    }
                    if (i == 3) {
                        information = result[i];
                    }
                }
                Learn learn = new Learn();
                learn.setID(id);
                learn.setIMAGEURL(imageURL);
                learn.setTOPIC(topic);
                learn.setINFORMATION(information);
                tempList.add(learn);
            }
        }
        catch (Exception ex) {
            Log.e("PHP", ""+ex.getMessage());
            str += "PHP error - " + ex.getMessage();
        }

        this.learnList = tempList;
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
