package com.servocode.skating.presentation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.servocode.skating.core.model.Trick;
import com.servocode.skating.presentation.fragments.TrickFragment;

import java.util.List;

import lombok.Getter;

public class TricksAdapter extends FragmentStatePagerAdapter {

    @Getter
    private final List<Trick> tricks;

    public TricksAdapter(FragmentManager fragmentManager, List<Trick> tricks) {
        super(fragmentManager);
        this.tricks = tricks;
    }

    @Override
    public Fragment getItem(int position) {
        return TrickFragment.newInstance(tricks.get(position));
    }

    @Override
    public int getCount() {
        return tricks.size();
    }

}
