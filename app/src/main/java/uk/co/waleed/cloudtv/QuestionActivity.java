package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by waleed on 08/08/2016.
 */
public class QuestionActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    Question currentQ;
    TextView txtQuestion, times, scored;
    Button button1, button2, button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get data from db
        getDB();
        currentQ = quesList.get(qid);
        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        scored = (TextView) findViewById(R.id.score);
        times = (TextView) findViewById(R.id.timers);
        setQuestionView();
        times.setText("00:05:00");
        scored.setText("Score : " + score);
        CounterClass timer = new CounterClass(300000, 1000);
        timer.start();
        // button click listeners
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button1.getText().toString());
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button3.getText().toString());
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button4.getText().toString());
            }
        });
    }

    public void getDB(){
        List<Question> tempList = new ArrayList<Question>();
        String str = "";
        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://www.ledhubuk.com/android/questions.php");
            HttpURLConnection mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setDoInput(true);

            InputStream is = new BufferedInputStream(mUrlConnection.getInputStream());
            str += readStream(is);
            Log.e("Debug -- ",str);
            Scanner scanner = new Scanner(str);
            scanner.useDelimiter("<br>");
            int id = 0;
            String question = "";
            String answer = "";
            String optionA = "";
            String optionB = "";
            String optionC = "";
            String optionD = "";

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
                        question = result[i];
                    }
                    if (i == 2) {
                        answer = result[i];
                    }
                    if (i == 3) {
                        optionA = result[i];
                    }
                    if (i == 4) {
                        optionB = result[i];
                    }
                    if (i == 5) {
                        optionC = result[i];
                    }
                    if (i == 6) {
                        optionD = result[i];
                    }
                }
                Question quest = new Question();
                quest.setID(id);
                quest.setQUESTION(question);
                quest.setANSWER(answer);
                quest.setOPTA(optionA);
                quest.setOPTB(optionB);
                quest.setOPTC(optionC);
                quest.setOPTD(optionD);
                tempList.add(quest);
            }
        }
        catch (Exception ex) {
            Log.e("PHP", ""+ex.getMessage());
            str += "PHP error - " + ex.getMessage();
        }

        this.quesList = tempList;

    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {
            // if conditions matches increase the int (score) by 1
            // and set the text of the score view
            score++;
            scored.setText("Score : " + score);
        }
        if (qid < quesList.size()) {
            // if questions are not over then do this
            currentQ = quesList.get(qid);
            setQuestionView();
        } else {
            // if over do this
            Intent intent = new Intent(QuestionActivity.this,
                    ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            b.putString("time",times.getText().toString());
            b.putInt("totalQuestions",quesList.size());
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {
        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onFinish() {
            times.setText("Time is up");
            Intent intent = new Intent(QuestionActivity.this,
                    ResultActivity.class);
            Bundle b = new Bundle();
            b.putInt("score", score); // Your score
            b.putString("time",times.getText().toString());
            b.putInt("totalQuestions",quesList.size());
            intent.putExtras(b); // Put your score to your next
            startActivity(intent);
            finish();
        }
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);
        }
    }
    private void setQuestionView() {
        // the method which will put all things together
        txtQuestion.setText(currentQ.getID() + ".   " +currentQ.getQUESTION());
        button1.setText(currentQ.getOPTA());
        button2.setText(currentQ.getOPTB());
        button3.setText(currentQ.getOPTC());
        button4.setText(currentQ.getOPTD());
        qid++;
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
