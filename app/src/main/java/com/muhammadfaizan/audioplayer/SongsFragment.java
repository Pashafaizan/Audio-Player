package com.muhammadfaizan.audioplayer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.muhammadfaizan.audioplayer.MainActivity.musicFiles;


public class SongsFragment extends Fragment {
 RecyclerView recyclerView;
 MusicAdapter adapter;

    public SongsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = view.findViewById(R.id.songs_recycler_view);
        recyclerView.setHasFixedSize(true);
        if(!(musicFiles.size() < 1))
        {
            adapter = new MusicAdapter(getContext(),musicFiles);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),recyclerView.VERTICAL, false));
        }
        return view;
    }
}