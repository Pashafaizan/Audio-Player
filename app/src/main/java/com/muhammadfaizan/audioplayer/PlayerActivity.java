package com.muhammadfaizan.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.muhammadfaizan.audioplayer.MainActivity.musicFiles;

public class PlayerActivity extends AppCompatActivity {
  TextView song_name, artist_name, duration_played, duration_total;
  ImageView cover_art,next_btn,prev_btn, back_btn, shuffle_btn, repeat_btn;
  FloatingActionButton play_pause_btn;
  SeekBar seekBar;
  int position= -1;
  static  ArrayList<MusicFiles> listSongs = new ArrayList<>();
  static Uri uri;
  static MediaPlayer mediaPlayer;
  private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntentMethod();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mediaPlayer != null && b){
                    mediaPlayer.seekTo(i*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null)
                {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private String formattedTime(int mCurrentPosition) {
        String totalout = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" +seconds;
        totalNew = minutes + ":0" + seconds;
        if(seconds.length()==1)
        {
            return  totalNew;
        }
        else
        {
            return totalout;
        }
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        listSongs = musicFiles;
        if(listSongs != null)
        {
            play_pause_btn.setImageResource(R.drawable.ic_baseline_pause_24);
            uri= Uri.parse(listSongs.get(position).getPath());

        }
        if(mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
           mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
           mediaPlayer.start();
        }
        else{
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        Toast.makeText(this,mediaPlayer.getDuration()/1000+"lkldsjflsk", Toast.LENGTH_SHORT).show();
       seekBar.setMax(mediaPlayer.getDuration()/1000);
       metaData(uri);
//       duration_total.setText(formattedTime(mediaPlayer.getDuration()/1000));
}

    private void initViews() {
        song_name = findViewById(R.id.song_name);
       artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.duration_played);
        duration_total = findViewById(R.id.duration_total);
        cover_art = findViewById(R.id.cover_art);
        next_btn = findViewById(R.id.id_next);
        prev_btn = findViewById(R.id.prev);
        back_btn = findViewById(R.id.back_button);
        shuffle_btn = findViewById(R.id.shuffle);
        repeat_btn = findViewById(R.id.repeat);
        play_pause_btn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekbar);
    }

    private void metaData(Uri uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration());
        duration_total.setText(formattedTime(durationTotal/1000));
        byte[] art = retriever.getEmbeddedPicture();
        if(art != null){
            Glide.with(this).asBitmap().load(art).into(cover_art);
        }
        else {
            Glide.with(this).asBitmap().load(R.drawable.spalsh_logo_2 ).into(cover_art);

        }
    }
}