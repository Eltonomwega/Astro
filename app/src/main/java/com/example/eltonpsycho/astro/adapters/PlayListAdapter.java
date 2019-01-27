package com.example.eltonpsycho.astro.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eltonpsycho.astro.R;
import com.example.eltonpsycho.astro.activities.musicplayer.MusicPlayerActivity;
import com.example.eltonpsycho.astro.models.modelPlaylist;

import java.util.ArrayList;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<modelPlaylist> mlist;


    public PlayListAdapter(Context context, ArrayList<modelPlaylist> list) {
        mcontext = context;
        mlist = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final modelPlaylist modelPlaylist = mlist.get(position);


        holder.Title.setText(modelPlaylist.getTitle());
        holder.artist.setText(modelPlaylist.getArtist());

        holder.Title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mcontext, MusicPlayerActivity.class);
                i.putExtra("musicPath", modelPlaylist.getFilePath());
                mcontext.startActivity(i);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Title, artist;

        public ViewHolder(View view) {
            super(view);

            Title = (TextView) view.findViewById(R.id.songtitle);
            artist = (TextView) view.findViewById(R.id.artist);

        }
    }
}
