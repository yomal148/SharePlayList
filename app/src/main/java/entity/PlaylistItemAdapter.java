package entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.shareplaylist.R;

public class PlaylistItemAdapter extends RecyclerView.Adapter<PlaylistItemAdapter.ItemViewHolder>{

    List<Item> itemList;
    String TOKEN;
    String playlistId;

    public PlaylistItemAdapter(List<Item> itemList, String TOKEN, String playlistId) {

        this.TOKEN = TOKEN;
        this.playlistId = playlistId;
        this.itemList = itemList;

    }

    @NonNull
    @Override
    public PlaylistItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item_row, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistItemAdapter.ItemViewHolder holder, int position) {
        String songName = itemList.get(position).track.getSongName();
        String artistName = itemList.get(position).track.album.artists[0].getArtistName();
        String iconUrl = itemList.get(position).track.album.getAlbumImages()[0].imageUrl;
        holder.setSongName(songName);
        holder.setArtistName(artistName);
        holder.setIcon(iconUrl);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView songNameView;
        private TextView artistNameView;
        private ImageView albumIcon;
        public ItemViewHolder(final View itemView) {
            super(itemView);
            songNameView =itemView.findViewById(R.id.playlist_song_name);
            artistNameView = itemView.findViewById(R.id.playlist_artist_name);
            albumIcon = itemView.findViewById(R.id.playlist_album_image);

            itemView.setOnClickListener(this);
        }

        public void setSongName(String s) {
            this.songNameView.setText(s);
        }

        public void setArtistName(String s) {
            this.artistNameView.setText(s);
        }

        public void setIcon(String url) {
            Glide.with(this.itemView.getContext()).load(url).into(albumIcon);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
