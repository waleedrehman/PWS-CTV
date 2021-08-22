package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import uk.co.waleed.cloudtv.R;


public class MainActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {

            /*
            * Showing splash screen with a timer. This will be useful when you
            * want to show case your app logo / company
            */

            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}