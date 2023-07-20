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
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OneTimePadding_F2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneTimePadding_F2 extends Fragment {

    public OneTimePadding_F2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneTimePadding_F2.
     */
    // TODO: Rename and change types and number of parameters
    public static OneTimePadding_F2 newInstance(String param1, String param2) {
        OneTimePadding_F2 fragment = new OneTimePadding_F2();
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
        return inflater.inflate(R.layout.fragment_one_time_padding__f2, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String path;
        final int[] stopPosition = new int[1];
        Bundle data = getArguments();
        String mystr = data.getString("tech_name");
        VideoView video1 = view.findViewById(R.id.video1);
        path = "android.resource://"+ getContext().getPackageName()+"/raw/"+R.raw.video5;
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