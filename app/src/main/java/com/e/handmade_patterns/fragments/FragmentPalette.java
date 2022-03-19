package com.e.handmade_patterns.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.e.handmade_patterns.databinding.FragmentPaletteBinding;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.helper.Help;
import com.e.handmade_patterns.interfaces.Communicator;
import com.e.handmade_patterns.interfaces.IOnBackPressed;
import com.e.handmade_patterns.ui.Home;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class FragmentPalette extends Fragment implements View.OnClickListener, IOnBackPressed {

    private FragmentPaletteBinding binding;
    private View view;
    private Help help;
    private Communicator communicator;
    private ImageView imagesArray [] = new ImageView[6];
    private ColorEnvelope colorEnvelope;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private InterstitialAd mInterstitialAd;

    public FragmentPalette() {
    }

    public static FragmentPalette getInstance() {
        FragmentPalette fragmentPalette = null;
        if (fragmentPalette == null)
            fragmentPalette = new FragmentPalette();
        return fragmentPalette;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_palette, container, false);
        view = binding.getRoot();
        //showAd();

        // this line to show the left tools layout
        communicator.hideToalbar();
        communicator.hideTools();

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

        binding.paletteBtn.setOnClickListener(this);

        for (int i=1;i<=6;i++) {
            int id = getResources().getIdentifier("palette_recent" + i, "id", getContext().getPackageName());
            CardView temp = view.findViewById(id);
            temp.setOnClickListener(FragmentPalette.this);
            int imgId = getResources().getIdentifier("palette_recent" + i + "_img", "id", getContext().getPackageName());
            ImageView temp2 = view.findViewById(imgId);
            imagesArray[i - 1] = temp2;
            imagesArray[i - 1].setOnClickListener(FragmentPalette.this);

            if (Integer.parseInt(preferences.getString("" + (i-1), Color.WHITE + "")) == Color.WHITE) {
                temp.setVisibility(View.INVISIBLE);
            } else {
                temp.setCardBackgroundColor(Integer.parseInt(preferences.getString(""+(i-1),Color.WHITE+"")));
                temp.setVisibility(View.VISIBLE);
            }
        }

        for (int i=1;i<=6;i++) {
            int id = getResources().getIdentifier("palette_recent"+i+"_img", "id", getContext().getPackageName());
            ImageView temp = view.findViewById(id);
            temp.setOnClickListener(FragmentPalette.this);
        }

        binding.paletteColorPickerView.setColorListener(new ColorEnvelopeListener() {
            @Override
            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                for (int i=0;i<6;i++)
                    imagesArray[i].setVisibility(View.INVISIBLE);

                colorEnvelope = envelope;
                // attach alphaSlideBar
                binding.paletteColorPickerView.attachAlphaSlider(binding.paletteAlphaSlideBar);

                // attach brightnessSlideBar
                binding.paletteColorPickerView.attachBrightnessSlider(binding.paletteBrightnessSlide);

                binding.paletteTextview.setTextColor(envelope.getColor());
                binding.paletteTextview.setText("#"+envelope.getHexCode());
                binding.paletteAlphaTileView.setPaintColor(envelope.getColor());

                if (colorEnvelope.getColor() != -1)
                    Constants.PEN_COLOR = colorEnvelope.getColor();
            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.palette_btn:
                int i;
                boolean choseRecentColor = false;
                Constants.CURRENT_COLOR = Constants.PEN_COLOR;
                Constants.TOOLS_STATE[2] = true;

                for (i = 0; i < 6; i++) {
                    if (Integer.parseInt(preferences.getString(""+i,Color.WHITE+"")) == Constants.PEN_COLOR)
                        choseRecentColor = true;
                    if (Integer.parseInt(preferences.getString(""+i,Color.WHITE+""))  == Color.WHITE)
                        break;
                }

                if (!choseRecentColor) {
                    if (i==6) {
                        for (int j=0;j<5;j++) {
                            editor.putString(""+j,preferences.getString(""+(j+1),""+Color.WHITE));
                        }
                        editor.putString("5", Constants.PEN_COLOR+"");
                    } else {
                        editor.putString(""+i,""+ Constants.PEN_COLOR);
                    }
                }
                editor.commit();
                communicator.showFragment(Home.CURRENT_FRAGMENT);
                break;
            case R.id.palette_recent1:
                handleCardClicking(0);
                break;
            case R.id.palette_recent2:
                handleCardClicking(1);
                break;
            case R.id.palette_recent3:
                handleCardClicking(2);
                break;
            case R.id.palette_recent4:
                handleCardClicking(3);
                break;
            case R.id.palette_recent5:
                handleCardClicking(4);
                break;
            case R.id.palette_recent6:
                handleCardClicking(5);
                break;
        }
    }

    private void handleCardClicking (int index) {
        for (int i=0;i<6;i++)
            imagesArray[i].setVisibility(View.INVISIBLE);
        Constants.PEN_COLOR = Integer.parseInt(preferences.getString(""+index,Color.WHITE+""));
        binding.paletteTextview.setTextColor(Constants.PEN_COLOR);
        binding.paletteTextview.setText("#"+Integer.toHexString(Constants.PEN_COLOR));
        binding.paletteAlphaTileView.setPaintColor(Constants.PEN_COLOR);
        imagesArray[index].setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
        help = new Help(context,getActivity());
    }

    @Override
    public boolean onBackPressed() {
        return help.showBackDialog(Home.CURRENT_FRAGMENT);
    }
}