package com.servocode.skating.presentation.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.servocode.skating.R;
import com.servocode.skating.core.app.SkatingApplication;
import com.servocode.skating.core.model.Trick;
import com.servocode.skating.core.resources.TrickImageIdService;

import lombok.Getter;

public class TrickFragment extends Fragment {
    private static final String TRICK_NAME = "TRICK_NAME";

    public TrickImageIdService trickImageIdService;

    @Getter private Trick trick;
    private ImageView trickImage;

    public TrickFragment() {
        // Required empty public constructor
    }

    public static TrickFragment newInstance(Trick trick) {
        TrickFragment fragment = new TrickFragment();
        Bundle args = new Bundle();
        args.putSerializable(TRICK_NAME, trick);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SkatingApplication)getActivity().getApplication()).inject(this);
        if (getArguments() != null) {
            trick = (Trick) getArguments().getSerializable(TRICK_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_trick, container, false);
        trickImage = (ImageView) fragmentView.findViewById(R.id.trick_image);
        int drawableId = trickImageIdService.getTrickImageIdByShortName(trick.getShortName());
        Log.i("Skating", ">>>>>> Looking for image for " + trick.getShortName() + " !");
        Log.i("Skating", ">>>>>> Got id " + drawableId + " !");
        trickImage.setImageResource(drawableId);
        return fragmentView;
    }

}
