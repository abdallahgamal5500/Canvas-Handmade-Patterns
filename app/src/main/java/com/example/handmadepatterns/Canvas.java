package com.example.handmadepatterns;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Canvas extends View {

    private Rect rect1,rect2;
    private Paint paint1,paint2;

    public Canvas(Context context) {
        super(context);
        init(null);
    }

    public Canvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Canvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        rect1 = new Rect();
        rect2 = new Rect();
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(Color.GREEN);

        paint2.setColor(Color.YELLOW);
    }

    public void swapColor() {
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(paint1.getColor()==Color.GREEN ? Color.BLUE : Color.GREEN);
        postInvalidate();
    }

    @Override
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        rect1.left = 100;
        rect1.top = 100;
        rect1.right = rect1.left+100;
        rect1.bottom = rect1.top+100;
        canvas.drawRect(rect1, paint1);

         //border
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(10);
        paint1.setColor(Color.BLACK);
        canvas.drawRect(rect1, paint1);

        rect2.left = 200;
        rect2.top = 200;
        rect2.right = rect2.left+100;
        rect2.bottom = rect2.top+100;
        canvas.drawRect(rect2,paint2);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX()>=100 && event.getX()<=200 && event.getY()>=100 && event.getY()<=200) {
                    paint1.setStyle(Paint.Style.FILL);
                    paint1.setColor(Color.BLUE);
                    postInvalidate();
                }
        }

        return value;
    }
}
