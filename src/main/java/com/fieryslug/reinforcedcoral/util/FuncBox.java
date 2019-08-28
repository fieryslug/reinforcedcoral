package com.fieryslug.reinforcedcoral.util;


import com.fieryslug.reinforcedcoral.core.Team;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.Stack;


public class FuncBox {

    private static Map<Pair<Image, Dimension>, Image> imageCache = new HashMap<>();
    private static Map<Image, BufferedImage> bufferedImageCache = new HashMap<>();
    private static Map<Pair<Color, Integer>, Border> lineBorderCache = new HashMap<>();

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

    public static void addKeyBinding(JComponent c, KeyStroke key, final Action action) {
        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, key);
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

    public static String readExternalFile(String urlstr) {

        String res = "";
        try {
            File file = new File(urlstr);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);


            BufferedReader read = new BufferedReader(reader);
            String i;
            while ((i = read.readLine()) != null)
                res = res + i + "\n";
            read.close();
        } catch (Exception e) {

            e.printStackTrace();

        }

        return res;

    }

    public static String[] listDir(String urlstr) {

        String[] res = {};
        try {
            File file = new File(urlstr);
            res = file.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void copy(String path, String pathDest, boolean external) {
        if (external) {
            try {
                FileUtils.copyFile(new File(path), new File(pathDest));
            } catch (Exception e) {
                System.out.println("Error occured while copying " + path + " to " + pathDest);
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.copyInputStreamToFile(FuncBox.class.getResourceAsStream(path), new File(pathDest));

            } catch (Exception e) {
                System.out.println("Error occurred while copying " + path + " to " + pathDest);
                e.printStackTrace();
            }
        }
    }


    public static Image resizeImage(Image image, int x, int y) {
        BufferedImage bimage = MediaRef.toBufferedImage(image);
        return bimage.getScaledInstance(x, y, Image.SCALE_SMOOTH);

    }

    public static Image resizeImagePreservingRatio(Image image, int x, int y) {

        Pair<Image, Dimension> information = new Pair<>(image, new Dimension(x, y));

        Image imageNew = imageCache.get(information);
        if (imageNew != null) return imageNew;

        BufferedImage bimage = MediaRef.toBufferedImage(image);
        int height = bimage.getHeight();
        int width = bimage.getWidth();

        double scalex = (double) x / width;
        double scaley = (double) y / height;
        double scale = Math.min(scalex, scaley);

        imageNew = bimage.getScaledInstance((int) (scale * width), (int) (scale * height), Image.SCALE_SMOOTH);
        if (imageNew != null) imageCache.put(new Pair<>(image, new Dimension(x, y)), imageNew);
        return imageNew;

    }

    /*
    @Deprecated
    public static File fileFromPath(String path) {

        try {
            return new File(FuncBox.class.getResource(path).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    */

    public static InputStream inputStreamFromPath(String path) {
        return inputStreamFromPath(path, false);
    }

    public static InputStream inputStreamFromPath(String path, boolean external) {
        if (external) {
            File file = new File(path);
            try {
                return new FileInputStream(file);
            } catch (Exception e) {
                System.out.println("Error occurred while loading file " + file.getPath());
                e.printStackTrace();
                return null;
            }
        } else {
            return FuncBox.class.getResourceAsStream(path);
        }
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

    public static Border getCompoundLineBorder(Color color, int size) {
        Border outer = BorderFactory.createLineBorder(color, size);
        Border inner = new EmptyBorder(-2, -2, -2, -2);
        return new CompoundBorder(outer, null);
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
                while (addresses.hasMoreElements()) {
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
        return createDivisionArray(n, 1.0);
    }

    public static double[] createDivisionArray(int n, double a) {

        if (n == 1) {
            return new double[]{round(a, 3)};
        }

        double u = round(a / n, 3);
        double r = a - u;
        double[] div = createDivisionArray(n - 1, r);
        return ArrayUtils.addAll(div, u);

    }



    public static double round(double a, int b) {
        double mul = Math.pow(10, b);
        double aa = a * mul;
        double k = (double) Math.round(aa);
        return k / mul;
    }

    public static BufferedImage toBufferedImage(Image img) {

        BufferedImage bufferedImage = bufferedImageCache.get(img);
        if(bufferedImage != null) return bufferedImage;


        if (img instanceof BufferedImage) {
            BufferedImage bimg = (BufferedImage) img;
            bufferedImageCache.put(img, bimg);
            return bimg;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        bufferedImageCache.put(img, bimage);
        return bimage;
    }

    public static String getHtmlRealText(String html) {


        char delimL = '<';
        char delimR = '>';
        StringBuilder res = new StringBuilder();
        boolean inTag = false;

        Stack<Character> stack = new Stack<>();

        for (char c : html.toCharArray()) {

            if (c == delimL) {
                inTag = true;
            }

            if(!inTag) {
                res.append(c);
            }

            if (c == delimR) {
                inTag = false;
            }
        }
        return res.toString();

    }

    public static ArrayList<Team> generateTeams(int teamNum) {

        ArrayList<Team> teams = new ArrayList<>();

        for (int i = 0; i < teamNum; ++i) {
            teams.add(new Team(i + 1));
        }
        return teams;

    }

    public static String getRawFileName(String path) {

        int ind = path.lastIndexOf('/');
        if(ind == -1) return path;

        return path.substring(ind+1);


    }

    public static void saveImage(Image image, File file) {

        BufferedImage bimage = toBufferedImage(image);
        String name = getRawFileName(file.getName());
        try {
            if (name.endsWith(".png")) {
                ImageIO.write(bimage, "png", file);
            }
            if (name.endsWith(".jpg")) {
                ImageIO.write(bimage, "jpg", file);
            }
        } catch (Exception e) {
            System.out.println("Error occurred while saving image " + file.getPath());
            e.printStackTrace();
        }

    }

    public static String removeHtmlTag(String html) {

        if(html.startsWith("<html>") && html.endsWith("</html>"))
            return html.substring(6, html.length() - 7);
        return html;

    }

    public static String toValidFileName(String text) {

        String r = text.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        if (r.length() == 0) {
            return "0";
        }
        else
            return r;

    }


}
