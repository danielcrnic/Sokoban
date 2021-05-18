package framework;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.*;

public class AudioPlayer {

    private Thread thread;
    private Player player;
    private final File file;

    public AudioPlayer(File file) {
        this.file = file;
        thread = new MusicThread();
    }

    public void play() {
        if (thread.isAlive()) {
            // Restart the music
            stop();
        }

        try {
            player = new Player(new FileInputStream(file));
        } catch (JavaLayerException | FileNotFoundException e) {
            System.err.println("An problem playing the music occurred!");
            e.printStackTrace();
            return;     // No reason to call the thread
        }
        thread = new MusicThread();     // Reset the thread
        thread.start();
    }

    public void stop() {
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }

    public File getFile() {
        return file;
    }

    public class MusicThread extends java.lang.Thread {
        @Override
        public void run() {
            try {
                player.play();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void interrupt() {
            player.close();
        }
    }

    /**
     * An method that checks if it is possible to play audio on the machine. This is useful to check before trying to
     * see if the device supports audio playing.
     *
     * For example, if missing codec or audio playing capabilities are blocked. It will not make it possible for the
     * user to play audio.
     *
     * This is useful to use before starting to either warn the user or prevent the application from continuing if
     * audio playing is not possible.
     *
     * @return True, if the device is capable of playing, false if the device cannot play.
     */
    public static boolean supportPlayingAudio() {
        try {
            Player player = new Player(new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            });
        } catch (JavaLayerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
