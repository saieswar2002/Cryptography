package com.example.cryptography;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class BlankFragment2 extends Fragment {


    public BlankFragment2() {
        // Required empty public constructor
    }

    public static BlankFragment2 newInstance(String param1, String param2) {
        BlankFragment2 fragment = new BlankFragment2();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String path;
        final int[] stopPosition = new int[1];
        Bundle data = getArguments();
        String mystr = data.getString("tech_name");
        VideoView video1 = view.findViewById(R.id.video1);
        path = "android.resource://"+ getContext().getPackageName()+"/raw/"+R.raw.video1;
        Uri uri = Uri.parse(path);
        video1.setVideoURI(uri);
        video1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            MediaPlayer mp;
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                video1.start();
                video1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(video1.isPlaying()){
                            stopPosition[0] = video1.getCurrentPosition();
                            video1.pause();
                        }
                        else {
                            video1.seekTo(stopPosition[0]);
                            video1.start();
                        }
                    }
                });

            }

        });


    }
}