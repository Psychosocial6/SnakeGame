package com.example.snakegame;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PartSnake {
    private Bitmap bitmap;
    private int x, y;
    private Rect rectBody, rectBottom, rectTop, rectRight, rectLeft;

    public PartSnake(Bitmap bitmap, int x, int y) {
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

    public Rect getRectBody() {
        return new Rect(this.x, this.y, this.x + GameView.fieldSize, this.y + GameView.fieldSize);
    }

    public void setRectBody(Rect rectBody) {
        this.rectBody = rectBody;
    }

    public Rect getRectBottom() {
        return new Rect(this.x, this.y + GameView.fieldSize, this.x + GameView.fieldSize, this.y + GameView.fieldSize + 10 * ScreenSize.SCREEN_HEIGHT / 1920);
    }

    public void setRectBottom(Rect rectBottom) {
        this.rectBottom = rectBottom;
    }

    public Rect getRectTop() {
        return new Rect(this.x, this.y - 10 * ScreenSize.SCREEN_HEIGHT/1920, this.x + GameView.fieldSize, this.y);
    }

    public void setRectTop(Rect rectTop) {
        this.rectTop = rectTop;
    }

    public Rect getRectRight() {
        return new Rect(this.x + GameView.fieldSize, this.y, this.x + GameView.fieldSize + 10 * ScreenSize.SCREEN_WIDTH / 1080, this.y + GameView.fieldSize);
    }

    public void setRectRight(Rect rectRight) {
        this.rectRight = rectRight;
    }

    public Rect getRectLeft() {
        return new Rect(this.x - 10 * ScreenSize.SCREEN_WIDTH / 1080, this.y, this.x, this.y + GameView.fieldSize);
    }

    public void setRectLeft(Rect rectLeft) {
        this.rectLeft = rectLeft;
    }
}
