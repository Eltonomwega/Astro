package com.example.eltonpsycho.astro.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.eltonpsycho.astro.R;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity {
   public ArrayList arrayTitle,arrayArtist;
    Uri uri,url;
    RecyclerView recyclerView;
    public static final int MY_PERMISSION_REQUEST=1;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        arrayTitle = new ArrayList<>();


        if (ContextCompat.checkSelfPermission(PlaylistActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(PlaylistActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
           doStuff();
        }

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.playlistToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        // inflating the popupmenu not complete
        ImageButton imageButton = (ImageButton) findViewById(R.id.options);

        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),imageButton);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu,popupMenu.getMenu());
        /////
    }

    private void doStuff() {
        getMusic();
        recyclerView = findViewById(R.id.playlistRecyler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView.LayoutManager rvlayoutmanager = layoutManager;


        recyclerView.setLayoutManager(rvlayoutmanager);

        recylerAdapter adapter = new recylerAdapter(this,arrayTitle);

        recyclerView.setAdapter(adapter);
    }

    public void launchHome(View view) {
        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
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
                           .PERMISSION_GRANTED){
                       Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
                       doStuff();
                   }
                } else {
                    Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show();
                }
            }

    }}






    public void getMusic(){

        ContentResolver contentResolver = getApplicationContext().getContentResolver();
         uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] columns = {MediaStore.Audio.AudioColumns.TITLE,MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST,MediaStore.Audio.AudioColumns.ALBUM_ID
        };

        final String where = MediaStore.Audio.AudioColumns.IS_MUSIC + "=1";

        Cursor songcursor = contentResolver.query(uri,columns,
                null,null,null);

        if (songcursor != null&& songcursor.moveToFirst()){

            int songTitle = songcursor.getColumnIndex(MediaStore.Audio
                    .AudioColumns.TITLE);
            int albumTitle = songcursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
            int songArtist = songcursor.getColumnIndex(MediaStore.Audio
                    .AudioColumns.ARTIST);
            int albumId = songcursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID);
          //  int albpath = songcursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
         //   int data = songcursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            do {
                //AudioModel audioModel = new AudioModel();


                String currenTitle = songcursor.getString(songTitle);
                String currentAlb = songcursor.getString(albumTitle);
                String currenArtist = songcursor.getString(songArtist);
                Long albumid = songcursor.getLong(albumId);


                Bitmap bitmap=null;
               Bitmap bitmapp=null;
               try {
                   Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");

                   Uri albumUri = ContentUris.withAppendedId(sArtworkUri,albumid);

                   bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), albumUri);
                    bitmapp = Bitmap.createScaledBitmap(bitmap, 30, 30, true);
                }catch (Exception exception){
                    exception.printStackTrace();
                    bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.icons8_music_50);
                    bitmapp = Bitmap.createScaledBitmap(bitmap,30,30,true);

                }/*catch (IOException e){
                    e.printStackTrace();
                }*/
                String result = currenTitle.replaceAll("[\\p{P}\\p{S}]"," ");

                arrayTitle.add(new modelPlaylist(result,currenArtist,bitmap));


            }while (songcursor.moveToNext());
            songcursor.close();
            } }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.playlisttoolbar,menu);


        return super.onCreateOptionsMenu(menu);
    }




}
