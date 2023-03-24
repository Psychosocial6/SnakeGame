package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class Time extends AppCompatActivity {

    public static TimeGameView gameView;
    public static TextView score, bestScore, minutes, seconds;
    public static int minutesC, secondsC;
    public static CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ScreenSize.SCREEN_HEIGHT = displayMetrics.heightPixels;
        ScreenSize.SCREEN_WIDTH = displayMetrics.widthPixels;
        setContentView(R.layout.activity_time);
        score = findViewById(R.id.score);
        bestScore = findViewById(R.id.bestScore);
        minutes = findViewById(R.id.minutes);
        seconds = findViewById(R.id.seconds);
        gameView = findViewById(R.id.gameView);
        bestScore.setText(String.valueOf(TimeGameView.bestScore));
        timer = new CountDownTimer((minutesC * 60 + secondsC) * 1000, 1000) {
            @Override
            public void onTick(long millis) {
                int t = (int) millis / 1000;
                minutesC = t / 60;
                secondsC = t % 60;
                minutes.setText(String.valueOf(minutesC));
                seconds.setText(String.valueOf(secondsC));
            }
            @Override
            public void onFinish() {
                gameView.gameOver();
            }
        };
    }
    public void gameOver(int score) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        TimeDialogFragment myDialogFragment = new TimeDialogFragment(score);
        myDialogFragment.setCancelable(false);
        myDialogFragment.show(fragmentManager, "result");
    }
}