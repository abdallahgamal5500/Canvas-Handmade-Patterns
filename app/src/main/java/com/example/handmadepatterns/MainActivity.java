package com.example.handmadepatterns;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Canvas canvas;
    private Button swap_btn1,swap_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swap_btn1 = findViewById(R.id.swap_btn1);
        swap_btn2 = findViewById(R.id.swap_btn2);
        canvas = findViewById(R.id.canvas);

        swap_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.swapColor();
            }
        });

        swap_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvas.swapColor();
            }
        });

    }
}