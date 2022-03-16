package com.e.handmade_patterns.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.e.handmade_patterns.databinding.FragmentSquareBinding;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.helper.Help;
import com.e.handmade_patterns.interfaces.Communicator;
import com.e.handmade_patterns.interfaces.IOnBackPressed;
import com.e.handmade_patterns.ui.Home;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class FragmentSquare extends Fragment implements View.OnClickListener, IOnBackPressed {

    private FragmentSquareBinding binding;
    private Communicator communicator;
    private View view;
    private Help help;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageView tools_pen,tools_eraser,tools_palette,tools_zoom_in,tools_zoom_out,toolbar_reload,toolbar_save_btn;
    private InterstitialAd mInterstitialAd;

    public FragmentSquare() {

    }

    public static FragmentSquare getInstance() {
        FragmentSquare fragmentSquare = null;
        if (fragmentSquare == null)
            fragmentSquare = new FragmentSquare();
        return fragmentSquare;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_square, container, false);
        view = binding.getRoot();
        showAd();
        // this line to show the left tools layout
        communicator.showToalbar();
        communicator.showTools();

        preferences = getActivity().getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        // these fun sets the size of the canvas dinamicly
        setCanvasSize();

        tools_pen = getActivity().findViewById(R.id.tools_pen);
        tools_eraser = getActivity().findViewById(R.id.tools_eraser);
        tools_palette = getActivity().findViewById(R.id.tools_palette);
        tools_zoom_in = getActivity().findViewById(R.id.tools_zoom_in);
        tools_zoom_out = getActivity().findViewById(R.id.tools_zoom_out);
        toolbar_reload = getActivity().findViewById(R.id.toolbar_reload);
        toolbar_save_btn = getActivity().findViewById(R.id.toolbar_save_btn);

        tools_pen.setOnClickListener(this);
        tools_eraser.setOnClickListener(this);
        tools_palette.setOnClickListener(this);
        tools_zoom_in.setOnClickListener(this);
        tools_zoom_out.setOnClickListener(this);
        toolbar_reload.setOnClickListener(this);
        toolbar_save_btn.setOnClickListener(this);

        // these lines to handle the margen left of the canvas
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT,
                CoordinatorLayout.LayoutParams.MATCH_PARENT);

        params.setMargins(getResources().getDimensionPixelSize(R.dimen._30sdp), 0, 0, 0);

        binding.squareParentLayout.setLayoutParams(params);

        communicator.handleToalsPen();

        return view;
    }

    private void showAd() {
        // Interstitial Ad
        AdRequest adRequest= new AdRequest.Builder().build();
        InterstitialAd.load(getContext(), "ca-app-pub-7416399602135775/2814481978", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.show(getActivity());
                    }
                });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
        Constants.SQUARE_CONTEXT = getContext();
        help = new Help(context,getActivity());
        Home.CURRENT_FRAGMENT = FragmentSquare.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tools_pen:
                communicator.handleToalsPen();
                break;
            case R.id.tools_eraser:
                communicator.handleToalsEraser();
                break;
            case R.id.tools_palette:
                communicator.handleToalsPalette();
                break;
            case R.id.tools_zoom_in:
                binding.squareCanvas.zoomIn();
                setCanvasSize();
                break;
            case R.id.tools_zoom_out:
                binding.squareCanvas.zoomOut();
                setCanvasSize();
                break;
            case R.id.toolbar_reload:
                help.showReloadDialog(FragmentSquare.getInstance());
                for (int i=0;i<Constants.SQUARE_COLUMNS_COUNT_CURRENT * Constants.SQUARE_RAWS_COUNT_CURRENT;i++)
                    editor.remove(Constants.SQUARE_COLOR_DB+i);

                editor.commit();
                break;
            case R.id.toolbar_save_btn:
                communicator.handleToalsSave();
                break;
        }
    }

    private void setCanvasSize() {
        // these lines to set the size of the canvas dinamicly
        binding.squareCanvas.setMinimumHeight(Constants.SQUARE_RAWS_COUNT_CURRENT * Constants.SQUARE_ITEM_SIZE);
        binding.squareCanvas.setMinimumWidth(Constants.SQUARE_COLUMNS_COUNT_CURRENT * Constants.SQUARE_ITEM_SIZE);
    }

    @Override
    public boolean onBackPressed() {
        return help.showBackDialog(FragmentChoose.getInstance());
    }
}