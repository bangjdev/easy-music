package bang.app.easymusic.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import bang.app.easymusic.R;
import bang.app.easymusic.controllers.Captain;
import bang.app.easymusic.models.SongsManager;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private SongsManager songsManager;
    private Captain captain;

    private RecyclerView songsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data
        songsManager = new SongsManager();
        captain = new Captain(this);

        // UI
        songsListView = findViewById(R.id.songsListView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        btnExit = findViewById(R.id.btnExit);

        if (!checkPermissions()) {
            requestPermissions();
            return;
        }

        reloadSongs();

        swipeRefreshLayout.setOnRefreshListener(this::reloadSongs);
        btnExit.setOnClickListener(view -> System.exit(0));
    }

    private void reloadSongs() {
        captain.loadSongsFromStorage();
        if (songsManager.getAllSongs().size() != 0) {
            songsListView.setLayoutManager(new LinearLayoutManager(this));
            songsListView.setAdapter(new SongsListAdapter(songsManager.getAllSongs()));
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Cần quyền đọc dữ liệu!", Toast.LENGTH_LONG);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 6969);
        }
    }

    public SongsManager getSongsManager() {
        return songsManager;
    }

}