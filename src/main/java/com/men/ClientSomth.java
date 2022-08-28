package com.men;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientSomth {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String addr;
    private int port;
    private String nickname;
    private Date time;
    private String dtime;
    private SimpleDateFormat dt1;


    public ClientSomth(String addr, int port) {
        this.addr = addr;
        this.port = port;
        try {
            this.socket = new Socket(addr, port);
        } catch (IOException e) {
            System.err.println("Socket ne socket");
        }
        try {
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.pressName();;
            new ReadMsg().start();
            new WriteMsg().start();
        }
        catch (IOException e) {
            ClientSomth.this.downService();
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = in.readLine();
                    if (msg.equals("stop") || msg.equals("exit") || msg.equals("quit")) {
                        ClientSomth.this.downService();
                        break;
                    }
                    System.out.println(msg);
                }
            } catch (IOException e) {
                ClientSomth.this.downService();
            }
        }
    }

    private class WriteMsg extends Thread {
        @Override
        public void run() {
            while (true) {
                String userWord;
                try {
                    time = new Date();
                    dt1 = new SimpleDateFormat("HH:mm:ss");
                    dtime = dt1.format(time);
                    userWord = inputUser.readLine();
                    if (userWord.equals("stop") || userWord.equals("exit") || userWord.equals("quit")) {
                        ClientSomth.this.downService();
                        break;
                    } else {
                        out.write("(" + dtime + ") " + nickname + ": " + userWord + "\n");
                    }
                    out.flush();
                } catch (IOException e) {
                    ClientSomth.this.downService();
                }
            }
        }
    }

    private void pressName() {
        System.out.print("Press your name: ");
        try{
            nickname = inputUser.readLine();;
            out.write("Hi " + nickname + "\n");
            out.flush();
        }
        catch (IOException ignored) {}
    }

    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {}
    }
}
