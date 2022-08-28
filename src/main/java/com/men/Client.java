package com.men;

public class Client {
    public static String ipAddr = "192.168.0.187";
    public static int port = 8080;



    public static void main(String[] args) {
        new com.men.ClientSomth(ipAddr, port);
    }
}
