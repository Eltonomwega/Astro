package com.example.eltonpsycho.astro.activities;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public class modelPlaylist {

    private String Title;
    private String Artist;
private Bitmap albumArtUri;


    public modelPlaylist(String title, String artist,Bitmap albumArt) {
        Title = title;
        Artist = artist;
        albumArtUri = albumArt;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public void setAlbumArtUri(Bitmap albumArtUri) { this.albumArtUri = albumArtUri; }

    public String getTitle() {
        return Title;
    }

    public String getArtist() {
        return Artist;
    }

    public Bitmap getAlbumArtUri() { return albumArtUri; }

}
