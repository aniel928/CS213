package com.example.anne.chess.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Chess;

import java.util.ArrayList;


public class ListGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);

        final ListView gameListView = findViewById(R.id.gameListView);

        //get list of old games and put into array
        final ArrayList<String> list = new ArrayList<>();

        for(Chess game : MainActivity.chessGames){
            list.add(game.getName());
        }

        ArrayAdapter<Chess> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.chessGames);
        gameListView.setAdapter(arrayAdapter);

        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                Object o = gameListView.getItemAtPosition(position);

                Intent intent = new Intent(ListGamesActivity.this, ReplayActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
