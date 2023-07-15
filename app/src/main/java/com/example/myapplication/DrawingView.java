package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {
    private static final int NUM_COLUMNS = 5;
    private static final int NUM_ROWS = 14;

    private Rect[][] cells;
    private boolean[][] cellChecked;
    private float cellWidth, cellHeight;
    private Paint blackPaint;

    public DrawingView(Context context) {
        super(context);
        blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        cells = new Rect[NUM_COLUMNS][NUM_ROWS];
        cellChecked = new boolean[NUM_COLUMNS][NUM_ROWS];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        cellWidth = w / (float)NUM_COLUMNS;
        cellHeight = h / (float)NUM_ROWS;
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                cells[i][j] = new Rect(
                        (int)(i * cellWidth),
                        (int)(j * cellHeight),
                        (int)((i + 1) * cellWidth),
                        (int)((j + 1) * cellHeight)
                );
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                if (cellChecked[i][j]) {
                    canvas.drawRect(cells[i][j], blackPaint);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);
            cellChecked[column][row] = true;
            invalidate();
        }
        return true;
    }

    public void clearGrid() {
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                cellChecked[i][j] = false;
            }
        }
        invalidate();
    }

}

