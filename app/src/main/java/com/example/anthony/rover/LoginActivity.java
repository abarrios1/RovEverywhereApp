package com.example.anthony.rover;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    TextView textRegister;
    EditText username;
    EditText password;
    Button btLogin;
    String txtReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Match variables to id's
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        btLogin = (Button) findViewById(R.id.buttonLogin);
        textRegister = (TextView) findViewById(R.id.textRegister);

        //Declare string text for text register
        txtReg = "Register Now!";
        textRegister.setText(txtReg);

        //Used to allow connection to database
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Moves to Register Activity on click
        textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });

        //Moves to main page activity, IF authentication is correct
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authentication();
                //Intent navIntent = new Intent(LoginActivity.this, NavigationBottom.class);
                //LoginActivity.this.startActivity(navIntent);
            }
        });

    }

    public void authentication() {
        //Used for toast
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        CharSequence text = "";

        //Database Connection variables
        final String url = "jdbc:mysql://ipAddress/database";
        final String user = "username";
        final String pass = "password";

        String userName = username.getText().toString();
        String passWord = password.getText().toString();

        //If fields are empty
        if (userName.equals("") || passWord.equals("")) {
            text = "Email or Password are empty. Try again!";
            Toast toast1 = Toast.makeText(context, text, duration);
            toast1.show();
        //If fields are not empty
        } else {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
             //System.out.println("Database connection success");

                text = "Database connection success\n";
                Statement st = con.createStatement();
                ResultSet email = st.executeQuery("SELECT email FROM users WHERE email= '" + userName + "';");
                boolean isEmptyEmail = email.next();

                //Email is not found
                if (!isEmptyEmail) {
                    text = "Wrong email or password";
                    Toast wrong = Toast.makeText(context, text, duration);
                    wrong.show();
                //Email is found and now checks pass
                } else {
                    ResultSet passW = st.executeQuery("SELECT password FROM users WHERE password= '" + passWord + "';");

                    boolean isEmptyPass = passW.next();

                    //If password is not found
                    if (!isEmptyPass) {
                        text = "Wrong email or password";
                        Toast right = Toast.makeText(context, text, duration);
                        right.show();
                    //If password is found, move to main page
                    } else {
                        Intent navIntent = new Intent(LoginActivity.this, NavigationBottom.class);
                        LoginActivity.this.startActivity(navIntent);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                text = e.toString();
                Toast toast3 = Toast.makeText(context, text, duration);
                toast3.show();
            }
        }

    }
}
