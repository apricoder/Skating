package com.servocode.skating.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.servocode.skating.BuildConfig;
import com.servocode.skating.R;
import com.servocode.skating.testutils.TestApplication;
import com.servocode.skating.tricks.Trick;
import com.servocode.skating.tricks.Tricks;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class, constants = BuildConfig.class, sdk = 23, manifest = "src/main/AndroidManifest.xml")
public class ShowTricksActivityTest {

    private ShowTricksActivity showTricksActivity;

    @Test
    public void should_show_first_trick_name_on_start() throws Exception {
        setupActivityWith(tricks("Jump"));
        assertEquals("Jump", getDisplayedTrickName());
    }

    @Test
    public void click_on_right_arrow_shows_next_trick() throws Exception {
        setupActivityWith(tricks("Jump", "Fall"));
        Button nextTrickArrow = (Button) showTricksActivity.findViewById(R.id.next_trick_arrow);

        nextTrickArrow.performClick();

        assertEquals("Fall", getDisplayedTrickName());
    }

    private CharSequence getDisplayedTrickName() {
        Fragment fragment = getCurrentTrickFragmentFrom(showTricksActivity);
        View view = fragment.getView();
        TextView trickName = (TextView) view.findViewById(R.id.trick_name);
        return trickName.getText();
    }

    private void setupActivityWith(List<Trick> tricks) {
        TestApplication application = (TestApplication) RuntimeEnvironment.application;
        application.setTricks(new TestTricks(tricks));
        showTricksActivity = Robolectric.setupActivity(ShowTricksActivity.class);
    }

    private List<Trick> tricks(String... names) {
        List<Trick> result = new ArrayList<>();
        for (String shortName : names) {
            result.add(new Trick(shortName, shortName.toLowerCase()));
        }
        return result;
    }

    private Fragment getCurrentTrickFragmentFrom(ShowTricksActivity activity) {
        ViewPager tricksPager = (ViewPager) activity.findViewById(R.id.tricks_pager);
        Fragment fragment = getCurrentItemFrom(tricksPager);
        SupportFragmentTestUtil.startFragment(fragment);
        return fragment;
    }

    private Fragment getCurrentItemFrom(ViewPager tricksPager) {
        return ((FragmentStatePagerAdapter) tricksPager.getAdapter()).getItem(tricksPager.getCurrentItem());
    }

    private class TestTricks implements Tricks {
        private List<Trick> tricks;

        public TestTricks(List<Trick> tricks) {

            this.tricks = tricks;
        }

        @Override
        public List<Trick> getAll() {
            return tricks;
        }
    }
}