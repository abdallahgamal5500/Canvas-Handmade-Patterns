package com.e.handmade_patterns.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.e.handmade_patterns.databinding.ActivityHomeBinding;
import com.e.handmade_patterns.fragments.FragmentChoose;
import com.e.handmade_patterns.fragments.FragmentPalette;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.helper.Help;
import com.e.handmade_patterns.interfaces.Communicator;
import com.e.handmade_patterns.interfaces.IOnBackPressed;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Home extends AppCompatActivity implements Communicator{

    private ActivityHomeBinding binding;
    private Help help;
    private AdView mAdView;
    public static Fragment CURRENT_FRAGMENT = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        //this line to disable darkmood
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        showFragment(FragmentChoose.getInstance());

        help = new Help(getApplicationContext(),this);

        mAdView = findViewById(R.id.home_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /*private void showFragmentHere (Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_framelayout,fragment)
                .commit();
    }*/

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_framelayout, fragment)
                .commit();
    }

    @Override
    public void hideToalbar() {
        binding.homeToolbarLayout.setVisibility(View.GONE);
    }

    @Override
    public void showToalbar() {
        binding.homeToolbarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTools() {
        binding.homeToolsCardview.setVisibility(View.GONE);
    }

    @Override
    public void showTools() {
        binding.homeToolsCardview.setVisibility(View.VISIBLE);
    }

    @Override
    public void handleToalsPen() {
        binding.homeTools.toolsPen.setBackgroundColor(Constants.CLICK_COLOR);
        binding.homeTools.toolsEraser.setBackgroundColor(Constants.COLOR_ACCENT);
        Constants.CURRENT_COLOR = Constants.PEN_COLOR;
        Constants.TOOLS_STATE[0] = true;
        Constants.TOOLS_STATE[1] = false;
    }

    @Override
    public void handleToalsEraser() {
        binding.homeTools.toolsPen.setBackgroundColor(Constants.COLOR_ACCENT);
        binding.homeTools.toolsEraser.setBackgroundColor(Constants.CLICK_COLOR);
        Constants.CURRENT_COLOR = Constants.DELETE_COLOR;
        Constants.TOOLS_STATE[0] = false;
        Constants.TOOLS_STATE[1] = true;
    }

    @Override
    public void handleToalsPalette() {
        showFragment(FragmentPalette.getInstance());
    }

    @Override
    public void handleToalsSave() {
        hideTools();
        help.saveAndTakeScreenShot();
        showTools();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_framelayout);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}