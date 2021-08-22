package uk.co.waleed.cloudtv;


/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uk.co.waleed.cloudtv.R;

public class Options extends Activity {

    Button btnplay,btnlearn,btnhighscore,btnhelp,btnexit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionsmenu);

        btnplay = (Button) findViewById(R.id.btnplay);
        btnlearn = (Button) findViewById(R.id.btnlearn);
        btnhighscore = (Button) findViewById(R.id.btnhighscore);
        btnhelp = (Button) findViewById(R.id.btnhelp);
        btnexit = (Button) findViewById(R.id.btnexit);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetConnection()){
                    Intent intent = new Intent(Options.this,
                            QuestionActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(Options.this, "There is no Internet Connection \nPlease connect to the internet and try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetConnection()){
                    Intent intent = new Intent(Options.this,
                            LearnActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Options.this, "There is no Internet Connection \nPlease connect to the internet and try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnhighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(internetConnection()){
                    Intent intent = new Intent(Options.this,
                            Highscores.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Options.this, "There is no Internet Connection \nPlease connect to the internet and try again", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Options.this,
                        Help.class);
                startActivity(intent);
                finish();
            }
        });

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
                System.exit(1);
            }
        });

    }

    public boolean internetConnection(){

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
