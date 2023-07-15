package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;


import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the DrawingView
        drawingView = new DrawingView(this);

        // Create the clear button
        clearButton = new Button(this);
        clearButton.setText("Clear");

        // Set an OnClickListener on the button to clear the grid when it's clicked
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.clearGrid();
            }
        });

        // Create a vertical LinearLayout and add the clearButton and drawingView to it
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(clearButton);
        layout.addView(drawingView);

        // Set the LinearLayout as the content view of the activity
        setContentView(layout);
    }
}













