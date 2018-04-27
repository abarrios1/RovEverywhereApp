package com.example.anthony.rover;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import net.margaritov.preference.colorpicker.ColorPickerDialog;

import java.util.Properties;
import java.util.Set;

import yuku.ambilwarna.AmbilWarnaDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {

    Button restart, shutdown, colorPick;
    ImageButton lighton, lightoff, honk;
    int DefaultColor;
    Drawable buttonBackground;

    ColorPickerDialog colorPickerDialog;
    int color = Color.parseColor("#33b5e5");

    public Settings() {
        // Required empty public constructor
    }


    //SSHConnection sshConnection = new SSHConnection();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        colorPick = (Button) view.findViewById(R.id.colorPick);
        restart = (Button) view.findViewById(R.id.restart);
        shutdown = (Button) view.findViewById(R.id.shutdown);
        lighton = (ImageButton) view.findViewById(R.id.lighton);
        lightoff = (ImageButton) view.findViewById(R.id.lightoff);
        honk = (ImageButton) view.findViewById(R.id.honk);

        DefaultColor = ContextCompat.getColor(view.getContext(), R.color.white);


        colorPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                colorPickerDialog = new ColorPickerDialog(getContext(), color);
                colorPickerDialog.setAlphaSliderVisible(true);
                colorPickerDialog.setHexValueEnabled(true);
                colorPickerDialog.setTitle("Choose a color");
                colorPickerDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int i) {
                        color = i;
                        colorPick.setBackgroundColor(color);
                        Log.d("TAG", "onOk(). Color: #" + Integer.toHexString(color));
                    }
                });
                colorPickerDialog.show();
            }
        });


        lighton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Lights On!", Toast.LENGTH_SHORT).show();
                    ColorDrawable buttonColor = (ColorDrawable) colorPick.getBackground();
                    int colorId = buttonColor.getColor();
                    String colors = Integer.toHexString(colorId);
                    String updateCol = colors.substring(2);
                    Log.d("TAG", "onOk(). Color: #" + colors.substring(2));
                    int  r =  Integer.valueOf( updateCol.substring( 0, 2 ), 16 );
                    String R = String.valueOf(r);
                    int  g =  Integer.valueOf( updateCol.substring( 2, 4 ), 16 );
                    String G = String.valueOf(g);
                    int  b =  Integer.valueOf( updateCol.substring( 4, 6 ), 16 );
                    String B = String.valueOf(b);
                    Log.d("TAG", "onOk(). Color: RGB: (" + R + " " + G + " " + B + ")");

                    //sshConnection.command("/var/www/RovEverywhere/public/cgi-bin/lighton.cgi 255 255 255");
                    SshConnection("sudo /var/www/RovEverywhere/public/cgi-bin/lighton.cgi " + R + " " + G + " " + B);

                }
                return false;
            }
        });

        lightoff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Lights Off!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("/var/www/RovEverywhere/public/cgi-bin/lightoff.cgi");
                    SshConnection("sudo /var/www/RovEverywhere/public/cgi-bin/lightoff.cgi");


                }
                return false;
            }
        });

        restart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Restarting!", Toast.LENGTH_SHORT).show();
                    //sshConnection.command("/var/www/RovEverywhere/public/cgi-bin/restart.cgi");
                    SshConnection("sudo /var/www/RovEverywhere/public/cgi-bin/restart.cgi");

                }
                return true;
            }
        });

        shutdown.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    Toast.makeText(getContext(),"Shutting Down!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./shutdown.cgi ");
                    //sshConnection.command("/var/www/RovEverywhere/public/cgi-bin/shutdown.cgi");
                    SshConnection("sudo /var/www/RovEverywhere/public/cgi-bin/shutdown.cgi");
                }
                return true;
            }
        });

        honk.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == motionEvent.ACTION_DOWN) {
                    //Toast.makeText(getContext(),"Shutting Down!", Toast.LENGTH_SHORT).show();
                    //connectSSH("cd /var/www/RovEverywhere/public/cgi-bin/; ./shutdown.cgi ");
                    //sshConnection.command("/var/www/RovEverywhere/public/cgi-bin/shutdown.cgi");
                    SshConnection("sudo /var/www/RovEverywhere/public/cgi-bin/playSound.cgi");
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
