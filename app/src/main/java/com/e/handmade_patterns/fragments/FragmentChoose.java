package com.e.handmade_patterns.fragments;

import android.content.Context;
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
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class FragmentChoose extends Fragment {

    private FragmentChooseBinding binding;
    private Communicator communicator;
    private ArrayAdapter<CharSequence> adapter1;
    private ArrayList<Integer> integerArrayList = new ArrayList<>();
    private View view;

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

        adapter1 = ArrayAdapter.createFromResource(getContext(),R.array.choose_spinner_1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.chooseSpinner1.setAdapter(adapter1);
        for (int i=0;i<500;i++) {
            integerArrayList.add(i+1);
        }
        binding.chooseSpinner2.setItems(integerArrayList);
        binding.chooseSpinner3.setItems(integerArrayList);

        binding.chooseSpinner1.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
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
                else if (binding.chooseSpinner3.getText().toString().equals(Constants.AR_CHOOSE_SPINNER3_TEXT) || binding.chooseSpinner3.getText().toString().equals(Constants.EN_CHOOSE_SPINNER3_TEXT))
                    binding.chooseSpinner3.setHintColor(Color.RED);
                else {
                    //setViewsState(false);
                    switch (binding.chooseSpinner1.getSelectedIndex()) {
                        case 0:
                            break;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            Constants.SQUARE_RAWS_COUNT = Integer.parseInt(binding.chooseSpinner2.getText().toString());
                            Constants.SQUARE_COLUMNS_COUNT = Integer.parseInt(binding.chooseSpinner3.getText().toString());
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