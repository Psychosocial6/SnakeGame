package com.example.snakegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
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

    private OfflineGame activity;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        activity = (OfflineGame) context;
        database = MainActivity.database;
        bitmapDarkGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_dark);
        bitmapDarkGrass = Bitmap.createScaledBitmap(bitmapDarkGrass, fieldSize, fieldSize, true);
        bitmapLightGrass = BitmapFactory.decodeResource(this.getResources(), R.drawable.grass_light);
        bitmapLightGrass = Bitmap.createScaledBitmap(bitmapLightGrass, fieldSize, fieldSize, true);
        bitmapSnake = BitmapFactory.decodeResource(this.getResources(), R.drawable.snake_sprites);
        bitmapSnake = Bitmap.createScaledBitmap(bitmapSnake, 14 * fieldSize, fieldSize, true);
        bitmapApple = BitmapFactory.decodeResource(this.getResources(), R.drawable.apple);
        bitmapApple = Bitmap.createScaledBitmap(bitmapApple, fieldSize, fieldSize, true);
        bestScore = database.select(1).points;
        // 1-?? ???????????????? ???????????????? ????????
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
        snake = new Snake(bitmapSnake, arrayGrass.get(126).getX(), arrayGrass.get(126).getY(), 4);
        apple = new Apple(bitmapApple, arrayGrass.get(appleGeneration()[0]).getX(), arrayGrass.get(appleGeneration()[1]).getY());
        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    // ???????????????? ?????? ?????????????? ????????????
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
                    if (mX - event.getX() > 100 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveRight()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveLeft(true);
                        isPlaying = true;
                    }
                    else if (event.getX() - mX > 100 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveLeft()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveRight(true);
                        isPlaying = true;
                    }
                    else if (mY - event.getY() > 100 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveDown()) {
                        mX = event.getX();
                        mY = event.getY();
                        snake.setMoveUp(true);
                        isPlaying = true;
                    }
                    else if (event.getY() - mY > 100 * ScreenSize.SCREEN_WIDTH / 1080 && !snake.isMoveUp()) {
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

    // ??????????????????
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
            for (int i = 1; i < snake.getArrayListPartSnake().size(); i++){
                if (snake.getArrayListPartSnake().get(0).getRectBody().intersect(snake.getArrayListPartSnake().get(i).getRectBody())){
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
            OfflineGame.score.setText(String.valueOf(score));
            if (score > bestScore) {
                bestScore = score;
                OfflineGame.bestScore.setText(String.valueOf(bestScore));
            }
        }
        handler.postDelayed(runnable, 100);
    }

    // ???????????????? ????????????
    public int[] appleGeneration() {
        int [] xy = new int [2];
        Random random = new Random();
        xy[0] =  random.nextInt(arrayGrass.size() - 1);
        xy[1] = random.nextInt(arrayGrass.size() - 1);
        Rect rect = new Rect(arrayGrass.get(xy[0]).getX(), arrayGrass.get(xy[1]).getY(), arrayGrass.get(xy[0]).getX() + fieldSize, arrayGrass.get(xy[1]).getY() + fieldSize);
        boolean check = true;
        while (check == true) {
            check = false;
            for (int i = 0; i < snake.getArrayListPartSnake().size(); i++) {
                if (rect.intersect(snake.getArrayListPartSnake().get(i).getRectBody())) {
                    check = true;
                    xy[0] = random.nextInt(arrayGrass.size() - 1);
                    xy[1] = random.nextInt(arrayGrass.size() - 1);
                    rect = new Rect(arrayGrass.get(xy[0]).getX(), arrayGrass.get(xy[1]).getY(), arrayGrass.get(xy[0]).getX() + fieldSize, arrayGrass.get(xy[1]).getY() + fieldSize);
                }
            }
        }
        return xy;
    }

    // ???????????????????? ????????
    private void gameOver(){
        isPlaying = false;
        activity.gameOver(score);
        if (bestScore > database.select(1).points) {
            ScoreForDB scoreForDB = new ScoreForDB(1, score);
            database.update(scoreForDB);
        }
    }

    // ???????????????????? ???????????????? ????????
    public void reset(){
        snake = new Snake(bitmapSnake, arrayGrass.get(126).getX(), arrayGrass.get(126).getY(), 4);
        apple = new Apple(bitmapApple, arrayGrass.get(appleGeneration()[0]).getX(), arrayGrass.get(appleGeneration()[1]).getY());
        score = 0;
        bestScore = database.select(1).points;
        OfflineGame.score.setText(String.valueOf(score));
        OfflineGame.bestScore.setText(String.valueOf(bestScore));
    }
}
