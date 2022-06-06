package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class OfflineGame extends AppCompatActivity {

    public static TextView score, bestScore;
    public static GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ScreenSize.SCREEN_HEIGHT = displayMetrics.heightPixels;
        ScreenSize.SCREEN_WIDTH = displayMetrics.widthPixels;
        setContentView(R.layout.activity_offline_game);
        gameView = findViewById(R.id.gameView);
        score = findViewById(R.id.score);
        bestScore = findViewById(R.id.bestScore);
        bestScore.setText(String.valueOf(GameView.bestScore));
    }
    // проигрыш
    public void gameOver(int score) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyDialogFragment myDialogFragment = new MyDialogFragment(score);
        myDialogFragment.setCancelable(false);
        myDialogFragment.show(fragmentManager, "result");
    }
}