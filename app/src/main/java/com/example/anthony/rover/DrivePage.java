package com.example.anthony.rover;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class DrivePage extends Fragment {

    public DrivePage() throws Exception {

    }
    SSHConnection sshConnection = new SSHConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_drive_page, container, false);
    }

    /*public void connectSSH(String command) {
        JSch jSch = new JSch();

        try {
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            Session session = jSch.getSession("pi", "192.168.12.1");
            session.setConfig(config);
            session.setPassword("green");
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.connect();

        } catch (Exception e) {
            System.out.println(e);
        }
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageButton forward = view.findViewById(R.id.forward);
        ImageButton left = view.findViewById(R.id.left);
        ImageButton right = view.findViewById(R.id.right);
        ImageButton reverse = view.findViewById(R.id.reverse);
        final EditText pwm = view.findViewById(R.id.pwm);
        final WebView webView = view.findViewById(R.id.webControls);


        forward.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Forward!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./forward.cgi " + pwm.getText().toString());
                    sshConnection.command("path + ./forward.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    sshConnection.command("path + ./stop.cgi");

                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Left!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./left.cgi " + pwm.getText().toString());
                    sshConnection.command("path + ./left.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    sshConnection.command("path + ./stop.cgi");
                }
                return false;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Right!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./right.cgi " + pwm.getText().toString());
                    sshConnection.command("path + ./right.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    sshConnection.command("path + ./stop.cgi");
                }
                return false;
            }
        });

        reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Backwards!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./reverse.cgi " + pwm.getText().toString());
                    sshConnection.command("path + ./reverse.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    sshConnection.command("path + ./stop.cgi");
                }
                return false;
            }
        });
    }
}
