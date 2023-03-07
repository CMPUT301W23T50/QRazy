package com.example.qrazy;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Test to check if a user could switch between the main activity and the qr scan activity
     * using the buttons on the main screen
     */
    @Test
    public void switchToQRScanFromMain() {
        // ensure we start at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        // click on the scan qr-code button
        solo.clickOnView(solo.getView(R.id.scan_qr_button));
        // ensure we're at the scan qr-code activity
        solo.assertCurrentActivity("Wrong activity",ScanCodeActivity.class);
        // go back to main activity from the scanning activity
        solo.goBack();
        // check if we're at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

    /**
     * Test to check if a user could switch between the main activity and the map activity
     * using the buttons on the main screen
     */
    @Test
    public void switchToMapFromMain() {
        // ensure we start at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        // click on the map button
        solo.clickOnView(solo.getView(R.id.map_button));
        // ensure we're at the map activity
        solo.assertCurrentActivity("Wrong activity",MapActivity.class);
        // go back to main activity
        solo.goBack();
        // check if we're at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

    /**
     * Test to check if a user could switch between the main activity and the user profile
     * activity using the buttons on the main screen
     */
    @Test
    public void switchToUserProfileFromMain() {
        // ensure we start at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        // click on the user profile button
        solo.clickOnView(solo.getView(R.id.user_profile_button));
        // ensure we're at the user profile activity
        solo.assertCurrentActivity("Wrong activity",UserProfileActivity.class);
        // go back to main activity
        solo.goBack();
        // check if we're at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

    /**
     * Test to check if a user could switch between the main activity and the user leaderboard
     * activity using the buttons on the main screen
     */
    @Test
    public void switchToUserLeaderboardFromMain() {
        // ensure we start at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        // click on the user leaderboard button
        solo.clickOnView(solo.getView(R.id.user_leaderboard_button));
        // ensure we're at the user leaderboard activity
        solo.assertCurrentActivity("Wrong activity",UserLeaderboardActivity.class);
        // go back to main activity
        solo.goBack();
        // check if we're at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

    /**
     * Test to check if a user could switch between the main activity and the qr leaderboard
     * activity using the buttons on the main screen
     */
    @Test
    public void switchToQRLeaderboardFromMain() {
        // ensure we start at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
        // click on the qr leaderboard button
        solo.clickOnView(solo.getView(R.id.qr_leaderboard_button));
        // ensure we're at the qr leaderboard activity
        solo.assertCurrentActivity("Wrong activity",QRLeaderboardActivity.class);
        // go back to main activity
        solo.goBack();
        // check if we're at the main activity
        solo.assertCurrentActivity("Wrong activity",MainActivity.class);
    }

}
