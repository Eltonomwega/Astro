package com.example.eltonpsycho.astro.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.eltonpsycho.astro.R;
import com.example.eltonpsycho.astro.adapters.PlayListAdapter;
import com.example.eltonpsycho.astro.models.modelPlaylist;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
    public ArrayList<modelPlaylist> playListArrayList = new ArrayList<>();
    Uri uri;
    RecyclerView recyclerView;
    public static final int MY_PERMISSION_REQUEST = 1;
    PlayListAdapter adapter;
    ProgressBar circularProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.playlistToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        circularProgressBar = findViewById(R.id.circular_progress_bar);


        getPermissions();
    }

    private void getPermissions() {
        if (ContextCompat.checkSelfPermission(PlaylistActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(PlaylistActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            doStuff();
        }
    }

    private void doStuff() {

        recyclerView = findViewById(R.id.playlistRecyler);

        //shortened recyclerview layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PlayListAdapter(this, playListArrayList);

        recyclerView.setAdapter(adapter);

        LoadSongsAsyncTask l = new LoadSongsAsyncTask();
        l.execute();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(PlaylistActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager
                            .PERMISSION_GRANTED) {
                        doStuff();
                    }
                } else {
                }
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playlisttoolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.fav:
                return true;
            case R.id.app_bar_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadSongsAsyncTask extends AsyncTask<Void, String, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            circularProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getMusic();
            return null;
        }


        private void getMusic() {

            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            final String[] columns = {MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.Albums.ALBUM,
                    MediaStore.Audio.AudioColumns.ARTIST, MediaStore.Audio.AudioColumns.ALBUM_ID,MediaStore.Audio.AudioColumns.DATA
            };

            final String where = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1";

            Cursor songcursor = contentResolver.query(uri, columns,
                    null, null, null);

            if (songcursor != null && songcursor.moveToFirst()) {

                int songTitle = songcursor.getColumnIndex(MediaStore.Audio
                        .AudioColumns.TITLE);
                int albumTitle = songcursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
                int songArtist = songcursor.getColumnIndex(MediaStore.Audio
                        .AudioColumns.ARTIST);
                int albumId = songcursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID);
                int filePath = songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                //  int albpath = songcursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                //   int data = songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                do {
                    //AudioModel audioModel = new AudioModel();


                    String currenTitle = songcursor.getString(songTitle);
                    String currentAlb = songcursor.getString(albumTitle);
                    String currenArtist = songcursor.getString(songArtist);
                    Long albumid = songcursor.getLong(albumId);
                    String path = songcursor.getString(filePath);


                   /* Bitmap bitmap;
                    Bitmap bitmapp = null;
                    try {
                        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

                        Uri albumUri = ContentUris.withAppendedId(sArtworkUri, albumid);

                        bitmap = MediaStore.Images.Media.getBitmap(PlaylistActivity.this.getContentResolver(), albumUri);
                        bitmapp = Bitmap.createScaledBitmap(bitmap, 30, 30, true);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        bitmap = BitmapFactory.decodeResource(PlaylistActivity.this.getResources(), R.drawable.icons8_music_50);
                        bitmapp = Bitmap.createScaledBitmap(bitmap, 30, 30, true);

                    }*/


                    String result = currenTitle.replaceAll("[\\p{P}\\p{S}]", " ");
                    String passedProgress = result + "," + currenArtist + "," + path;
                    publishProgress(passedProgress);

                } while (songcursor.moveToNext());
                songcursor.close();
            }
        }


        @Override
        protected void onProgressUpdate(String... values) {
            String[] parts = values[0].split(",");
            playListArrayList.add(new modelPlaylist(parts[0], parts[1], parts[2]));
            adapter.notifyDataSetChanged();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            circularProgressBar.setVisibility(View.GONE);
            Log.d("LoadSongsAsyncTask", "Finished gettings songs");
        }
    }
}
