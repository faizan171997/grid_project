package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DrawingView extends View {
    private static final int NUM_COLUMNS = 32;
    private static final int NUM_ROWS = 32;

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
        // Determine the smaller dimension
        int minDimension = Math.min(w, h);

        // Set both the cellWidth and cellHeight to the smaller dimension divided by the number of cells
        cellWidth = cellHeight = minDimension / (float) NUM_COLUMNS;

        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                cells[i][j] = new Rect(
                        (int) (i * cellWidth),
                        (int) (j * cellHeight),
                        (int) ((i + 1) * cellWidth),
                        (int) ((j + 1) * cellHeight)
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
            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);

            // Add bounds checking
            if (column >= NUM_COLUMNS || row >= NUM_ROWS) {
                return true;
            }

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

    public String getGridData() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                data.append(cellChecked[i][j] ? 1 : 0);
                if (j < NUM_ROWS - 1) {
                    data.append(",");
                }
            }
            if (i < NUM_COLUMNS - 1) {
                data.append(";");
            }
        }
        return data.toString();
    }

    public void sendGridDataToNodeMcu(final String dataToSend) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.0.27:3001/post");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    String payload = "payload=" + dataToSend;
                    os.write(payload.getBytes());
                    os.flush();
                    os.close();
                    int responseCode = conn.getResponseCode();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
