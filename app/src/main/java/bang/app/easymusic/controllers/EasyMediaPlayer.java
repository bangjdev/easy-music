package bang.app.easymusic.controllers;

import android.media.MediaPlayer;

import java.io.IOException;

import bang.app.easymusic.models.SongsManager;

public class EasyMediaPlayer {
    private static MediaPlayer instance;
    public static MediaPlayer getInstance() {
        if (instance == null) {
            instance = new MediaPlayer();
        }
        return instance;
    }
    public static int currentIndex = -1;

    public static void play(int id) {
        currentIndex = id;
        MediaPlayer player = EasyMediaPlayer.getInstance();
        player.reset();
        try {
            player.setDataSource(SongsManager.getInstance().getSongByIndex(id).getPath());
            player.prepare();
            player.start();
            player.setOnCompletionListener(EasyMediaPlayer::playNext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playNext(MediaPlayer mediaPlayer) {
        currentIndex ++;
        if (currentIndex >= SongsManager.getInstance().getAllSongs().size()) {
            currentIndex = 0;
        }
        try {
            play(currentIndex);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
