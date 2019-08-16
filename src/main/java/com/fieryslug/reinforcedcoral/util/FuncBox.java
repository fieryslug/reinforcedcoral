package com.fieryslug.reinforcedcoral.util;


import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class FuncBox {

    public static Map<Pair<Image, Dimension>, Image> imageCache = new HashMap<>();
    public static Map<Pair<Color, Integer>, Border> lineBorderCache = new HashMap<>();

    public static JLabel blankLabel(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));


        return label;
    }

    public static void addKeyBinding(JComponent c, String key, final Action action) {

        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        c.getActionMap().put(key, action);
        c.setFocusable(true);

    }

    public static String readFile(String urlstr) {

        URL url = FuncBox.class.getResource(urlstr);

        String res = "";
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String i;
            while ((i = read.readLine()) != null)
                res = res + i + "\n";
            read.close();
        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    public static Image resizeImage(Image image, int x, int y) {
        BufferedImage bimage = MediaRef.toBufferedImage(image);
        return bimage.getScaledInstance(x, y, Image.SCALE_SMOOTH);

    }

    public static Image resizeImagePreservingRatio(Image image, int x, int y) {

        Pair<Image, Dimension> information = new Pair<>(image, new Dimension(x, y));

        Image imageNew = imageCache.get(information);
        if(imageNew != null) return imageNew;

        BufferedImage bimage = MediaRef.toBufferedImage(image);
        int height = bimage.getHeight();
        int width = bimage.getWidth();

        double scalex = (double) x / width;
        double scaley = (double) y / height;
        double scale = Math.min(scalex, scaley);

        imageNew =  bimage.getScaledInstance((int) (scale * width), (int) (scale * height), Image.SCALE_SMOOTH);
        if(imageNew != null) imageCache.put(new Pair<>(image, new Dimension(x, y)), imageNew);
        return imageNew;

    }

    @Deprecated
    public static File fileFromPath(String path) {

        try {
            return new File(FuncBox.class.getResource(path).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream inputStreamFromPath(String path) {
        return FuncBox.class.getResourceAsStream(path);
    }

    public static void listAllFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();

        for (Font font : allFonts) {
            System.out.println(font.getFontName(Locale.US));
        }
    }

    public static <T> T randomChoice(Set<T> set, Random random) {
        ArrayList<T> list = new ArrayList<>(set);
        int i = random.nextInt(list.size());
        return list.get(i);
    }

    public static <T> Set<T> randomChoice(Set<T> set, Random random, int count) {
        if (set.size() < count) {
            return null;
        }
        ArrayList<T> list = new ArrayList<>(set);
        Collections.shuffle(list);
        return new HashSet<>(list.subList(0, count));


    }

    public static Border getLineBorder(Color color, int size) {

        Pair<Color, Integer> info = new Pair<>(color, size);
        Border border = lineBorderCache.get(info);
        if (border != null) {
            return border;
        }
        border = BorderFactory.createLineBorder(color, size);
        lineBorderCache.put(info, border);
        return border;

    }

    public static String getIp() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) continue;

                    ip = addr.getHostAddress();
                    System.out.println(iface.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return ip;
    }

    public static double[] createDivisionArray(int n) {

        double[] res = new double[n];
        for (int i = 0; i < n; ++i) {
            res[i] = 1.0d/n;
        }
        return res;

    }


}
