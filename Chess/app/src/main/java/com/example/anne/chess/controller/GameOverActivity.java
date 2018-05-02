package com.example.anne.chess.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Chess;
import com.example.anne.chess.model.Player;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Button saveButton = findViewById(R.id.saveButton);
        Button homeButton = findViewById(R.id.homeButton);

        TextView gameOverText = findViewById(R.id.gameOverText);
        TextView winnerText= findViewById(R.id.winnerText);

        final EditText gameName = findViewById(R.id.gameName);


        final Intent intent = getIntent();
        gameOverText.setText(intent.getStringExtra("gameOver"));
        winnerText.setText(intent.getStringExtra("winner"));
        final Boolean replay = intent.getBooleanExtra("replay", false);

        if(replay){
            findViewById(R.id.textView4).setVisibility(View.INVISIBLE);
            gameName.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.INVISIBLE);
        }else {

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gameName.getText().toString() == null || gameName.getText().toString().equals("")) {
                        Toast.makeText(GameOverActivity.this, "Please fill out name field!", Toast.LENGTH_SHORT).show();
                    } else {
                        MainActivity.chessGames.get(MainActivity.chessGames.size() - 1).setName(gameName.getText().toString());
                        MainActivity.chessGames.get(MainActivity.chessGames.size() - 1).setTimeOfSave(LocalDateTime.now());
                        MainActivity.chessGames.get(MainActivity.chessGames.size() - 1).setWinner(intent.getStringExtra("winner"));
                        MainActivity.chessGames.get(MainActivity.chessGames.size() - 1).setMethod(intent.getStringExtra("gameOver"));


                        Intent i = new Intent(GameOverActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                        finish();
                    }
                }
            });
        }
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!replay) {
                    MainActivity.chessGames.remove(MainActivity.chessGames.size() - 1);
                }
                Intent i= new Intent(GameOverActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();
            }


        });
    }

}
