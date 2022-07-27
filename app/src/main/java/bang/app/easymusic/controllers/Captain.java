package bang.app.easymusic.controllers;

import android.database.Cursor;
import android.provider.MediaStore;

import bang.app.easymusic.models.Song;
import bang.app.easymusic.models.SongsManager;
import bang.app.easymusic.views.MainActivity;

public class Captain {
    private static final String LOG_TAG = "CAPTAIN";

    private final MainActivity mainActivity;

    public Captain(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void loadSongsFromStorage() {
        SongsManager.getInstance().clear();

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor songsCursor = mainActivity.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        while (songsCursor.moveToNext()) {
            Song newSong = new Song(songsCursor.getString(0), songsCursor.getString(1), songsCursor.getString(2));
            SongsManager.getInstance().addSong(newSong);
        }
    }
}
