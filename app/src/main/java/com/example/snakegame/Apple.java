package com.example.snakegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Apple {
    private Bitmap bitmap;
    private int x, y;
    private Rect rect;

    public Apple(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rect getRect() {
        return new Rect(this.x, this.y, this.x + GameView.fieldSize, this.y + GameView.fieldSize);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
    // отрисовка яблока
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }
    // изменение координат
    public void reset(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }
}
