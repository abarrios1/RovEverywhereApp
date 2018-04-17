package com.example.anthony.rover;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.util.Properties;

/**
 * Created by anthonybarrios on 4/17/18.
 */

public class SSHConnection {

    private ChannelExec channel;
    private Session session;
    JSch jSch = new JSch();
    Properties config = new Properties();

    public SSHConnection() throws Exception {
        config.put("StrictHostKeyChecking", "no");

        session = jSch.getSession("username", "ipAddress");
        session.setConfig(config);
        session.setPassword("password");
        session.connect();

        channel = (ChannelExec) session.openChannel("exec");
    }

    public void close() {
        channel.disconnect();
    }

    public void command(String input) {
        channel.setCommand(input);
        try {
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

}
