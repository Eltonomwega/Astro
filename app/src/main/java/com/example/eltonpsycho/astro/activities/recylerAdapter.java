package com.example.eltonpsycho.astro.activities;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eltonpsycho.astro.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recylerAdapter extends RecyclerView.Adapter<recylerAdapter.ViewHolder> {


    private Context mcontext;
    private ArrayList<modelPlaylist>mlist;




    public recylerAdapter(Context context, ArrayList<modelPlaylist> list){
     mcontext = context;
     mlist=list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);

       View view= layoutInflater.inflate(R.layout.music_card,parent,false);

ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        modelPlaylist modelPlaylist = mlist.get(position);
        TextView ttl,artname;
        CircleImageView albumart;

        ttl=holder.Title;
        artname=holder.artist;
        albumart = holder.albumArt;

        ttl.setText(modelPlaylist.getTitle());
        artname.setText(modelPlaylist.getArtist());
        albumart.setImageBitmap(modelPlaylist.getAlbumArtUri());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView Title,artist;

        CircleImageView albumArt;

        public ViewHolder(View view) {
            super(view);

            Title = (TextView) view.findViewById(R.id.songtitle);
            artist =(TextView) view.findViewById(R.id.artist);
            albumArt = (CircleImageView) view.findViewById(R.id.albumArt);
        }
    }
}
