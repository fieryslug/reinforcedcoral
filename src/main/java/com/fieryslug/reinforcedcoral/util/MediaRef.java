package com.fieryslug.reinforcedcoral.util;

//import sun.audio.AudioPlayer;
//import sun.audio.AudioStream;

import javax.imageio.ImageIO;
        import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
        import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MediaRef {


    private static Map<String, Image> pathImageCache = new HashMap<>();
    private static Map<String, Image> externalCache = new HashMap<>();
    private static Map<URL, Image> urlImageCache = new HashMap<>();

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

    public static final Map<String, Image> TEXTURE_ICON_MAP = new HashMap<>();

    static {
        String prefix = "/res/images/texture_preview/";
        TEXTURE_ICON_MAP.put("caramel", getImage(prefix + "caramel.png"));
        TEXTURE_ICON_MAP.put("balloon", getImage(prefix + "balloon.png"));
        TEXTURE_ICON_MAP.put("classic", getImage(prefix + "classic.png"));
        TEXTURE_ICON_MAP.put("ocean", getImage(prefix + "ocean.png"));
        TEXTURE_ICON_MAP.put("eggplant", getImage(prefix + "eggplant.png"));
        TEXTURE_ICON_MAP.put("jungle", getImage(prefix + "jungle.png"));
        TEXTURE_ICON_MAP.put("punk", getImage(prefix + "punk.png"));
    }


    public static Image getImage2(String path) {

        Image image = pathImageCache.get(path);
        if (image != null) return image;

        try {
            image = ImageIO.read(MediaRef.class.getResource(path));
        } catch (Exception e) {
            System.out.println("Error occurred while loading image: " + path);
            e.printStackTrace();
        }
        if (image != null) pathImageCache.put(path, image);
        System.out.println("currently " + pathImageCache.size() + " images in cache.");
        return image;
    }

    @Deprecated
    public static Image getImage(String path) {

        if (path.startsWith(Reference.EXTERNAL_PREFIX)) {
            String path1 = path.substring(Reference.EXTERNAL_PREFIX.length());
            return getImage(path1, true);
        }
        else {
            return getImage(path, false);
        }

    }

    public static Image getImage(String path, boolean external) {

        Image image;
        image = (external ? externalCache : pathImageCache).get(path);
        if(image != null) return image;

        if (external) {

            try {
                image = ImageIO.read(new File(path));

            } catch (Exception e) {
                System.out.println("Error occurred while loading external image: " + path);
                e.printStackTrace();
            }

        } else {

            try {
                image = ImageIO.read(MediaRef.class.getResource(path));
            } catch (Exception e) {
                System.out.println("Error occurred while loading image: " + path);
                e.printStackTrace();
            }

        }

        if(image != null) (external ? externalCache : pathImageCache).put(path, image);
        return image;

    }

    public static Image getImage(URL url) {

        Image image = urlImageCache.get(url);
        if(image != null) return image;

        try {
            image = ImageIO.read(url);
            if(image != null) urlImageCache.put(url, image);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    /*
    @Deprecated
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
    */

    public static AePlayWave playSound(String path) {
        AePlayWave res = new AePlayWave(FuncBox.class.getResource(path));
        res.start();
        return res;
    }

    public static AePlayWave playSound(InputStream inputStream) {
        AePlayWave res = new AePlayWave(inputStream);
        res.start();
        return res;
    }

    public static boolean isCollided = false;

    public static void play(String filename) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(MediaRef.class.getResourceAsStream(filename)));// new
                    // File(filename)));
                    if (isCollided) {
                        clip.stop();
                    } else {
                        clip.loop(0);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace(System.out);
                }
            }
        }).start();
    }





}
