package com.example.anne.chess.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.anne.chess.R;

import java.util.ArrayList;


public class ListGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_games);

        ListView gameListView = findViewById(R.id.gameListView);

        //get list of old games and put into array (TODO: pull old games);
        ArrayList<String> list = new ArrayList<>();
        list.add("stuff");
        list.add("here");
        list.add("later");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        gameListView.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
