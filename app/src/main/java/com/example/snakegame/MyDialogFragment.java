package com.example.snakegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends AppCompatDialogFragment {
    int points;
    GameView gameView = OfflineGame.gameView;

    public MyDialogFragment(int points) {
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
                gameView.score = 0;
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(buttonPlayText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gameView.reset();
                dialog.cancel();
            }
        });
        builder.setCancelable(true);

        return builder.create();
    }
}
