package com.example.anne.chess.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.anne.chess.R;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button saveButton = findViewById(R.id.saveButton);
        Button playAgainButton = findViewById(R.id.playAgainButton);
        Button homeButton = findViewById(R.id.homeButton);

        TextView gameOverText = findViewById(R.id.gameOverText);
        TextView winnerText= findViewById(R.id.winnerText);

        Intent intent = getIntent();
        gameOverText.setText(intent.getStringExtra("gameOver"));
        winnerText.setText(intent.getStringExtra("winner"));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save here and on device
            }
        });

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
