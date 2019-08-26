package com.fieryslug.reinforcedcoral.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AePlayWave extends Thread {
    private AudioInputStream stream;

    private final int EXTERNAL_BUFFER_SIZE = 26214; // 6.4Kb

    private boolean continuing = true;
    private SourceDataLine audioLine;


    public AePlayWave(InputStream wavfile) {
        try {
            stream = AudioSystem.getAudioInputStream(wavfile);
        } catch (Exception e) {
            System.out.println("Error occurred while loading audio " + wavfile);
            e.printStackTrace();
        }

    }

    public AePlayWave(URL url) {
        try {
            stream = AudioSystem.getAudioInputStream(url);
        } catch (Exception e) {
            System.out.println("Error occurred while loading audio" + url);
            e.printStackTrace();
        }
    }

    public void run() {

        AudioInputStream audioInputStream = this.stream;

        /*
        try {
            //audioInputStream = AudioSystem.getAudioInputStream(filename);
            audioInputStream = AudioSystem.getAudioInputStream(this.stream);
            System.out.println(audioInputStream);
        } catch (UnsupportedAudioFileException e) {

        } catch (IOException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
            this.audioLine = auline;
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];

        try {
            while (nBytesRead != -1 && continuing) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);

                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
        }

    }

    public void stopSound() {
        continuing = false;
        try {
            if (this.audioLine != null && false) {
                this.audioLine.stop();
                this.audioLine.drain();
                this.audioLine.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
