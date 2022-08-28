package com.men;

import java.io.*;
import java.net.Socket;

public class ServerSomth extends Thread {

    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    public ServerSomth (Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        Server.story.printStory(out);
        this.start();
    }

    @Override
    public void run() {
        String word;
        try {
            word = in.readLine();
            out.write(word + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
        try {
            while (true) {
                word = in.readLine();
                if (word.equals("stop") || word.equals("exit") || word.equals("quit")) {
                    this.downService();
                    break;
                }
                System.out.println("Echo: " + word);
                Server.story.addStoryMsg(word);
                for (ServerSomth str : Server.serverList) {
                    str.send(word);
                }
            }
        } catch (IOException ignored) {
        }
    }

    private void downService() {
        try {
            if(!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ServerSomth str : Server.serverList) {
                    if (str.equals(this)) str.interrupt();
                    Server.serverList.remove(this);
                }
            }
        }
        catch (IOException ignored) {}
    }

    private void send (String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        }
        catch (IOException ignored) {}
    }

}
