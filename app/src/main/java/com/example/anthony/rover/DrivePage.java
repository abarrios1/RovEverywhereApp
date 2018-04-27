package com.example.anthony.rover;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    public DrivePage() {

    }

    public int freq = 1500;
    public int tiltFreq = 1400;

    //SSHConnection sshConnection = new SSHConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        return inflater.inflate(R.layout.activity_drive_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        ImageButton forward = (ImageButton) view.findViewById(R.id.forward);
        ImageButton left = (ImageButton) view.findViewById(R.id.left);
        ImageButton right = (ImageButton) view.findViewById(R.id.right);
        ImageButton reverse = (ImageButton) view.findViewById(R.id.reverse);
        ImageButton panLeft = (ImageButton) view.findViewById(R.id.panLeft);
        ImageButton panRight = (ImageButton) view.findViewById(R.id.panRight);
        ImageButton tiltUp = (ImageButton) view.findViewById(R.id.tiltUp);
        final ImageButton tiltDown = (ImageButton) view.findViewById(R.id.tiltDown);
        ImageButton neutral = (ImageButton) view.findViewById(R.id.neutral);
        final EditText pwm = (EditText) view.findViewById(R.id.pwm);


//Image Buttons for moving the wheels
        forward.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Forward!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./forward.cgi " + pwm.getText().toString());
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/forward.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/stop.cgi");
                }
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Left!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./left.cgi " + pwm.getText().toString());
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/left.cgi " + pwm.getText().toString());
                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/stop.cgi");
                }
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Right!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./right.cgi " + pwm.getText().toString());
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/right.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/stop.cgi");
                }
                return true;
            }
        });

        reverse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Moving Backwards!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./reverse.cgi " + pwm.getText().toString());
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/reverse.cgi " + pwm.getText().toString());

                } else if (motionEvent.getAction() == motionEvent.ACTION_UP) {
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./stop.cgi");
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/stop.cgi");
                }
                return true;
            }
        });

//Image Buttons for moving the camera

        //get value of freq
        //if value +100 <= 2500 then proceed to go to ajax call
        //else
        panLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    int currFreq = freq;
                    int incFreq = currFreq + 100;

                    if (incFreq <= 2500) {
                        freq = incFreq;
                        String move = String.valueOf(freq);
                        Toast.makeText(getContext(),"Rotating Left!", Toast.LENGTH_SHORT).show();
                        //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./panMovement.cgi " );
                        SshConnection("/var/www/RovEverywhere/public/cgi-bin/panMovement.cgi " + move);
                        Log.d("TAG", "Pan Freq: "+freq);
                    } else {
                        Toast.makeText(getContext(),"Can't Rotate Anymore!!!", Toast.LENGTH_SHORT).show();
                    }


                }
                return true;
            }
        });

        panRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    int currFreq = freq;
                    int decFreq = currFreq - 100;

                    if (decFreq >= 600) {
                        freq = decFreq;
                        String move = String.valueOf(tiltFreq);
                        Toast.makeText(getContext(),"Rotating Right!", Toast.LENGTH_SHORT).show();
                        //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./panMovement.cgi " );
                        SshConnection("/var/www/RovEverywhere/public/cgi-bin/panMovement.cgi " + move);
                        Log.d("TAG", "Pan Freq: "+freq);
                    } else {
                        Toast.makeText(getContext(),"Can't Rotate Anymore!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        tiltUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    int currFreq = tiltFreq;
                    int incFreq = currFreq + 100;

                    if (incFreq <= 2400) {
                        tiltFreq = incFreq;
                        String move = String.valueOf(tiltFreq);
                        Toast.makeText(getContext(),"Tilting Up!", Toast.LENGTH_SHORT).show();
                        //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./tiltMovement.cgi " + move);
                        SshConnection("/var/www/RovEverywhere/public/cgi-bin/tiltMovement.cgi " + move);
                        Log.d("TAG", "Tilt Freq: "+tiltFreq);
                    } else {
                        Toast.makeText(getContext(),"Can't Tilt Anymore!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        tiltDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    int currFreq = tiltFreq;
                    int decFreq = currFreq - 100;

                    if (decFreq >= 900) {
                        tiltFreq = decFreq;
                        String move = String.valueOf(tiltFreq);
                        Toast.makeText(getContext(),"Tilting Down!", Toast.LENGTH_SHORT).show();
                        //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./tiltMovement.cgi " + move);
                        SshConnection("/var/www/RovEverywhere/public/cgi-bin/tiltMovement.cgi " + move);
                        Log.d("TAG", "Tilt Freq: " + tiltFreq);
                    } else {
                        Toast.makeText(getContext(),"Can't Tilt Anymore!!!", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        neutral.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    freq = 1500;
                    tiltFreq = 1400;
                    Toast.makeText(getContext(),"Neutralizing!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("cd /var/www/RovEverywhere/public/cgi-bin/; ./panTiltNeutral.cgi " );
                    SshConnection("/var/www/RovEverywhere/public/cgi-bin/panTiltNeutral.cgi");
                }
                return true;
            }
        });
    }

    public void SshConnection(String comm) {
        ChannelExec channel;
        Session session;
        JSch jSch = new JSch();
        Properties config = new Properties();
        try {
            config.put("StrictHostKeyChecking", "no");

            session = jSch.getSession("pi", "192.168.12.1");
            session.setConfig(config);
            session.setPassword("green");
            session.connect();

            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(comm);
            channel.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
