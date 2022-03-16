package com.e.handmade_patterns.canvas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.e.handmade_patterns.helper.Constants;

import java.util.ArrayList;

public class CanvasBrick extends View {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private ArrayList<Paint> paintArrayList;
    private ArrayList<Rect> rectArrayList;
    private ArrayList<Integer> colorArrayList;
    private boolean painting = false;
    private int index;


    public CanvasBrick(Context context) {
        super(context);
        init(null);
    }

    public CanvasBrick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CanvasBrick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        paintArrayList = new ArrayList<>();
        rectArrayList = new ArrayList<>();
        colorArrayList = new ArrayList<>();

        preferences = Constants.BRICK_CONTEXT.getSharedPreferences(Constants.DATABASE_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();

        for (int i=0;i<Constants.BRICK_COLUMNS_COUNT_CURRENT * Constants.BRICK_RAWS_COUNT_CURRENT;i++) {
            paintArrayList.add(new Paint(Paint.ANTI_ALIAS_FLAG));
            rectArrayList.add(new Rect());
            colorArrayList.add(preferences.getInt(Constants.BRICK_COLOR_DB+i, Color.WHITE));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int hcounter1 = 0, vcounter = 0, rawsCounter = 1;

        if (painting) {
            colorArrayList.remove(index);
            colorArrayList.add(index,Constants.CURRENT_COLOR);
            editor.putInt(Constants.BRICK_COLOR_DB+index, Constants.CURRENT_COLOR);
            editor.commit();
        }

        for (int i=0;i<Constants.BRICK_COLUMNS_COUNT_CURRENT * Constants.BRICK_RAWS_COUNT_CURRENT;i++) {
            rectArrayList.get(i).top = vcounter;
            rectArrayList.get(i).bottom = rectArrayList.get(i).top + Constants.BRICK_HEIGHT_SIZE;
            rectArrayList.get(i).left = hcounter1;
            rectArrayList.get(i).right = rectArrayList.get(i).left + Constants.BRICK_WIDTH_SIZE;
            paintArrayList.get(i).setStyle(Paint.Style.FILL);
            paintArrayList.get(i).setColor(colorArrayList.get(i));
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));
            paintArrayList.get(i).setStyle(Paint.Style.STROKE);
            paintArrayList.get(i).setStrokeWidth(Constants.STROKE_SIZE);
            paintArrayList.get(i).setColor(Constants.BLUE_COLOR);
            canvas.drawRect(rectArrayList.get(i),paintArrayList.get(i));
            hcounter1 += Constants.BRICK_WIDTH_SIZE;
            if((i+1) % Constants.BRICK_COLUMNS_COUNT_CURRENT == 0) {
                vcounter += Constants.BRICK_HEIGHT_SIZE;
                rawsCounter++;
                hcounter1 = (rawsCounter%2==0) ? Constants.BRICK_WIDTH_SIZE/2 : 0;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.i("test",""+event.getX());
                int column = 0;
                int raw = (int) (event.getY()/Constants.BRICK_HEIGHT_SIZE)+1;
                if (raw %2 ==0) {
                    if (event.getX()>=Constants.BRICK_WIDTH_SIZE/2 && event.getX()<=Constants.BRICK_WIDTH_SIZE*Constants.BRICK_COLUMNS_COUNT_CURRENT+Constants.BRICK_WIDTH_SIZE/2) {
                        column = (int) ((event.getX()-Constants.BRICK_WIDTH_SIZE/2) / Constants.BRICK_WIDTH_SIZE)+1;
                        index = (raw-1)*Constants.BRICK_COLUMNS_COUNT_CURRENT+column-1;
                        painting = true;
                        postInvalidate();
                    }
                }
                else {
                    if (event.getX()<=Constants.BRICK_WIDTH_SIZE*Constants.BRICK_COLUMNS_COUNT_CURRENT) {
                        column = (int) (event.getX() / Constants.BRICK_WIDTH_SIZE) + 1;
                        index = (raw - 1) * Constants.BRICK_COLUMNS_COUNT_CURRENT + column - 1;
                        painting = true;
                        postInvalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void zoomIn() {
        Constants.BRICK_WIDTH_SIZE += Constants.BRICK_ZOOMING_RATIO_WIDTH;
        Constants.BRICK_HEIGHT_SIZE += Constants.BRICK_ZOOMING_RATIO_HEIGHT;
        postInvalidate();
    }

    public void zoomOut() {
        if (Constants.BRICK_ZOOMING_RATIO_WIDTH < Constants.BRICK_WIDTH_SIZE) {
            Constants.BRICK_WIDTH_SIZE -= Constants.BRICK_ZOOMING_RATIO_WIDTH;
            Constants.BRICK_HEIGHT_SIZE -= Constants.BRICK_ZOOMING_RATIO_HEIGHT;
            postInvalidate();
        }
    }
}
