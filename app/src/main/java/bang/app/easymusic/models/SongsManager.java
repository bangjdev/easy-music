package bang.app.easymusic.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SongsManager extends Observable {
    private static SongsManager instance;
    private final List<Song> songsList;

    private SongsManager() {
        songsList = new ArrayList<>();
    }

    public static SongsManager getInstance() {
        if (instance == null) {
            instance = new SongsManager();
        }
        return instance;
    }

    public Song getSongByIndex(int id) throws IndexOutOfBoundsException {
        return songsList.get(id);
    }

    public void addSong(Song song) {
        songsList.add(song);
        notifyObservers();
    }

    public void clear() {
        songsList.clear();
        notifyObservers();
    }

    public List<Song> getAllSongs() {
        return songsList;
    }

    public int getSongsCount() {
        return this.songsList.size();
    }
}
