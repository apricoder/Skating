package com.servocode.skating.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.servocode.skating.R;
import com.servocode.skating.common.SkatingApplication;
import com.servocode.skating.fragments.TrickFragment;
import com.servocode.skating.tricks.Trick;
import com.servocode.skating.tricks.Tricks;

import java.util.List;

public class ShowTricksActivity extends FragmentActivity {

    private ViewPager tricksPager;
    private ScreenSlidePagerAdapter tricksAdapter;
    public Tricks tricks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tricks);

        ((SkatingApplication)getApplication()).inject(this);
        initPager();
        findViewById(R.id.next_trick_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tricksPager.setCurrentItem(tricksPager.getCurrentItem() + 1);
            }
        });
    }

    private void initPager() {
        tricksPager = (ViewPager) findViewById(R.id.tricks_pager);
        tricksAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), tricks.getAll());
        tricksPager.setAdapter(tricksAdapter);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private final List<Trick> tricks;

        public ScreenSlidePagerAdapter(FragmentManager fragmentManager, List<Trick> all) {
            super(fragmentManager);
            this.tricks = all;
        }

        @Override
        public Fragment getItem(int position) {
            Trick trick = tricks.get(position);
            String trickName = trick.name;
            String drawableId = "";
            return TrickFragment.newInstance(trickName, drawableId);
        }

        @Override
        public int getCount() {
            return tricks.size();
        }

    }
}
