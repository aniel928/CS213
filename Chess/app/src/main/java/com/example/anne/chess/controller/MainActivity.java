package com.example.anne.chess.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Chess;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<Chess> chessGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newGame = findViewById(R.id.newGame);
        Button oldGame = findViewById(R.id.oldGame);

        newGame.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, GameActivity.class);
               startActivity(intent);
           }
       });

        oldGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListGamesActivity.class);
                startActivity(intent);
            }
        });
    }
}
