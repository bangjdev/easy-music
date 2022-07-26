package bang.app.easymusic.controllers;

import android.media.MediaPlayer;

public class EasyMediaPlayer {
    private static MediaPlayer instance;
    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }
    public static int currentIndex = -1;
}
