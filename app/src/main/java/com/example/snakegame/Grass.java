package com.example.snakegame;

import android.graphics.Bitmap;

public class Grass {
    private Bitmap bitmap;
    private int x, y, height, width;

    public Grass(Bitmap bitmap, int x, int y, int height, int width) {
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
}
