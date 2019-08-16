package com.fieryslug.reinforcedcoral;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.WorkTable;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;


import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.web.RequestHandler;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.net.InetSocketAddress;


import javax.swing.*;

//import layout.TableLayout;
import info.clearthought.layout.TableLayout;

public class Main {

    public static void main(String[] args) {

        //start();
        start();
    }

    public static void start() {
        System.out.println("fieryslug is back");


        Game game = null;
        if (Reference.DEFAULT_GAME == 0)
            game = WorkTable.getGame0();
        if (Reference.DEFAULT_GAME == 1)
            game = WorkTable.getGame1();
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

    public static void test1() {
        JFrame frame = new JFrame("test");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FuncBox.addKeyBinding(frame.getRootPane(), "F11", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                        if(frame.isUndecorated()) {
                            device.setFullScreenWindow(null);
                            frame.setUndecorated(false);

                        }
                        else {
                            frame.setUndecorated(true);
                            device.setFullScreenWindow(frame);

                        }

                        frame.setVisible(true);

                        //frame.refresh();

                        frame.repaint();
                    }
                }
        );

        JPanel panel = new JPanel();
        double[][] size = {{0.5, 0.5}, {0.5, 0.5}};
        panel.setLayout(new TableLayout(size));

        JButton button = new JButton();
        button.setBackground(Reference.DARKRED);

        panel.add(button, "0, 0");
        frame.add(panel);
    }

}
