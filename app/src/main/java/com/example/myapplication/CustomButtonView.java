package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class CustomButtonView extends Button {
    public CustomButtonView(Context context) {
        super(context);
        init();
    }

    public CustomButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            setBackgroundColor(Color.BLACK);
        }
        return false;  // Allow the event to be propagated to the parent
    }
}
