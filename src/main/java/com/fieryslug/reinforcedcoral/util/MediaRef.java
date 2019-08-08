package com.fieryslug.reinforcedcoral.util;

import info.clearthought.layout.TableLayout;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MediaRef {


    public static Map<String, Image> pathImageCache = new HashMap<>();

    public static final Image CHROME = getImage("/res/icon/chrome.png");
    public static final Image CORAL = getImage("/res/icon/coral.png");
    public static final Image ADD = getImage("/res/icon/buttonadd.png");
    public static final Image ADD_HOVER = getImage("/res/icon/buttonadd_hover.png");
    public static final Image ADD_PRESS = getImage("/res/icon/buttonadd_press.png");

    public static final Image PROBLEM = getImage("/res/button/problem.png");
    public static final Image PROBLEM_HOVER = getImage("/res/button/problem_hover.png");
    public static final Image PROBLEM_PRESS = getImage("/res/button/problem_press.png");
    public static final Image PROBLEM_DISABLED = getImage("/res/button/problem_disabled.png");
    public static final Image PROBLEM_SELECTED = getImage("/res/button/problem_selected.png");
    public static final Image PROBLEM_DISABLED_SELECTED = getImage("/res/button/problem_disabled_selected.png");
    public static final Image PROBLEM_PREENABLED = getImage("/res/button/problem_preenabled.png");
    public static final Image PROBLEM_PREENABLED_SELECTED = getImage("/res/button/problem_preenabled_selected.png");


    public static final String APPLE1 = "/res/sound/apple1.wav";
    public static final String SUPERCELL = "/res/sound/supercell.wav";
    public static final String EXPLOSION = "/res/sound/explosion.wav";


    public static Image getImage(String path) {

        Image image = pathImageCache.get(path);
        if(image != null) return image;

        try {
            image = ImageIO.read(MediaRef.class.getResource(path));
        } catch (Exception e) {
            System.out.println("Error occurred while loading image: " + path);
            e.printStackTrace();
        }
        if(image != null) pathImageCache.put(path, image);
        return image;
    }

    public static BufferedImage toBufferedImage(Image image) {

        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static AudioStream playWav(String path) {

        AudioStream audioStream = null;

        try {
            InputStream inputStream = MediaRef.class.getResourceAsStream(path);
            audioStream = new AudioStream(inputStream);
        } catch (Exception e) {
            System.out.println("Error occurred while loading audio file: " + path);
            e.printStackTrace();
        }

        AudioPlayer.player.start(audioStream);
        return audioStream;
    }
}
