package uk.co.waleed.cloudtv;

/**
 * Created by waleed on 18/07/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import uk.co.waleed.cloudtv.R;

/**
 * Created by waleed on 01/08/2016.
 */
public class Help extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView textResult = (TextView) findViewById(R.id.helpText);;
        textResult.setText("This game has been created for the Mobile Development Module." + "\n"
                + "The Game has been created by: Muhammad Waleed ur Rehman." + "\n"
                + "The server being used to host the Database and PHP files used are located at www.ledhubuk.com" + "\n"
                + "The help for this game was received from Parallel Codes - Referenced in details in Report." + "\n");
    }
    public void back(View o) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
        finish();
    }
}