package com.servocode.skating.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.servocode.skating.R;
public class TrickFragment extends Fragment {
    private static final String TRICK_NAME = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String trickName;
    private String mParam2;

    public TrickFragment() {
        // Required empty public constructor
    }

    public static TrickFragment newInstance(String trickName, String param2) {
        TrickFragment fragment = new TrickFragment();
        Bundle args = new Bundle();
        args.putString(TRICK_NAME, trickName);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trickName = getArguments().getString(TRICK_NAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trick, container, false);
        TextView trickNameText = (TextView) view.findViewById(R.id.trick_name);
        trickNameText.setText(trickName);
        return view;
    }
}
