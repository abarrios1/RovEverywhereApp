package com.example.anthony.rover;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etEmail, etUsername, etPass, etPassConf;
    Button buttonRegister;

    String name, email, username, pass, passConf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = (EditText) findViewById(R.id.editName);
        etEmail = (EditText) findViewById(R.id.editEmail);
        etUsername = (EditText) findViewById(R.id.editUsername);
        etPass = (EditText) findViewById(R.id.editPassword);
        etPassConf = (EditText) findViewById(R.id.editPasswordConfirm);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userReg();
            }
        });
    }

    public void userReg() {

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();
        passConf = etPassConf.getText().toString();

        //Used for toast
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;
        CharSequence text = "";

        //Database Connection variables
        final String url = "jdbc:mysql://108.255.70.130:3306/roveverywhere";
        final String user = "rover";
        final String passWord = "glutenfreebrick";

        if (name.equals("") || email.equals("") || pass.equals("") || passConf.equals("")) {
            text = "Some fields are empty. Please fill them in.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        } else {
            if (pass.equals(passConf)) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, user, passWord);
                /* System.out.println("Database connection success"); */

                    text = "Database connection success\n";
                    Statement st = con.createStatement();
                    ResultSet checkEmail = st.executeQuery("SELECT email FROM users WHERE email= '" + email + "';");
                    boolean isEmptyEmail = checkEmail.next();

                    //If email is not found
                    if (!isEmptyEmail) {
                        st.executeUpdate("INSERT INTO users (name, email, password) VALUES ('" + name + "', '" + email + "', '" + pass + "');");
                        text = "Successfully Registered!";
                        Toast toast1 = Toast.makeText(context, text, duration);
                        toast1.show();
                    } else {
                        text = "Sorry, email is already in use. Try another email.";
                        Toast toast3 = Toast.makeText(context, text, duration);
                        toast3.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    text = e.toString();
                    Toast toast2 = Toast.makeText(context, text, duration);
                    toast2.show();
                }
            } else {
                text = "Please check passwords. Passwords do not match.";
                Toast toast0 = Toast.makeText(context, text, duration);
                toast0.show();
            }
        }
    }
}
