package entity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.northeastern.shareplaylist.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<Item> itemList;
    String TOKEN;
    String playlistId;
    Toast toast;
    public ItemAdapter(List<Item> itemList, String TOKEN, String playlistId) {

        this.TOKEN = TOKEN;
        this.playlistId = playlistId;
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
        String uri = itemList.get(position).uri;
        holder.addSongToList(uri);
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
        private Toast successToast;
        private Toast failureToast;
        public ItemViewHolder(final View itemView) {
            super(itemView);
            songNameView =itemView.findViewById(R.id.song_name);
            artistNameView = itemView.findViewById(R.id.artist_name);
            albumIcon = itemView.findViewById(R.id.album_image);
            successToast = Toast.makeText(itemView.getContext(), "Successfully added song", Toast.LENGTH_SHORT);
            failureToast = Toast.makeText(itemView.getContext(), "Successfully added song", Toast.LENGTH_SHORT);


            itemView.setOnClickListener(this);

        }

        public void addSongToList(String uri) {
            add = itemView.findViewById(R.id.add_song);
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.spotify.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    JsonPlaceHolderSpotifyApi apiDOA = retrofit.create(JsonPlaceHolderSpotifyApi.class);
                    Call<AddTrackResponse> call = apiDOA.addTrackToPlaylist(playlistId, new AddTrackBody(0, new String[]{uri}), TOKEN);
                    call.enqueue(new Callback<AddTrackResponse>() {
                        @Override
                        public void onResponse(Call<AddTrackResponse> call, Response<AddTrackResponse> response) {

                            System.out.println(uri);
                            System.out.println(playlistId);
                            System.out.println(TOKEN);
                            System.out.println("searching");
                            if (response.isSuccessful()) {
                                successToast.show();
                            }


                            else {
                                failureToast.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddTrackResponse> call, Throwable t) {
                            failureToast.show();
                        }
                    });
                }
            });
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
