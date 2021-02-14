package com.e.handmade_patterns.interfaces;

import androidx.fragment.app.Fragment;

public interface Communicator {
    void showFragment(Fragment fragment);
    void hideToalbar();
    void showToalbar();
    void hideTools();
    void showTools();
    void handleToalsPen();
    void handleToalsEraser();
    void handleToalsPalette();
    void handleToalsSave();
}
