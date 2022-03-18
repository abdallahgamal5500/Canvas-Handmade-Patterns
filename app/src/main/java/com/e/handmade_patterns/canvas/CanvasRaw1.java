package com.e.handmade_patterns.canvas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.e.handmade_patterns.helper.Constants;

import java.util.ArrayList;

public class CanvasRaw1 extends View {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Paint> paintArrayList;
    private ArrayList<Rect> rectArrayList;
    private ArrayList<Integer> colorArrayList;
    private ArrayList<Integer> leftArrayList;
    private ArrayList<Integer> rightArrayList;
    private ArrayList<Integer> topArrayList;
    private ArrayList<Integer> bottomArrayList;
    private boolean painting = false;
    private int index;

    public CanvasRaw1(Context context) {
        super(context);
        init(null);
    }

    public CanvasRaw1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CanvasRaw1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        paintArrayList = new ArrayList<>();
        rectArrayList = new ArrayList<>();
        colorArrayList = new ArrayList<>();
        leftArrayList = new ArrayList<>();
        rightArrayList = new ArrayList<>();
        topArrayList = new ArrayList<>();
        bottomArrayList = new ArrayList<>();

        preferences = Constants.RAW1_CONTEXT.getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        for (int i=0;i<(int) ((Constants.RAW1_COLUMNS_COUNT_CURRENT/2.0) * Constants.RAW1_RAWS_COUNT_CURRENT);i++) {
            paintArrayList.add(new Paint(Paint.ANTI_ALIAS_FLAG));
            rectArrayList.add(new Rect());
            colorArrayList.add(preferences.getInt(Constants.RAW1_COLOR_DB+i, Color.WHITE));
        }
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        int hcounter1 = Constants.RAW1_ITEM_HEIGHT_SIZE, vcounter = 0, rawsCounter = 1, columnsCounter = Constants.RAW1_COLUMNS_COUNT_CURRENT/2,temp=0;

        leftArrayList.clear();
        rightArrayList.clear();
        topArrayList.clear();
        bottomArrayList.clear();


        if (Constants.RAW1_ITEM_HEIGHT_SIZE<Constants.RAW1_ITEM_WIDTH_SIZE) {
            temp = Constants.RAW1_ITEM_HEIGHT_SIZE;
            Constants.RAW1_ITEM_HEIGHT_SIZE = Constants.RAW1_ITEM_WIDTH_SIZE;
            Constants.RAW1_ITEM_WIDTH_SIZE = temp;
            hcounter1 = Constants.RAW1_ITEM_HEIGHT_SIZE;
        }

        if (painting) {
            colorArrayList.remove(index);
            colorArrayList.add(index,Constants.CURRENT_COLOR);
            editor.putInt(Constants.RAW1_COLOR_DB+index, Constants.CURRENT_COLOR);
            editor.commit();
        }

        for (int i=0;i<(int) ((Constants.RAW1_COLUMNS_COUNT_CURRENT/2.0) * Constants.RAW1_RAWS_COUNT_CURRENT);i++) {

            rectArrayList.get(i).left = hcounter1;
            rectArrayList.get(i).top = vcounter;
            rectArrayList.get(i).right = rectArrayList.get(i).left + Constants.RAW1_ITEM_WIDTH_SIZE;
            rectArrayList.get(i).bottom = rectArrayList.get(i).top + Constants.RAW1_ITEM_HEIGHT_SIZE;

            leftArrayList.add(rectArrayList.get(i).left);
            rightArrayList.add(rectArrayList.get(i).right);
            topArrayList.add(rectArrayList.get(i).top);
            bottomArrayList.add(rectArrayList.get(i).bottom);

            paintArrayList.get(i).setStyle(Paint.Style.FILL);
            paintArrayList.get(i).setColor(colorArrayList.get(i));
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));
            paintArrayList.get(i).setStyle(Paint.Style.STROKE);
            paintArrayList.get(i).setStrokeWidth(Constants.STROKE_SIZE);
            paintArrayList.get(i).setColor(Constants.BLUE_COLOR);
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));

            hcounter1 = rectArrayList.get(i).right+Constants.RAW1_ITEM_HEIGHT_SIZE;
            if (rawsCounter%2!=0 && (i+1) == columnsCounter) {
                // U are in an odd raw
                rawsCounter++;
                columnsCounter = (Constants.RAW1_COLUMNS_COUNT_CURRENT%2 == 0) ? columnsCounter+Constants.RAW1_COLUMNS_COUNT_CURRENT/2 : columnsCounter+Constants.RAW1_COLUMNS_COUNT_CURRENT/2+1;
                vcounter += Constants.RAW1_ITEM_HEIGHT_SIZE;
                hcounter1 = 0;
                // swapping
                temp = Constants.RAW1_ITEM_WIDTH_SIZE;
                Constants.RAW1_ITEM_WIDTH_SIZE = Constants.RAW1_ITEM_HEIGHT_SIZE;
                Constants.RAW1_ITEM_HEIGHT_SIZE = temp;
            } else if (rawsCounter%2==0 && (i+1) == columnsCounter) {
                // u are in an even raw and
                rawsCounter++;
                columnsCounter += Constants.RAW1_COLUMNS_COUNT_CURRENT/2;
                vcounter += Constants.RAW1_ITEM_HEIGHT_SIZE;
                hcounter1 = Constants.RAW1_ITEM_WIDTH_SIZE;
                temp = Constants.RAW1_ITEM_WIDTH_SIZE;
                Constants.RAW1_ITEM_WIDTH_SIZE = Constants.RAW1_ITEM_HEIGHT_SIZE;
                Constants.RAW1_ITEM_HEIGHT_SIZE = temp;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < leftArrayList.size(); i++) {
                    if (event.getY() <= bottomArrayList.get(i) && event.getY() >= topArrayList.get(i) && event.getX() >= leftArrayList.get(i) && event.getX() <= rightArrayList.get(i)) {
                        index = i;
                        painting = true;
                        postInvalidate();
                        break;
                    }
                }
                break;
        }
        return true;
    }

    public void zoomIn() {
        Constants.RAW1_ITEM_HEIGHT_SIZE += Constants.RAW1_ZOOMING_RATIO_HEIGHT;
        Constants.RAW1_ITEM_WIDTH_SIZE += Constants.RAW1_ZOOMING_RATIO_WIDTH;
        postInvalidate();
    }

    public void zoomOut() {
        if (Constants.RAW1_ZOOMING_RATIO_WIDTH<Constants.RAW1_ITEM_WIDTH_SIZE && Constants.RAW1_ZOOMING_RATIO_HEIGHT<Constants.RAW1_ITEM_HEIGHT_SIZE) {
            Constants.RAW1_ITEM_HEIGHT_SIZE -= Constants.RAW1_ZOOMING_RATIO_HEIGHT;
            Constants.RAW1_ITEM_WIDTH_SIZE -= Constants.RAW1_ZOOMING_RATIO_WIDTH;
            postInvalidate();
        }
    }
}