package bang.app.easymusic.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class SongsManager extends Observable {
    private final List<Song> songsList;

    public SongsManager(){
        songsList = new ArrayList<>();
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
}
