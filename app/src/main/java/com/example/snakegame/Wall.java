package com.example.snakegame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Wall {
    private Bitmap bitmap;
    private int x, y, height, width;
    private Rect rect;

    public Wall(Bitmap bitmap, int x, int y, int height, int width) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Rect getRect() {
        return new Rect(this.x, this.y, this.x + GameView.fieldSize, this.y + GameView.fieldSize);
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }
}

