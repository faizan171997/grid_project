package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DrawingView drawingView;
    private Button clearButton;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create the DrawingView
        drawingView = new DrawingView(this);

        // Create the clear button
        clearButton = new Button(this);
        clearButton.setText("Clear");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.clearGrid();
            }
        });

        // Create the send button
        sendButton = new Button(this);
        sendButton.setText("Send");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gridData = drawingView.getGridData();
                Log.d("DEBUG", gridData); // print out gridData for debugging
                drawingView.sendGridDataToNodeMcu(gridData);
            }
        });

        // Create a vertical LinearLayout and add the clearButton, sendButton and drawingView to it
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(clearButton);
        layout.addView(sendButton);
        layout.addView(drawingView);

        // Set the LinearLayout as the content view of the activity
        setContentView(layout);
    }
}
