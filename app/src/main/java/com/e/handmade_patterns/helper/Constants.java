package com.e.handmade_patterns.helper;

import android.content.Context;
import android.graphics.Color;

public class Constants {

    // Colors
    public static final int BLACK_COLOR = Color.parseColor("#121C3B");
    public static final int DELETE_COLOR = Color.WHITE;
    public static int PEN_COLOR = Color.parseColor("#FFCB41"), CURRENT_COLOR = Color.parseColor("#FFCB41");
    public static final int CLICK_COLOR = Color.parseColor("#80FFFFFF");
    public static final int COLOR_ACCENT = Color.parseColor("#FFCB41");
    public final static int DEFAULT_COLOR = Color.WHITE;
    public static boolean [] TOOLS_STATE = new boolean[3];

    // common betweet the canvas
    public static String DATABASE_NAME = "DATABASE_NAME";
    public static final int STROKE_SIZE = 5;

    // square
    public static int SQUARE_ITEM_SIZE = 50;
    public static int SQUARE_ZOOMING_RATIO = 25;
    public static int SQUARE_RAWS_COUNT = 0;
    public static int SQUARE_COLUMNS_COUNT =0;
    public static int SQUARE_RAWS_COUNT_CURRENT = 0;
    public static int SQUARE_COLUMNS_COUNT_CURRENT =0;
    public static String SQUARE_COLOR_DB = "SQUARE_COLOR";
    public static String SQUARE_RAWS_COUNT_DB = "SQUARE_RAWS_COUNT";
    public static String SQUARE_COLUMNS_COUNT_DB = "SQUARE_COLUMNS_COUNT";
    public static Context SQUARE_CONTEXT = null;

    // peyote
    public static int PEYOTE_WIDTH_SIZE = 40;
    public static int PEYOTE_HEIGHT_SIZE = 50;
    public static int PEYOTE_ZOOMING_RATIO_WIDTH = 20;
    public static int PEYOTE_ZOOMING_RATIO_HEIGHT = 25;
    public static int PEYOTE_RAWS_COUNT = 0;
    public static int PEYOTE_COLUMNS_COUNT =0;
    public static int PEYOTE_RAWS_COUNT_CURRENT = 0;
    public static int PEYOTE_COLUMNS_COUNT_CURRENT =0;
    public static String PEYOTE_COLOR_DB = "PEYOTE_COLOR";
    public static String PEYOTE_RAWS_COUNT_DB = "PEYOTE_RAWS_COUNT";
    public static String PEYOTE_COLUMNS_COUNT_DB = "PEYOTE_COLUMNS_COUNT";
    public static Context PEYOTE_CONTEXT = null;

    // choose fragment
    public static final String EN_CHOOSE_SPINNER1_TEXT = "Choose stitch";
    public static final String AR_CHOOSE_SPINNER1_TEXT = "اختار الغرزة المناسبة";
    public static final String EN_CHOOSE_SPINNER2_TEXT = "Choose raws number";
    public static final String AR_CHOOSE_SPINNER2_TEXT = "اختار عدد الصفوف";
    public static final String EN_CHOOSE_SPINNER3_TEXT = "Choose columns number";
    public static final String AR_CHOOSE_SPINNER3_TEXT = "اختار عدد الأعمدة";
}
