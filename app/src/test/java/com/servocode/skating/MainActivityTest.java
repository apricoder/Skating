package com.servocode.skating;

import android.app.Activity;
import android.content.Intent;
import android.widget.Button;

import com.servocode.skating.activities.MainActivity;
import com.servocode.skating.activities.ShowTricksActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 23, manifest = "src/main/AndroidManifest.xml")
public class MainActivityTest {
    @Test
    public void show_trick_button_switches_to_another_activity() throws Exception {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);

        Button showTricksButton = (Button) mainActivity.findViewById(R.id.learn_tricks_button);

        showTricksButton.performClick();

        assertNextStartedActivity(mainActivity, ShowTricksActivity.class);
    }

    private void assertNextStartedActivity(Activity sourceActivity, Class<? extends Activity> expectedActivityClass) {
        Intent nextStartedActivity = Shadows.shadowOf(sourceActivity).getNextStartedActivity();
        Intent expectedIntent = new Intent(sourceActivity, expectedActivityClass);
        assertEquals(Shadows.shadowOf(expectedIntent).getIntentClass(), Shadows.shadowOf(nextStartedActivity).getIntentClass());
    }
}