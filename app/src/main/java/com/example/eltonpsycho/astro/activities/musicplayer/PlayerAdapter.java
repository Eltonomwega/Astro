package com.example.eltonpsycho.astro.activities.musicplayer;

public interface PlayerAdapter {

    void loadMedia(String path);

    void release();

    boolean isPlaying();

    void play();

    void reset();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}