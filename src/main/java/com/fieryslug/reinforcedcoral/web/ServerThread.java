package com.fieryslug.reinforcedcoral.web;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class ServerThread extends Thread {

    public ServerThread(FrameCoral frame) {
        super(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
                    server.createContext("/test", new RequestHandler(frame));
                    server.start();
                    System.out.println("Server started");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
