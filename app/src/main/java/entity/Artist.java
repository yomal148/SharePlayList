package entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Image {

    @SerializedName("height")
    int height;
    @SerializedName("width")
    int width;
    @SerializedName("url")
    String Url;
}

class ExternalUrls {
    @SerializedName("spotify")
    String url;

    public String getUrl() {
        return url;
    }
}
class Followers {
    @SerializedName("href")
    String href;
    @SerializedName("total")
    Long total;
}
public class Artist {
    @SerializedName("external_urls")
    private ExternalUrls externalUrls;

    @SerializedName("followers")
    private Followers followers;

    @SerializedName("name")
    private String name;

    @SerializedName("popularity")
    private int popularity;

    @SerializedName("genres")
    private String[] genres;

    public String getGenres() {
        String res = "";
        for (String genre: genres) {
            res += genre + ",";
        }

        return res;
    }



    public String getName() {
        return name;
    }



    @SerializedName("images")
    private List<Image> image;

    public int getPopularity() {
        return popularity;
    }

    public String getExternalUrls() {
        return externalUrls.url;
    }

    public Followers getFollowers() {
        return followers;
    }

    public String getFirstImageUrl() {
        return image.get(0).Url;
    }

    /**
     *
     * Example response"
     * "external_urls": {
     *         "spotify": "https://open.spotify.com/artist/7dGJo4pcD2V6oG8kP0tJRR"
     *     },
     *     "followers": {
     *         "href": null,
     *         "total": 60439612
     *     },
     *     "genres": [
     *         "detroit hip hop",
     *         "hip hop",
     *         "rap"
     *     ],
     *     "href": "https://api.spotify.com/v1/artists/7dGJo4pcD2V6oG8kP0tJRR",
     *     "id": "7dGJo4pcD2V6oG8kP0tJRR",
     *     "images": [
     *         {
     *             "height": 640,
     *             "url": "https://i.scdn.co/image/ab6761610000e5eba00b11c129b27a88fc72f36b",
     *             "width": 640
     *         },
     *         {
     *             "height": 320,
     *             "url": "https://i.scdn.co/image/ab67616100005174a00b11c129b27a88fc72f36b",
     *             "width": 320
     *         },
     *         {
     *             "height": 160,
     *             "url": "https://i.scdn.co/image/ab6761610000f178a00b11c129b27a88fc72f36b",
     *             "width": 160
     *         }
     *     ],
     *     "name": "Eminem",
     *     "popularity": 91,
     *     "type": "artist",
     *     "uri": "spotify:artist:7dGJo4pcD2V6oG8kP0tJRR"
     *
     */
}
