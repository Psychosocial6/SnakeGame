package com.example.snakegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RunDialogFragment extends AppCompatDialogFragment {
    int points;
    RunGameView runGameView = RunRun.runGameView;

    public RunDialogFragment(int points) {
        this.points = points;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = "Game Over!";
        String buttonMenuText = "Go to menu";
        String buttonPlayText = "Play again";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);  // заголовок
        builder.setMessage("Your result: " + points + " points"); // сообщение
        builder.setPositiveButton(buttonMenuText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                runGameView.score = 0;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(buttonPlayText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                runGameView.reset();
                dialog.cancel();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
