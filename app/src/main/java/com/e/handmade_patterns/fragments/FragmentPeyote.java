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
import com.e.handmade_patterns.databinding.FragmentPeyoteBinding;
import com.e.handmade_patterns.helper.Constants;
import com.e.handmade_patterns.helper.Help;
import com.e.handmade_patterns.interfaces.Communicator;
import com.e.handmade_patterns.interfaces.IOnBackPressed;
import com.e.handmade_patterns.ui.Home;

public class FragmentPeyote extends Fragment implements View.OnClickListener, IOnBackPressed {

    private FragmentPeyoteBinding binding;
    private Communicator communicator;
    private View view;
    private Help help;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ImageView tools_pen,tools_eraser,tools_palette,tools_zoom_in,tools_zoom_out,toolbar_reload,toolbar_save_btn;

    public FragmentPeyote() {
    }

    public static FragmentPeyote getInstance() {
        FragmentPeyote fragmentPeyote = null;
        if (fragmentPeyote == null)
            fragmentPeyote = new FragmentPeyote();
        return fragmentPeyote;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_peyote, container, false);
        view = binding.getRoot();

        // this line to show the left tools layout
        communicator.showToalbar();
        communicator.showTools();

        preferences = getActivity().getSharedPreferences(Constants.DATABASE_NAME, Context.MODE_PRIVATE);
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

        binding.peyoteParentLayout.setLayoutParams(params);

        communicator.handleToalsPen();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (Communicator) context;
        Constants.PEYOTE_CONTEXT = getContext();
        help = new Help(context,getActivity());
        Home.CURRENT_FRAGMENT = FragmentPeyote.getInstance();
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
                binding.peyoteCanvas.zoomIn();
                setCanvasSize();
                break;
            case R.id.tools_zoom_out:
                binding.peyoteCanvas.zoomOut();
                setCanvasSize();
                break;
            case R.id.toolbar_reload:
                help.showReloadDialog(FragmentPeyote.getInstance());
                for (int i=0;i<Constants.PEYOTE_COLUMNS_COUNT_CURRENT * Constants.PEYOTE_RAWS_COUNT_CURRENT;i++)
                    editor.remove(Constants.PEYOTE_COLOR_DB+i);

                editor.commit();
                break;
            case R.id.toolbar_save_btn:
                communicator.handleToalsSave();
                break;
        }
    }

    private void setCanvasSize() {
        // these lines to set the size of the canvas dinamicly
        binding.peyoteCanvas.setMinimumHeight(Constants.PEYOTE_RAWS_COUNT_CURRENT*Constants.PEYOTE_HEIGHT_SIZE+Constants.PEYOTE_HEIGHT_SIZE/2);
        binding.peyoteCanvas.setMinimumWidth(Constants.PEYOTE_COLUMNS_COUNT_CURRENT*Constants.PEYOTE_WIDTH_SIZE);
    }

    @Override
    public boolean onBackPressed() {
        return help.showBackDialog(FragmentChoose.getInstance());
    }
}