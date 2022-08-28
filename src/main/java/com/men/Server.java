package com.men;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {

    public static final int PORT = 8080;
    public static LinkedList<ServerSomth> serverList = new LinkedList<ServerSomth>();
    public static Story story;

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(PORT);
        story = new Story();
        System.out.println("com.moc.Server start...");
        try {
            while (true) {
                Socket socket = server.accept();
                try {
                    serverList.add(new ServerSomth(socket));
                }
                catch (IOException e) {server.close();}
            }
        }
        finally {
            server.close();
        }

    }

}
