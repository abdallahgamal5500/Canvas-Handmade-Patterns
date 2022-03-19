package com.e.handmade_patterns.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.e.handmade_patterns.databinding.FragmentChooseBinding;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.interfaces.Communicator;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentChoose extends Fragment {

    private FragmentChooseBinding binding;
    private Communicator communicator;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private ArrayList<String> stringArrayListSpinner1 = new ArrayList<>();
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    private View view;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private InterstitialAd mInterstitialAd;

    public FragmentChoose() {

    }

    public static FragmentChoose getInstance() {
        FragmentChoose fragmentChoose = null;
        if (fragmentChoose == null)
            fragmentChoose = new FragmentChoose();
        return fragmentChoose;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_choose, container, false);
        view = binding.getRoot();
        //showAd();

        // these two lines to hide the left tools layout
        communicator.hideToalbar();
        communicator.hideTools();

        preferences = getActivity().getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        binding.chooseCard2.setVisibility(View.GONE);
        binding.chooseCard3.setVisibility(View.GONE);
        binding.chooseCard4.setVisibility(View.GONE);

        if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
            stringArrayListSpinner1.add(Constants.AR_CHOOSE_SPINNER1_BRICK);
            stringArrayListSpinner1.add(Constants.AR_CHOOSE_SPINNER1_PEYOTE);
            stringArrayListSpinner1.add(Constants.AR_CHOOSE_SPINNER1_RAW1);
            stringArrayListSpinner1.add(Constants.AR_CHOOSE_SPINNER1_SQUARE);

            stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
            stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_CURRENT);

        } else {
            stringArrayListSpinner1.add(Constants.EN_CHOOSE_SPINNER1_BRICK);
            stringArrayListSpinner1.add(Constants.EN_CHOOSE_SPINNER1_PEYOTE);
            stringArrayListSpinner1.add(Constants.EN_CHOOSE_SPINNER1_RAW1);
            stringArrayListSpinner1.add(Constants.EN_CHOOSE_SPINNER1_SQUARE);

            stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
            stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_CURRENT);
        }

        for (int i=1;i<=200;i++)
            integerArrayList.add(i);

        binding.chooseSpinner1.setItems(stringArrayListSpinner1);
        binding.chooseSpinner2.setItems(stringArrayList);
        binding.chooseSpinner3.setItems(integerArrayList);
        binding.chooseSpinner4.setItems(integerArrayList);

        binding.chooseSpinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                binding.chooseCard2.setVisibility(View.GONE);
                binding.chooseCard3.setVisibility(View.GONE);
                binding.chooseCard4.setVisibility(View.GONE);

                switch (position) {
                    case 0:
                        if (preferences.getInt(Constants.BRICK_RAWS_COUNT_DB,-1) == -1) {
                            // u dont have database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        } else {
                            // u have a database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_CURRENT);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_CURRENT);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 1:
                        if (preferences.getInt(Constants.PEYOTE_RAWS_COUNT_DB,-1) == -1) {
                            // u dont have database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        } else {
                            // u have a database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_CURRENT);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_CURRENT);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        if (preferences.getInt(Constants.RAW1_RAWS_COUNT_DB,-1) == -1) {
                            // u dont have database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        } else {
                            // u have a database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_CURRENT);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_CURRENT);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 3:
                        if (preferences.getInt(Constants.SQUARE_RAWS_COUNT_DB,-1) == -1) {
                            // u dont have database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        } else {
                            // u have a database
                            stringArrayList.clear();
                            if (Locale.getDefault().getDisplayLanguage().equals("العربية")) {
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.AR_CHOOSE_SPINNER2_GO_CURRENT);
                            } else {
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_NEW);
                                stringArrayList.add(Constants.EN_CHOOSE_SPINNER2_GO_CURRENT);
                            }
                            binding.chooseSpinner2.setItems(stringArrayList);
                            binding.chooseCard2.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            }
        });

        binding.chooseSpinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        binding.chooseCard3.setVisibility(View.VISIBLE);
                        binding.chooseCard4.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        binding.chooseCard3.setVisibility(View.GONE);
                        binding.chooseCard4.setVisibility(View.GONE);
                }
            }
        });

        binding.chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.chooseSpinner1.getText().toString().equals(Constants.AR_CHOOSE_SPINNER1_TEXT) || binding.chooseSpinner1.getText().toString().equals(Constants.EN_CHOOSE_SPINNER1_TEXT))
                    binding.chooseSpinner1.setHintColor(Color.RED);
                else if (binding.chooseSpinner2.getText().toString().equals(Constants.AR_CHOOSE_SPINNER2_TEXT) || binding.chooseSpinner2.getText().toString().equals(Constants.EN_CHOOSE_SPINNER2_TEXT))
                    binding.chooseSpinner2.setHintColor(Color.RED);
                else if (binding.chooseSpinner3.isShown() && (binding.chooseSpinner3.getText().toString().equals(Constants.AR_CHOOSE_SPINNER3_TEXT) || binding.chooseSpinner3.getText().toString().equals(Constants.EN_CHOOSE_SPINNER3_TEXT)))
                    binding.chooseSpinner3.setHintColor(Color.RED);
                else if (binding.chooseSpinner4.isShown() && (binding.chooseSpinner4.getText().toString().equals(Constants.AR_CHOOSE_SPINNER4_TEXT) || binding.chooseSpinner4.getText().toString().equals(Constants.EN_CHOOSE_SPINNER4_TEXT)))
                    binding.chooseSpinner4.setHintColor(Color.RED);
                else {
                    switch (binding.chooseSpinner1.getSelectedIndex()) {
                        case 0:
                            Constants.BRICK_RAWS_COUNT_CURRENT = preferences.getInt(Constants.BRICK_RAWS_COUNT_DB,-1);
                            Constants.BRICK_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.BRICK_COLUMNS_COUNT_DB,-1);

                            if (binding.chooseSpinner2.getSelectedIndex() == 0) {
                                // the user need a new design
                                if (Constants.BRICK_RAWS_COUNT_CURRENT != -1) {
                                    // u have database
                                    for (int i=0;i<Constants.BRICK_COLUMNS_COUNT_CURRENT * Constants.BRICK_RAWS_COUNT_CURRENT;i++)
                                        editor.remove(Constants.BRICK_COLOR_DB+i);
                                }
                                Constants.BRICK_RAWS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                                Constants.BRICK_COLUMNS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner4.getText().toString());

                                editor.putInt(Constants.BRICK_RAWS_COUNT_DB, Constants.BRICK_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.BRICK_COLUMNS_COUNT_DB, Constants.BRICK_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            }
                            Toast.makeText(getContext(), R.string.choose_fragment_toast, Toast.LENGTH_LONG).show();
                            communicator.showFragment(FragmentBrick.getInstance());
                            break;
                        case 1:
                            Constants.PEYOTE_RAWS_COUNT_CURRENT = preferences.getInt(Constants.PEYOTE_RAWS_COUNT_DB,-1);
                            Constants.PEYOTE_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.PEYOTE_COLUMNS_COUNT_DB,-1);

                            if (binding.chooseSpinner2.getSelectedIndex() == 0) {
                                // the user need a new design
                                if (Constants.PEYOTE_RAWS_COUNT_CURRENT != -1) {
                                    // u have database
                                    for (int i=0;i<Constants.PEYOTE_COLUMNS_COUNT_CURRENT * Constants.PEYOTE_RAWS_COUNT_CURRENT;i++)
                                        editor.remove(Constants.PEYOTE_COLOR_DB+i);
                                }
                                Constants.PEYOTE_RAWS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                                Constants.PEYOTE_COLUMNS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner4.getText().toString());

                                editor.putInt(Constants.PEYOTE_RAWS_COUNT_DB, Constants.PEYOTE_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.PEYOTE_COLUMNS_COUNT_DB, Constants.PEYOTE_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            }
                            Toast.makeText(getContext(), R.string.choose_fragment_toast, Toast.LENGTH_LONG).show();
                            communicator.showFragment(FragmentPeyote.getInstance());
                            break;
                        case 2:
                            Constants.RAW1_RAWS_COUNT_CURRENT = preferences.getInt(Constants.RAW1_RAWS_COUNT_DB,-1);
                            Constants.RAW1_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.RAW1_COLUMNS_COUNT_DB,-1);

                            if (binding.chooseSpinner2.getSelectedIndex() == 0) {
                                // the user need a new design
                                if (Constants.RAW1_RAWS_COUNT_CURRENT != -1) {
                                    // u have database
                                    for (int i=0;i<Constants.RAW1_COLUMNS_COUNT_CURRENT * Constants.RAW1_RAWS_COUNT_CURRENT;i++)
                                        editor.remove(Constants.RAW1_COLOR_DB+i);
                                }
                                Constants.RAW1_RAWS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner3.getText().toString())*2+1;
                                Constants.RAW1_COLUMNS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner4.getText().toString())*2+1;

                                editor.putInt(Constants.RAW1_RAWS_COUNT_DB, Constants.RAW1_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.RAW1_COLUMNS_COUNT_DB, Constants.RAW1_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            }
                            Toast.makeText(getContext(), R.string.choose_fragment_toast, Toast.LENGTH_LONG).show();
                            communicator.showFragment(FragmentRaw1.getInstance());
                            break;
                        case 3:
                            Constants.SQUARE_RAWS_COUNT_CURRENT = preferences.getInt(Constants.SQUARE_RAWS_COUNT_DB,-1);
                            Constants.SQUARE_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.SQUARE_COLUMNS_COUNT_DB,-1);

                            if (binding.chooseSpinner2.getSelectedIndex() == 0) {
                                // the user need a new design
                                if (Constants.SQUARE_RAWS_COUNT_CURRENT != -1) {
                                    // u have database
                                    for (int i=0;i<Constants.SQUARE_COLUMNS_COUNT_CURRENT * Constants.SQUARE_RAWS_COUNT_CURRENT;i++)
                                        editor.remove(Constants.SQUARE_COLOR_DB+i);
                                }
                                Constants.SQUARE_RAWS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                                Constants.SQUARE_COLUMNS_COUNT_CURRENT = Integer.parseInt(binding.chooseSpinner4.getText().toString());

                                editor.putInt(Constants.SQUARE_RAWS_COUNT_DB, Constants.SQUARE_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.SQUARE_COLUMNS_COUNT_DB, Constants.SQUARE_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            }
                            Toast.makeText(getContext(), R.string.choose_fragment_toast, Toast.LENGTH_LONG).show();
                            communicator.showFragment(FragmentSquare.getInstance());
                            break;
                        }
                    }
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }
}