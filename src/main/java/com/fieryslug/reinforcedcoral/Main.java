package com.fieryslug.reinforcedcoral;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.WorkTable;
import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.web.RequestHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {

        System.out.println("fieryslug is back");

        Game game = WorkTable.getGame();
        FrameCoral frame = new FrameCoral(game);
        Thread serverthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
                    server.createContext("/test", new RequestHandler(frame));
                    server.start();
                    System.out.println("Server started");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        serverthread.start();





    }

}
