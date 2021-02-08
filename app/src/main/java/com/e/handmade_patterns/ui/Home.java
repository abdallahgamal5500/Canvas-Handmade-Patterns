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
import com.e.handmade_patterns.fragments.FragmentSquare;
import com.e.handmade_patterns.interfaces.Communicator;
import com.e.handmade_patterns.interfaces.IOnBackPressed;

public class Home extends AppCompatActivity implements Communicator,View.OnClickListener{

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home);

        //this line to disable darkmood
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        showFragmentHere(FragmentChoose.getInstance());
    }

    private void showFragmentHere (Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_framelayout,fragment)
                .commit();
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.home_framelayout, fragment)
                .addToBackStack(null)
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
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_framelayout);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            if (fragment instanceof FragmentSquare) {
                hideToalbar();
                hideTools();
            }
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_reload:
        }
    }
}