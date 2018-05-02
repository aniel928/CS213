package com.example.anne.chess.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Chess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ListGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);
        final Button sortButton = findViewById(R.id.sortButton);
        final ListView gameListView = findViewById(R.id.gameListView);

        //get list of old games and put into array
        final ArrayList<String> list = new ArrayList<>();

        for(Chess game : MainActivity.chessGames){
            list.add(game.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MainActivity.chessGames);
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
        final String[] value = {sortButton.getText().toString()};
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(value[0].equals("Sort By Date")){
                    value[0] = "Sort By Name";
                    sortButton.setText("Sort By Name");
                    MainActivity.chessGames.sort(Comparator.comparing(Chess::getTimeOfSave));
                    ArrayAdapter adapter = new ArrayAdapter<>(ListGamesActivity.this, android.R.layout.simple_list_item_1, MainActivity.chessGames);
                    gameListView.setAdapter(adapter);
                }else {
                    value[0] = "Sort By Date";
                    sortButton.setText("Sort By Date");
                    MainActivity.chessGames.sort(Comparator.comparing(Chess::getName));
                    ArrayAdapter adapter = new ArrayAdapter<>(ListGamesActivity.this, android.R.layout.simple_list_item_1, MainActivity.chessGames);
                    gameListView.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
