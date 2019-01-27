package com.example.eltonpsycho.astro.models;

import android.graphics.Bitmap;

public class modelPlaylist {

    private String Title;
    private String Artist;
    private String filePath;

    public modelPlaylist(String title, String artist, String filePath) {
        Title = title;
        Artist = artist;
        this.filePath = filePath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
