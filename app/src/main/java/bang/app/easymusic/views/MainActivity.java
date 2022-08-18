package bang.app.easymusic.views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import bang.app.easymusic.R;
import bang.app.easymusic.controllers.Captain;
import bang.app.easymusic.models.SongsManager;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";

    private Captain captain;

    public static RecyclerView songsListView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvSongsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Data
        captain = new Captain(this);

        // UI
        songsListView = findViewById(R.id.songsListView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        Button btnExit = findViewById(R.id.btnExit);
        tvSongsCount = findViewById(R.id.tvSongsCount);

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
        if (SongsManager.getInstance().getAllSongs().size() != 0) {
            songsListView.setLayoutManager(new LinearLayoutManager(this));
            songsListView.setAdapter(new SongsListAdapter(SongsManager.getInstance().getAllSongs()));
        }
        swipeRefreshLayout.setRefreshing(false);
        tvSongsCount.setText(TextUtils.join(" ", new String[]{
                getResources().getString(R.string.label_songs_count_prefix),
                Integer.toString(SongsManager.getInstance().getSongsCount()),
                getResources().getString(R.string.label_songs_count_postfix)
        }));
    }

    private boolean checkPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, getResources().getString(R.string.alert_need_permissions), Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 6969);
        }
    }

}