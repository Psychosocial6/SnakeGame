package com.example.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class SecondGameView extends View {
    private Bitmap bitmapDarkGrass, bitmapLightGrass, bitmapSnake, bitmapApple, bitmapWall;
    public static int fieldSize = 75 * ScreenSize.SCREEN_WIDTH / 1080;
    private ArrayList<Grass> arrayGrass = new ArrayList<>();
    private ArrayList<Wall>  arrayWall = new ArrayList<>();
    private Snake snake;
    private int height = 21, width = 12;
    private boolean move = false;
    private float mX, mY;
    private android.os.Handler handler;
    private Runnable runnable;
    private Apple apple;
    private static boolean isPlaying = false;
    public static int score = 0, bestScore = 0;
    public MyDB database;

    private SecondMode activity;

    public SecondGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activity = (SecondMode) context;
        database = MainActivity.database;
        bitmapDarkGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_dark);
        bitmapDarkGrass = Bitmap.createScaledBitmap(bitmapDarkGrass, fieldSize, fieldSize, true);
        bitmapLightGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_light);
        bitmapLightGrass = Bitmap.createScaledBitmap(bitmapLightGrass, fieldSize, fieldSize, true);
        bitmapSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_sprites);
        bitmapSnake = Bitmap.createScaledBitmap(bitmapSnake, 14 * fieldSize, fieldSize, true);
        bitmapApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bitmapApple = Bitmap.createScaledBitmap(bitmapApple, fieldSize, fieldSize, true);
        bitmapWall = BitmapFactory.decodeResource(this.getResources(), R.drawable.wall);
        bitmapWall = Bitmap.createScaledBitmap(bitmapWall, fieldSize, fieldSize, true);
        bestScore = database.select(2).points;
        // 1-е создание игры
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((i + j) % 2 == 0) {
                    arrayGrass.add(new Grass(bitmapDarkGrass, j * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, i * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
                }
                else {
                    arrayGrass.add(new Grass(bitmapLightGrass, j * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, i * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
                }
            }
        }
        // добавление стенок
        arrayGrass.remove(27 - 1);
        arrayGrass.remove(34 - 2);
        arrayGrass.remove(35 - 3);
        arrayGrass.remove(36 - 4);
        arrayGrass.remove(37 - 5);
        arrayGrass.remove(38 - 6);
        arrayGrass.remove(39 - 7);
        arrayGrass.remove(46 - 8);
        arrayGrass.remove(47 - 9);
        arrayGrass.remove(48 - 10);
        arrayGrass.remove(66 - 11);
        arrayGrass.remove(94 - 12);
        arrayGrass.remove(106 - 13);
        arrayGrass.remove(114 - 14);
        arrayGrass.remove(118 - 15);
        arrayGrass.remove(125 - 16);
        arrayGrass.remove(126 - 17);
        arrayGrass.remove(127 - 18);
        arrayGrass.remove(130 - 19);
        arrayGrass.remove(138 - 20);
        arrayGrass.remove(166 - 21);
        arrayGrass.remove(167 - 22);
        arrayGrass.remove(168 - 23);
        arrayGrass.remove(171 - 24);
        arrayGrass.remove(172 - 25);
        arrayGrass.remove(173 - 26);
        arrayGrass.remove(178 - 27);
        arrayGrass.remove(185 - 28);
        arrayGrass.remove(190 - 29);
        arrayGrass.remove(197 - 30);
        arrayGrass.remove(217 - 31);
        arrayGrass.remove(218 - 32);

        arrayWall.add(new Wall(bitmapWall, 2 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 2 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 2 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 10 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 2 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 11 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 2 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 2 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 10 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 11 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 3 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 5 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 5 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 7 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 8 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 5 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 9 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 9 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 4 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 10 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 5 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 10 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 6 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 10 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 10 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 5 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 11 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 13 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 10 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 13 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 11 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 13 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 2 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 14 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 3 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 14 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 4 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 14 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 14 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 4 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 15 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 9 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 15 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, 4 * fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 16 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 18 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));
        arrayWall.add(new Wall(bitmapWall, fieldSize + ScreenSize.SCREEN_WIDTH / 2 - (width / 2) * fieldSize, 18 * fieldSize + 100 * ScreenSize.SCREEN_HEIGHT / 1920, fieldSize, fieldSize));

        snake = new Snake(bitmapSnake, arrayGrass.get(130).getX(), arrayGrass.get(130).getY(), 4, 140);
        int [] list = appleGeneration();
        apple = new Apple(bitmapApple, arrayGrass.get(list[0]).getX(), arrayGrass.get(list[1]).getY());
        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    // действия при касании
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                if (move == false) {
                    mX = event.getX();
                    mY = event.getY();
                    move = true;
                }
                else {
                    if (mX - event.getX() > 125 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveRight()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveLeft(true);
                        isPlaying = true;
                    }
                    else if (event.getX() - mX > 125 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveLeft()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveRight(true);
                        isPlaying = true;
                    }
                    else if (mY - event.getY() > 125 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveDown()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveUp(true);
                        isPlaying = true;
                    }
                    else if (event.getY() - mY > 125 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveUp()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveDown(true);
                        isPlaying = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mX = 0;
                mY = 0;
                move = false;
                break;
        }
        return true;
    }

    // отрисовка
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(0xFF008EFF);
        for (int i = 0; i < arrayGrass.size(); i++) {
            canvas.drawBitmap(arrayGrass.get(i).getBitmap(), arrayGrass.get(i).getX(), arrayGrass.get(i).getY(), null);
        }
        for (int i = 0; i < arrayWall.size(); i++) {
            canvas.drawBitmap(arrayWall.get(i).getBitmap(), arrayWall.get(i).getX(), arrayWall.get(i).getY(), null);
        }
        if (isPlaying == true) {
            snake.renew();
            if (snake.getArrayListPartSnake().get(0).getX() < this.arrayGrass.get(0).getX() || snake.getArrayListPartSnake().get(0).getY() < this.arrayGrass.get(0).getY() || snake.getArrayListPartSnake().get(0).getY() + fieldSize > this.arrayGrass.get(this.arrayGrass.size()-1).getY() + fieldSize || snake.getArrayListPartSnake().get(0).getX() + fieldSize > this.arrayGrass.get(this.arrayGrass.size()-1).getX() + fieldSize) {
                gameOver();
            }
            for (int i = 1; i < snake.getArrayListPartSnake().size(); i++){
                if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(snake.getArrayListPartSnake().get(i).getRectBody())){
                    gameOver();
                }
            }
            for (int i = 0; i < arrayWall.size(); i++) {
                if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(arrayWall.get(i).getRect())) {
                    gameOver();
                }
            }
        }
        snake.draw(canvas);
        apple.draw(canvas);
        if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(apple.getRect())) {
            int [] list = appleGeneration();
            apple.reset(arrayGrass.get(list[0]).getX(), arrayGrass.get(list[1]).getY());
            snake.addPart();
            score += 1;
            SecondMode.score.setText(String.valueOf(score));
            if (score > bestScore) {
                bestScore = score;
                SecondMode.bestScore.setText(String.valueOf(bestScore));
            }
            else {
                SecondMode.bestScore.setText(String.valueOf(bestScore));
            }
        }
        handler.postDelayed(runnable, snake.getVelocity());
    }

    // создание яблока
    public int[] appleGeneration() {
        int [] xy = new int [2];
        Random random = new Random();
        int index = random.nextInt(arrayGrass.size() - 1);
        int x = arrayGrass.get(index).getX();
        int y = arrayGrass.get(index).getY();
        boolean check = true;
        Rect rect = new Rect(x, y, x + fieldSize, y + fieldSize);
        while (check) {
            check = false;
            for (int i = 0; i < snake.getArrayListPartSnake().size(); i++) {
                if (snake.getArrayListPartSnake().get(i).getRectBody().intersect(rect)) {
                    check = true;
                    index = random.nextInt(arrayGrass.size() - 1);
                    x = arrayGrass.get(index).getX();
                    y = arrayGrass.get(index).getY();
                    rect = new Rect(x, y, x + fieldSize, y + fieldSize);
                }
            }
        }
        xy[0] = index; // переделать реализацию!!!!!!!!!!!!!!!!!!!!!!!! вместо [] 1 число
        xy[1] = index;
        return xy;
    }

    // проигрыш
    private void gameOver() {
        isPlaying = false;
        activity.gameOver(score);
        if (bestScore > database.select(2).points) {
            ScoreForDB scoreForDB = new ScoreForDB(2, score);
            database.update(scoreForDB);
        }
    }

    // перезапуск
    public void reset() {
        snake = new Snake(bitmapSnake, arrayGrass.get(130).getX(), arrayGrass.get(130).getY(), 4, 140);
        int [] list = appleGeneration();
        apple = new Apple(bitmapApple, arrayGrass.get(list[0]).getX(), arrayGrass.get(list[1]).getY());
        score = 0;
        bestScore = database.select(2).points;
        SecondMode.score.setText(String.valueOf(score));
        SecondMode.bestScore.setText(String.valueOf(bestScore));
    }
}
