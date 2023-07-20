package com.example.cryptography;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Hill_F1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Hill_F1 extends Fragment {
    private String mystr;
    private TextView tvMytext;
    private ImageView img;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Hill_F1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Hill_F1.
     */
    // TODO: Rename and change types and number of parameters
    public static Hill_F1 newInstance(String param1, String param2) {
        Hill_F1 fragment = new Hill_F1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        tvMytext = view.findViewById(R.id.tv);
        img = view.findViewById(R.id.img);
        Bundle data = getArguments();
        mystr = data.getString("tech_name").toString();
        tvMytext.setText(mystr);
        img.setImageResource(R.drawable.img_4);
        return view;
    }
}