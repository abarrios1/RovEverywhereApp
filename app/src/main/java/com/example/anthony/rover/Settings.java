package com.example.anthony.rover;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    Button restart, shutdown;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        restart = view.findViewById(R.id.restart);
        shutdown = view.findViewById(R.id.shutdown);

        restart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Restarting!", Toast.LENGTH_SHORT).show();
                    connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./restart.cgi");

                }
                return true;
            }
        });

        shutdown.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Shutting Down!", Toast.LENGTH_SHORT).show();
                    connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./shutdown.cgi ");

                }
                return true;
            }
        });
    }

    public void connectSSH(String command) {
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
    }

}
