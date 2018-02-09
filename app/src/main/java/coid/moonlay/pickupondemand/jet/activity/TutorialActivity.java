package coid.moonlay.pickupondemand.jet.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.fragment.PickupTutorialSlideFragment;

public class TutorialActivity extends AppIntro2
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addSlide(PickupTutorialSlideFragment.newInstance(R.layout.fragment_pickup_tutorial_slide_01, R.color.colorPrimary, R.id.rl_container));
        addSlide(PickupTutorialSlideFragment.newInstance(R.layout.fragment_pickup_tutorial_slide_02, R.color.colorInfo, R.id.rl_container));
        addSlide(PickupTutorialSlideFragment.newInstance(R.layout.fragment_pickup_tutorial_slide_03, R.color.colorTextDisable, R.id.rl_container));
        addSlide(PickupTutorialSlideFragment.newInstance(R.layout.fragment_pickup_tutorial_slide_04, R.color.colorInfo, R.id.rl_container));
        addSlide(PickupTutorialSlideFragment.newInstance(R.layout.fragment_pickup_tutorial_slide_05, R.color.colorSuccess, R.id.rl_container));
        setColorTransitionsEnabled(true);
        updateFirstTimeOpenedState();
    }

    @Override
    public void onBackPressed()
    {
        updateFirstTimeOpenedState();
        super.onBackPressed();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment)
    {
        super.onSkipPressed(currentFragment);
        updateFirstTimeOpenedState();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment)
    {
        super.onDonePressed(currentFragment);
        updateFirstTimeOpenedState();
        finish();
    }

    private void updateFirstTimeOpenedState()
    {
        SharedPreferences pref = getSharedPreferences(AppConfig.JET_SHARED_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(AppConfig.FIRST_TIME_OPENED_PARAM_KEY, false);
        editor.apply();
    }
}