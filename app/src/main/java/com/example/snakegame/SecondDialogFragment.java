package com.example.snakegame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SecondDialogFragment extends AppCompatDialogFragment {
    int points;
    SecondGameView gameView = SecondMode.gameView;

    public SecondDialogFragment(int points) {
        this.points = points;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = "Game Over!";
        String buttonMenuText = "Go to menu";
        String buttonPlayText = "Play again";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage("Your result: " + points + " points");
        builder.setPositiveButton(buttonMenuText, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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
