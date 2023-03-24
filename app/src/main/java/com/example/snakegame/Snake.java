package com.example.snakegame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Snake {
    private boolean moveLeft, moveRight, moveUp, moveDown;
    private int x, y, length;
    private Bitmap bitmap, bitmapHeadUp, bitmapHeadDown, bitmapHeadLeft, bitmapHeadRight, bitmapBodyVertical, bitmapBodyHorizontal, bitmapBodyTopRight, bitmapBodyTopLeft, bitmapBodyBottomRight, bitmapBodyBottomLeft, bitmapTailUp, bitmapTailDown, bitmapTailRight, bitmapTailLeft;
    private ArrayList<PartSnake> arrayListPartSnake = new ArrayList<>();
    private int velocity;

    public Snake(Bitmap bitmap, int x, int y, int length, int velocity) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.length = length;
        this.velocity = velocity;
        bitmapBodyBottomLeft = Bitmap.createBitmap(bitmap, 0, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapBodyBottomRight = Bitmap.createBitmap(bitmap, GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapBodyHorizontal = Bitmap.createBitmap(bitmap, 2 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapBodyTopLeft = Bitmap.createBitmap(bitmap, 3 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapBodyTopRight = Bitmap.createBitmap(bitmap, 4 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapBodyVertical = Bitmap.createBitmap(bitmap, 5 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapHeadDown = Bitmap.createBitmap(bitmap, 6 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapHeadLeft = Bitmap.createBitmap(bitmap, 7 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapHeadRight = Bitmap.createBitmap(bitmap, 8 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapHeadUp = Bitmap.createBitmap(bitmap, 9 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapTailUp = Bitmap.createBitmap(bitmap, 10 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapTailRight = Bitmap.createBitmap(bitmap, 11 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapTailLeft = Bitmap.createBitmap(bitmap, 12 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        bitmapTailDown = Bitmap.createBitmap(bitmap, 13 * GameView.fieldSize, 0, GameView.fieldSize, GameView.fieldSize);
        arrayListPartSnake.add(new PartSnake(bitmapHeadRight, x, y));
        for (int i = 1; i < length - 1; i++) {
            arrayListPartSnake.add(new PartSnake(bitmapBodyHorizontal, arrayListPartSnake.get(i - 1).getX() - GameView.fieldSize, y));
        }
        arrayListPartSnake.add(new PartSnake(bitmapTailRight, arrayListPartSnake.get(length - 2).getX() - GameView.fieldSize, y));
        setMoveRight(true);
    }

    // обновление положения
    public void renew() {
        for (int i = length - 1; i > 0; i--) {
            arrayListPartSnake.get(i).setX(arrayListPartSnake.get(i - 1).getX());
            arrayListPartSnake.get(i).setY(arrayListPartSnake.get(i - 1).getY());
        }
        if (moveRight == true) {
            arrayListPartSnake.get(0).setX(arrayListPartSnake.get(0).getX() + GameView.fieldSize);
            arrayListPartSnake.get(0).setBitmap(bitmapHeadRight);
        }
        else if (moveLeft == true) {
            arrayListPartSnake.get(0).setX(arrayListPartSnake.get(0).getX() - GameView.fieldSize);
            arrayListPartSnake.get(0).setBitmap(bitmapHeadLeft);
        }
        else if (moveUp == true) {
            arrayListPartSnake.get(0).setY(arrayListPartSnake.get(0).getY() - GameView.fieldSize);
            arrayListPartSnake.get(0).setBitmap(bitmapHeadUp);
        }
        else if (moveDown == true) {
            arrayListPartSnake.get(0).setY(arrayListPartSnake.get(0).getY() + GameView.fieldSize);
            arrayListPartSnake.get(0).setBitmap(bitmapHeadDown);
        }
        for (int i = 1; i < length - 1; i++) {
            if (arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyBottomLeft);
            }
            else if (arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyBottomRight);
            }
            else if (arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyTopLeft);
            }
            else if (arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyTopRight);
            }
            else if (arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectTop().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectBottom().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyVertical);
            }
            else if (arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i + 1).getRectBody()) && arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i - 1).getRectBody()) || arrayListPartSnake.get(i).getRectLeft().intersect(arrayListPartSnake.get(i - 1).getRectBody()) && arrayListPartSnake.get(i).getRectRight().intersect(arrayListPartSnake.get(i + 1).getRectBody())) {
                arrayListPartSnake.get(i).setBitmap(bitmapBodyHorizontal);
            }
        }
        if (arrayListPartSnake.get(length - 1).getRectRight().intersect(arrayListPartSnake.get(length - 2).getRectBody())) {
            arrayListPartSnake.get(length - 1).setBitmap(bitmapTailRight);
        }
        else if (arrayListPartSnake.get(length - 1).getRectLeft().intersect(arrayListPartSnake.get(length - 2).getRectBody())) {
            arrayListPartSnake.get(length - 1).setBitmap(bitmapTailLeft);
        }
        else if (arrayListPartSnake.get(length - 1).getRectTop().intersect(arrayListPartSnake.get(length - 2).getRectBody())) {
            arrayListPartSnake.get(length - 1).setBitmap(bitmapTailUp);
        }
        else if (arrayListPartSnake.get(length - 1).getRectBottom().intersect(arrayListPartSnake.get(length - 2).getRectBody())) {
            arrayListPartSnake.get(length - 1).setBitmap(bitmapTailDown);
        }
    }

    // отрисовка
    public void draw(Canvas canvas) {
        for (int i = 0; i < length; i++) {
            canvas.drawBitmap(arrayListPartSnake.get(i).getBitmap(), arrayListPartSnake.get(i).getX(), arrayListPartSnake.get(i).getY(), null);
        }
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmapHeadUp() {
        return bitmapHeadUp;
    }

    public void setBitmapHeadUp(Bitmap bitmapHeadUp) {
        this.bitmapHeadUp = bitmapHeadUp;
    }

    public Bitmap getBitmapHeadDown() {
        return bitmapHeadDown;
    }

    public void setBitmapHeadDown(Bitmap bitmapHeadDown) {
        this.bitmapHeadDown = bitmapHeadDown;
    }

    public Bitmap getBitmapHeadLeft() {
        return bitmapHeadLeft;
    }

    public void setBitmapHeadLeft(Bitmap bitmapHeadLeft) {
        this.bitmapHeadLeft = bitmapHeadLeft;
    }

    public Bitmap getBitmapHeadRight() {
        return bitmapHeadRight;
    }

    public void setBitmapHeadRight(Bitmap bitmapHeadRight) {
        this.bitmapHeadRight = bitmapHeadRight;
    }

    public Bitmap getBitmapBodyVertical() {
        return bitmapBodyVertical;
    }

    public void setBitmapBodyVertical(Bitmap bitmapBodyVertical) {
        this.bitmapBodyVertical = bitmapBodyVertical;
    }

    public Bitmap getBitmapBodyHorizontal() {
        return bitmapBodyHorizontal;
    }

    public void setBitmapBodyHorizontal(Bitmap bitmapBodyHorizontal) {
        this.bitmapBodyHorizontal = bitmapBodyHorizontal;
    }

    public Bitmap getBitmapBodyTopRight() {
        return bitmapBodyTopRight;
    }

    public void setBitmapBodyTopRight(Bitmap bitmapBodyTopRight) {
        this.bitmapBodyTopRight = bitmapBodyTopRight;
    }

    public Bitmap getBitmapBodyTopLeft() {
        return bitmapBodyTopLeft;
    }

    public void setBitmapBodyTopLeft(Bitmap bitmapBodyTopLeft) {
        this.bitmapBodyTopLeft = bitmapBodyTopLeft;
    }

    public Bitmap getBitmapBodyBottomRight() {
        return bitmapBodyBottomRight;
    }

    public void setBitmapBodyBottomRight(Bitmap bitmapBodyBottomRight) {
        this.bitmapBodyBottomRight = bitmapBodyBottomRight;
    }

    public Bitmap getBitmapBodyBottomLeft() {
        return bitmapBodyBottomLeft;
    }

    public void setBitmapBodyBottomLeft(Bitmap bitmapBodyBottomLeft) {
        this.bitmapBodyBottomLeft = bitmapBodyBottomLeft;
    }

    public Bitmap getBitmapTailUp() {
        return bitmapTailUp;
    }

    public void setBitmapTailUp(Bitmap bitmapTailUp) {
        this.bitmapTailUp = bitmapTailUp;
    }

    public Bitmap getBitmapTailDown() {
        return bitmapTailDown;
    }

    public void setBitmapTailDown(Bitmap bitmapTailDown) {
        this.bitmapTailDown = bitmapTailDown;
    }

    public Bitmap getBitmapTailRight() {
        return bitmapTailRight;
    }

    public void setBitmapTailRight(Bitmap bitmapTailRight) {
        this.bitmapTailRight = bitmapTailRight;
    }

    public Bitmap getBitmapTailLeft() {
        return bitmapTailLeft;
    }

    public void setBitmapTailLeft(Bitmap bitmapTailLeft) {
        this.bitmapTailLeft = bitmapTailLeft;
    }

    public ArrayList<PartSnake> getArrayListPartSnake() {
        return arrayListPartSnake;
    }

    public void setArrayListPartSnake(ArrayList<PartSnake> arrayListPartSnake) {
        this.arrayListPartSnake = arrayListPartSnake;
    }

    public void setVelocity(int velocity) { this.velocity = velocity; }

    public int getVelocity() { return velocity; }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        stop();
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        stop();
        this.moveRight = moveRight;
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        stop();
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        stop();
        this.moveDown = moveDown;
    }
    public void stop() {
        this.moveDown = false;
        this.moveUp = false;
        this.moveLeft = false;
        this.moveRight = false;
    }

    // увеличение длины
    public void addPart() {
        PartSnake part = this.arrayListPartSnake.get(length - 1);
        this.length += 1;
        if (part.getBitmap() == bitmapTailRight) {
            this.arrayListPartSnake.add(new PartSnake(bitmapTailRight, part.getX() - GameView.fieldSize, part.getY()));
        }
        else if (part.getBitmap() == bitmapTailLeft) {
            this.arrayListPartSnake.add(new PartSnake(bitmapTailLeft, part.getX() + GameView.fieldSize, part.getY()));
        }
        else if (part.getBitmap() == bitmapTailUp) {
            this.arrayListPartSnake.add(new PartSnake(bitmapTailUp, part.getX(), part.getY() + GameView.fieldSize));
        }
        else if (part.getBitmap() == bitmapTailDown) {
            this.arrayListPartSnake.add(new PartSnake(bitmapTailRight, part.getX(), part.getY() - GameView.fieldSize));
        }
    }
}
