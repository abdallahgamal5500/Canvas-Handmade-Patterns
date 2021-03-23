package com.e.handmade_patterns.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.e.handmade_patterns.R;
import com.e.handmade_patterns.databinding.FragmentChooseBinding;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.interfaces.Communicator;

import java.util.ArrayList;

public class FragmentChoose extends Fragment {

    private FragmentChooseBinding binding;
    private Communicator communicator;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    private View view;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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

        // these two lines to hide the left tools layout
        communicator.hideToalbar();
        communicator.hideTools();

        preferences = getActivity().getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.choose_spinner_1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.chooseSpinner1.setAdapter(adapter1);
        for (int i=0;i<200;i++) {
            integerArrayList.add(i+1);
        }
        binding.chooseSpinner2.setItems(integerArrayList);
        binding.chooseSpinner3.setItems(integerArrayList);

        binding.chooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.chooseSpinner1.getText().toString().equals(Constants.AR_CHOOSE_SPINNER1_TEXT) || binding.chooseSpinner1.getText().toString().equals(Constants.EN_CHOOSE_SPINNER1_TEXT))
                    binding.chooseSpinner1.setHintColor(Color.RED);
                else if (binding.chooseSpinner2.getText().toString().equals(Constants.AR_CHOOSE_SPINNER2_TEXT) || binding.chooseSpinner2.getText().toString().equals(Constants.EN_CHOOSE_SPINNER2_TEXT))
                    binding.chooseSpinner2.setHintColor(Color.RED);
                else if (binding.chooseSpinner3.getText().toString().equals(Constants.AR_CHOOSE_SPINNER3_TEXT) || binding.chooseSpinner3.getText().toString().equals(Constants.EN_CHOOSE_SPINNER3_TEXT))
                    binding.chooseSpinner3.setHintColor(Color.RED);
                else {
                    //setViewsState(false);
                    switch (binding.chooseSpinner1.getSelectedIndex()) {
                        case 0:
                            Constants.BRICK_RAWS_COUNT = Integer.parseInt(binding.chooseSpinner2.getText().toString());
                            Constants.BRICK_COLUMNS_COUNT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                            if (preferences.getInt(Constants.BRICK_RAWS_COUNT_DB,-1) == -1) {
                                // u dont have database
                                Constants.BRICK_RAWS_COUNT_CURRENT = Constants.BRICK_RAWS_COUNT;
                                Constants.BRICK_COLUMNS_COUNT_CURRENT = Constants.BRICK_COLUMNS_COUNT;
                                editor.putInt(Constants.BRICK_RAWS_COUNT_DB, Constants.BRICK_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.BRICK_COLUMNS_COUNT_DB, Constants.BRICK_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            } else {
                                // u already have database
                                Constants.BRICK_RAWS_COUNT_CURRENT = preferences.getInt(Constants.BRICK_RAWS_COUNT_DB,-1);
                                Constants.BRICK_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.BRICK_COLUMNS_COUNT_DB,-1);
                            }
                            communicator.showFragment(FragmentBrick.getInstance());
                            break;
                        case 1:
                            Constants.PEYOTE_RAWS_COUNT = Integer.parseInt(binding.chooseSpinner2.getText().toString());
                            Constants.PEYOTE_COLUMNS_COUNT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                            if (preferences.getInt(Constants.PEYOTE_RAWS_COUNT_DB,-1) == -1) {
                                // u dont have database
                                Constants.PEYOTE_RAWS_COUNT_CURRENT = Constants.PEYOTE_RAWS_COUNT;
                                Constants.PEYOTE_COLUMNS_COUNT_CURRENT = Constants.PEYOTE_COLUMNS_COUNT;
                                editor.putInt(Constants.PEYOTE_RAWS_COUNT_DB, Constants.PEYOTE_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.PEYOTE_COLUMNS_COUNT_DB, Constants.PEYOTE_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            } else {
                                // u already have database
                                Constants.PEYOTE_RAWS_COUNT_CURRENT = preferences.getInt(Constants.PEYOTE_RAWS_COUNT_DB,-1);
                                Constants.PEYOTE_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.PEYOTE_COLUMNS_COUNT_DB,-1);
                            }
                            communicator.showFragment(FragmentPeyote.getInstance());
                            break;
                        case 2:
                            Constants.RAW1_RAWS_COUNT = Integer.parseInt(binding.chooseSpinner2.getText().toString());
                            Constants.RAW1_COLUMNS_COUNT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                            if (preferences.getInt(Constants.RAW1_RAWS_COUNT_DB,-1) == -1) {
                                // u dont have database
                                Constants.RAW1_RAWS_COUNT_CURRENT = Constants.RAW1_RAWS_COUNT;
                                Constants.RAW1_COLUMNS_COUNT_CURRENT = Constants.RAW1_COLUMNS_COUNT;
                                editor.putInt(Constants.RAW1_RAWS_COUNT_DB, Constants.RAW1_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.RAW1_COLUMNS_COUNT_DB, Constants.RAW1_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            } else {
                                // u already have database
                                Constants.RAW1_RAWS_COUNT_CURRENT = preferences.getInt(Constants.RAW1_RAWS_COUNT_DB,-1);
                                Constants.RAW1_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.RAW1_COLUMNS_COUNT_DB,-1);
                            }
                            communicator.showFragment(FragmentRaw1.getInstance());
                            break;
                        case 3:
                            Constants.SQUARE_RAWS_COUNT = Integer.parseInt(binding.chooseSpinner2.getText().toString());
                            Constants.SQUARE_COLUMNS_COUNT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
                            if (preferences.getInt(Constants.SQUARE_RAWS_COUNT_DB,-1) == -1) {
                                // u dont have database
                                Constants.SQUARE_RAWS_COUNT_CURRENT = Constants.SQUARE_RAWS_COUNT;
                                Constants.SQUARE_COLUMNS_COUNT_CURRENT = Constants.SQUARE_COLUMNS_COUNT;
                                editor.putInt(Constants.SQUARE_RAWS_COUNT_DB, Constants.SQUARE_RAWS_COUNT_CURRENT);
                                editor.putInt(Constants.SQUARE_COLUMNS_COUNT_DB, Constants.SQUARE_COLUMNS_COUNT_CURRENT);
                                editor.commit();
                            } else {
                                // u already have database
                                Constants.SQUARE_RAWS_COUNT_CURRENT = preferences.getInt(Constants.SQUARE_RAWS_COUNT_DB,-1);
                                Constants.SQUARE_COLUMNS_COUNT_CURRENT = preferences.getInt(Constants.SQUARE_COLUMNS_COUNT_DB,-1);
                            }
                            communicator.showFragment(FragmentSquare.getInstance());
                            break;
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
    }
}