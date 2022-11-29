package entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.shareplaylist.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<Item> itemList;


    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        String songName = itemList.get(position).songName;
        String artistName = itemList.get(position).artist[0].getArtistName();
        String iconUrl = itemList.get(position).album.getAlbumImages()[0].imageUrl;
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
        private Button add;
        public ItemViewHolder(final View itemView) {
            super(itemView);
            songNameView =itemView.findViewById(R.id.song_name);
            artistNameView = itemView.findViewById(R.id.artist_name);
            albumIcon = itemView.findViewById(R.id.album_image);
            add = itemView.findViewById(R.id.add_song);
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
//            Glide.with(getApplicationContext()).load(iconUrl).into(icon);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
