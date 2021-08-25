package uk.co.waleed.cloudtv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login extends Activity {

    EditText username, pass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.username_input);
        pass = (EditText) findViewById(R.id.pass);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = username.getText().toString();
                password = pass.getText().toString();
                Toast.makeText(Login.this, "Username: " + email +  "\nPassword: " + password, Toast.LENGTH_LONG).show();
            }
        });

    }
}
