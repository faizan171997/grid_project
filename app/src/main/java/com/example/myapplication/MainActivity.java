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

public class MainActivity extends AppCompatActivity {
    private GridLayout grid;
    private Button[][] buttons = new Button[5][14];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grid = findViewById(R.id.grid);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 14; j++) {
                buttons[i][j] = new Button(this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.columnSpec = GridLayout.spec(i);
                params.rowSpec = GridLayout.spec(j);
                params.setGravity(Gravity.FILL);
                buttons[i][j].setLayoutParams(params);
                buttons[i][j].setBackgroundColor(Color.WHITE);
                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onButtonClicked(finalI, finalJ);
                    }
                });
                grid.addView(buttons[i][j]);
            }
        }
    }

    private void onButtonClicked(int i, int j) {
        Button button = buttons[i][j];
        ColorDrawable buttonColor = (ColorDrawable) button.getBackground();

        if (buttonColor.getColor() == Color.WHITE) {
            button.setBackgroundColor(Color.BLACK);
        } else {
            button.setBackgroundColor(Color.WHITE);
        }
    }
}




