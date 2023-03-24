package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

public class RunRun extends AppCompatActivity {

    public static TextView score, bestScore;
    public static RunGameView runGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ScreenSize.SCREEN_HEIGHT = displayMetrics.heightPixels;
        ScreenSize.SCREEN_WIDTH = displayMetrics.widthPixels;
        setContentView(R.layout.activity_run_run);
        runGameView = findViewById(R.id.runGameView);
        score = findViewById(R.id.score);
        bestScore = findViewById(R.id.bestScore);
        bestScore.setText(String.valueOf(RunGameView.bestScore));
    }

    public void gameOver(int score) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        RunDialogFragment runDialogFragment = new RunDialogFragment(score);
        runDialogFragment.setCancelable(false);
        runDialogFragment.show(fragmentManager, "result");
    }
}