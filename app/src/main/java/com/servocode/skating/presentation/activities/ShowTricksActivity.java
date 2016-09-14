package com.servocode.skating.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.servocode.skating.R;
import com.servocode.skating.core.app.SkatingApplication;
import com.servocode.skating.core.events.DisplayedTrickChangedEvent;
import com.servocode.skating.core.model.Trick;
import com.servocode.skating.core.resources.TrickImageIdService;
import com.servocode.skating.core.services.TricksService;
import com.servocode.skating.presentation.adapters.TricksAdapter;
import com.servocode.skating.presentation.fragments.TrickFragment;
import com.servocode.skating.presentation.utils.animation.ActivityNavigator;
import com.servocode.skating.presentation.utils.font.FontCollection;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ShowTricksActivity extends FragmentActivity {

    public TricksService tricksService;
    public FontCollection fontCollection;
    public ActivityNavigator navigator;
    public TrickImageIdService trickImageIdService;

    private TricksAdapter tricksAdapter;
    private ViewPager tricksPager;
    private TextView trickName;
    private TextView goBackButton;
    private TextView nextTrickArrow;
    private TextView prevTrickArrow;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tricks);
        registerOnEvents();
        injectDependencies();
        initViewsReferences();
        initPager();
        initGoBackButton();
        initNextTrickButton();
        initPrevTrickButton();
        attachFonts();
    }

    private void registerOnEvents() {
        EventBus.getDefault().register(this);
    }

    private void initViewsReferences() {
        trickName = (TextView) findViewById(R.id.trick_name);
        goBackButton = (TextView) findViewById(R.id.toolbar_back_button);
        nextTrickArrow = (TextView) findViewById(R.id.next_trick_arrow);
        prevTrickArrow = (TextView) findViewById(R.id.prev_trick_arrow);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    private void initGoBackButton() {
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void initNextTrickButton() {
        nextTrickArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tricksPager.setCurrentItem(tricksPager.getCurrentItem() + 1);
            }
        });
    }

    private void initPrevTrickButton() {
        prevTrickArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tricksPager.setCurrentItem(tricksPager.getCurrentItem() - 1);
            }
        });
    }

    private void injectDependencies() {
        ((SkatingApplication)getApplication()).inject(this);
    }

    private void attachFonts() {
        toolbarTitle.setTypeface(fontCollection.getBigNoodleTitling());
        trickName.setTypeface(fontCollection.getBigNoodleTitling());
        goBackButton.setTypeface(fontCollection.getBariolBold());
        prevTrickArrow.setTypeface(fontCollection.getBariolBold());
        nextTrickArrow.setTypeface(fontCollection.getBariolBold());
    }

    private void initPager() {
        tricksPager = (ViewPager) findViewById(R.id.tricks_pager);
        tricksAdapter = new TricksAdapter(getSupportFragmentManager(), tricksService.getAllTricks());
        tricksPager.setAdapter(tricksAdapter);
        tricksPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
            @Override public void onPageScrollStateChanged(int state) { }
            @Override
            public void onPageSelected(int position) {
                updateDisplayedTrick();
            }
        });
        updateDisplayedTrick();
    }

    private void updateDisplayedTrick() {
        Log.i("Skating", ">>>>>> Update displayed trick called!");
        TricksAdapter adapter = (TricksAdapter) tricksPager.getAdapter();
        Trick displayedTrick = adapter.getTricks().get(tricksPager.getCurrentItem());
        EventBus.getDefault().post(new DisplayedTrickChangedEvent(displayedTrick));
    }

    @Subscribe
    public void onEvent(DisplayedTrickChangedEvent event) {
        trickName.setText(event.getTrick().getName());
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    private void goBack() {
        navigator.goBackWithSlideAnimation(ShowTricksActivity.this, MainActivity.class);
    }

}
