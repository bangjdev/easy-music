package bang.app.easymusic.views;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bang.app.easymusic.R;
import bang.app.easymusic.controllers.EasyMediaPlayer;
import bang.app.easymusic.models.Song;

public class SongsListAdapter extends RecyclerView.Adapter<SongsListAdapter.SongItemViewHolder> {
    private List<Song> songsList;

    public SongsListAdapter(List<Song> songsList) {
        this.songsList = songsList;
    }

    @NonNull
    @Override
    public SongItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_layout, parent, false);
        return new SongItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongItemViewHolder holder, int position) {
        holder.getTvSongTitle().setText(songsList.get(position).getTitle());
        if (EasyMediaPlayer.currentIndex == position) {
            holder.itemView.setBackgroundColor(Color.RED);
            holder.getTvSongTitle().setTextColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.getTvSongTitle().setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(view -> {
            EasyMediaPlayer.play(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class SongItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSongTitle;

        public SongItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitle = itemView.findViewById(R.id.tvSongTitle);
        }

        public TextView getTvSongTitle() {
            return tvSongTitle;
        }
    }
}
