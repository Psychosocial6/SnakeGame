package com.example.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class TimeGameView extends View {
    private Bitmap bitmapDarkGrass, bitmapLightGrass, bitmapSnake, bitmapApple;
    public static int fieldSize = 75 * ScreenSize.SCREEN_WIDTH / 1080;
    private ArrayList<Grass> arrayGrass = new ArrayList<>();
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
    private Time activity;
    public int minutes, seconds;
    private int isWorking = 0;

    public TimeGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activity = (Time) context;
        database = MainActivity.database;
        bitmapDarkGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_dark);
        bitmapDarkGrass = Bitmap.createScaledBitmap(bitmapDarkGrass, fieldSize, fieldSize, true);
        bitmapLightGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_light);
        bitmapLightGrass = Bitmap.createScaledBitmap(bitmapLightGrass, fieldSize, fieldSize, true);
        bitmapSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_sprites);
        bitmapSnake = Bitmap.createScaledBitmap(bitmapSnake, 14 * fieldSize, fieldSize, true);
        bitmapApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bitmapApple = Bitmap.createScaledBitmap(bitmapApple, fieldSize, fieldSize, true);
        bestScore = database.select(4).points;
        Time.minutesC = 2;
        Time.secondsC = 0;
        isWorking = 0;
        // 1-е создание игрового поля
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
        snake = new Snake(bitmapSnake, arrayGrass.get(126).getX(), arrayGrass.get(126).getY(), 4, 140);
        apple = new Apple(bitmapApple, arrayGrass.get(appleGeneration()[0]).getX(), arrayGrass.get(appleGeneration()[1]).getY());
        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    // действия при касании экрана
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
        if (isWorking == 0) {
            isWorking = 1;
            Time.timer.start();
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
        if (isPlaying == true) {
            snake.renew();
            if (snake.getArrayListPartSnake().get(0).getX() < this.arrayGrass.get(0).getX() || snake.getArrayListPartSnake().get(0).getY() < this.arrayGrass.get(0).getY() || snake.getArrayListPartSnake().get(0).getY() + fieldSize > this.arrayGrass.get(this.arrayGrass.size()-1).getY() + fieldSize || snake.getArrayListPartSnake().get(0).getX() + fieldSize > this.arrayGrass.get(this.arrayGrass.size()-1).getX() + fieldSize) {
                gameOver();
            }
            for (int j = 1; j < snake.getArrayListPartSnake().size(); j++){
                if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(snake.getArrayListPartSnake().get(j).getRectBody())){
                    gameOver();
                }
            }
        }
        snake.draw(canvas);
        apple.draw(canvas);
        if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(apple.getRect())) {
            appleGeneration();
            apple.reset(arrayGrass.get(appleGeneration()[0]).getX(), arrayGrass.get(appleGeneration()[1]).getY());
            snake.addPart();
            score += 1;
            Time.score.setText(String.valueOf(score));
            if (score > bestScore) {
                bestScore = score;
                Time.bestScore.setText(String.valueOf(bestScore));
            }
            else {
                Time.bestScore.setText(String.valueOf(bestScore));
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

    // завершение игры
    public void gameOver(){
        isPlaying = false;
        activity.gameOver(score);
        if (bestScore > database.select(4).points) {
            ScoreForDB scoreForDB = new ScoreForDB(4, score);
            database.update(scoreForDB);
        }
    }

    // обновление игрового поля
    public void reset(){
        snake = new Snake(bitmapSnake, arrayGrass.get(126).getX(), arrayGrass.get(126).getY(), 4, 140);
        apple = new Apple(bitmapApple, arrayGrass.get(appleGeneration()[0]).getX(), arrayGrass.get(appleGeneration()[1]).getY());
        score = 0;
        bestScore = database.select(4).points;
        Time.score.setText(String.valueOf(score));
        Time.bestScore.setText(String.valueOf(bestScore));
        Time.minutesC = 2;
        Time.secondsC = 0;
        Time.minutes.setText(String.valueOf(minutes));
        Time.seconds.setText(String.valueOf(seconds));
    }

}
