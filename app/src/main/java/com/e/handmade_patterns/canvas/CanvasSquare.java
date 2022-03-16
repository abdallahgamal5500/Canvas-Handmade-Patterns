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

public class CanvasSquare extends View {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Paint> paintArrayList;
    private ArrayList<Rect> rectArrayList;
    private ArrayList<Integer> colorArrayList;
    private boolean painting = false;
    private int index;

    public CanvasSquare(Context context) {
        super(context);
        init(null);
    }

    public CanvasSquare(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CanvasSquare(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        paintArrayList = new ArrayList<>();
        rectArrayList = new ArrayList<>();
        colorArrayList = new ArrayList<>();

        preferences = Constants.SQUARE_CONTEXT.getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        for (int i=0;i<Constants.SQUARE_COLUMNS_COUNT_CURRENT * Constants.SQUARE_RAWS_COUNT_CURRENT;i++) {
            paintArrayList.add(new Paint(Paint.ANTI_ALIAS_FLAG));
            rectArrayList.add(new Rect());
            colorArrayList.add(preferences.getInt(Constants.SQUARE_COLOR_DB+i, Color.WHITE));
        }
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        int hcounter1 = 0, vcounter = 0;

        if (painting) {
            colorArrayList.remove(index);
            colorArrayList.add(index,Constants.CURRENT_COLOR);
            editor.putInt(Constants.SQUARE_COLOR_DB+index, Constants.CURRENT_COLOR);
            editor.commit();
        }

        for (int i=0;i<Constants.SQUARE_COLUMNS_COUNT_CURRENT * Constants.SQUARE_RAWS_COUNT_CURRENT;i++) {
            rectArrayList.get(i).left = hcounter1;
            rectArrayList.get(i).top = vcounter;
            rectArrayList.get(i).right = rectArrayList.get(i).left + Constants.SQUARE_ITEM_SIZE;
            rectArrayList.get(i).bottom = rectArrayList.get(i).top + Constants.SQUARE_ITEM_SIZE;
            paintArrayList.get(i).setStyle(Paint.Style.FILL);
            paintArrayList.get(i).setColor(colorArrayList.get(i));
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));
            paintArrayList.get(i).setStyle(Paint.Style.STROKE);
            paintArrayList.get(i).setStrokeWidth(Constants.STROKE_SIZE);
            paintArrayList.get(i).setColor(Constants.BLUE_COLOR);
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));
            hcounter1 += Constants.SQUARE_ITEM_SIZE;
            if((i+1) % Constants.SQUARE_COLUMNS_COUNT_CURRENT == 0) {
                vcounter += Constants.SQUARE_ITEM_SIZE;
                hcounter1 = 0;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int raw = (int) (event.getY()/Constants.SQUARE_ITEM_SIZE)+1;
                int column = (int) (event.getX()/Constants.SQUARE_ITEM_SIZE)+1;
                index = (raw-1)*Constants.SQUARE_COLUMNS_COUNT_CURRENT+column-1;
                painting = true;
                postInvalidate();
                break;
        }
        return true;
    }

    public void zoomIn() {
        Constants.SQUARE_ITEM_SIZE  = Constants.SQUARE_ITEM_SIZE+Constants.SQUARE_ZOOMING_RATIO;
        postInvalidate();
    }

    public void zoomOut() {
        if (Constants.SQUARE_ZOOMING_RATIO<Constants.SQUARE_ITEM_SIZE) {
            Constants.SQUARE_ITEM_SIZE  = Constants.SQUARE_ITEM_SIZE-Constants.SQUARE_ZOOMING_RATIO;
            postInvalidate();
        }
    }
}