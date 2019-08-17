package com.fieryslug.reinforcedcoral;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.WorkTable;

import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;


import com.fieryslug.reinforcedcoral.minigame.snake.ProblemSnake;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.web.RequestHandler;
import com.sun.jna.platform.win32.OaIdl;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Set;


import javax.swing.*;

//import layout.TableLayout;
import info.clearthought.layout.TableLayout;

public class Main {

    public static void main(String[] args) {

        start();
        //ProblemSet set = new ProblemSet("oblivion2");
        //set.loadProblemSet();
        //set.dumpProblemSet("oblivion3", true);


    }

    public static void start() {
        System.out.println("fieryslug is back\n");


        DataLoader.getInstance().checkFiles();

        ProblemSet set = new ProblemSet("oblivion3");
        set.loadProblemSet();


        Game game = null;
        if (Reference.DEFAULT_GAME == 0)
            game = WorkTable.getGame0();
        if (Reference.DEFAULT_GAME == 1)
            game = WorkTable.getGame1();


        game = new Game(set, game.teams);

        FrameCoral frame = new FrameCoral(game);
        Thread serverthread = new Thread(new Runnable() {
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
                        if (frame.isUndecorated()) {
                            device.setFullScreenWindow(null);
                            frame.setUndecorated(false);

                        } else {
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
