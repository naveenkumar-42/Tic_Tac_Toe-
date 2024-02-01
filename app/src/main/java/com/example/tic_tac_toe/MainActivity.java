package com.example.tic_tac_toe;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;

import android.os.Bundle;

import android.view.View;

import android.widget.Button;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean playerOneActive;
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button reset, playAgain;

    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private int playerOneScoreCount, playerTwoScoreCount;
    private int rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.score_Player1);
        playerTwoScore = findViewById(R.id.score_Player2);
        playerStatus = findViewById(R.id.textStatus);
        reset = findViewById(R.id.btn_reset);
        playAgain = findViewById(R.id.btn_play_again);

        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        for (Button button : buttons) {
            button.setOnClickListener(this);
        }

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        resetGame();
    }

    @Override
    public void onClick(View view) {
        if (!isCellEmpty((Button) view) || checkWinner()) {
            return;
        }

        String buttonId = getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1));

        if (playerOneActive) {
            setCellText((Button) view, "X", Color.parseColor("#00008B"));
            gameState[gameStatePointer] = 0;
        } else {
            setCellText((Button) view, "O", Color.parseColor("#00008B"));
            gameState[gameStatePointer] = 1;
        }

        rounds++;

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++;
                playerStatus.setText("Player 1 has won");
            } else {
                playerTwoScoreCount++;
                playerStatus.setText("Player 2 has won");
            }
            updatePlayerScore();
        } else if (rounds == 9) {
            playerStatus.setText("No Winner");
        } else {
            playerOneActive = !playerOneActive;
        }
    }

    private boolean isCellEmpty(Button button) {
        return button.getText().toString().isEmpty();
    }

    private void setCellText(Button button, String text, int textColor) {
        button.setText(text);
        button.setTextColor(textColor);
    }

    private boolean checkWinner() {
        for (int[] winningPosition : winningPositions) {
            int position1 = winningPosition[0];
            int position2 = winningPosition[1];
            int position3 = winningPosition[2];

            if (gameState[position1] == gameState[position2] &&
                    gameState[position2] == gameState[position3] &&
                    gameState[position1] != 2) {
                return true;
            }
        }
        return false;
    }

    private void resetGame() {
        rounds = 0;
        playerOneActive = true;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;

        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }

        updatePlayerScore();
        playerStatus.setText("Status");
    }

    private void updatePlayerScore() {
        playerOneScore.setText(String.valueOf(playerOneScoreCount));
        playerTwoScore.setText(String.valueOf(playerTwoScoreCount));
    }
}
