package com.example.anne.chess.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Chess;
import com.example.anne.chess.model.ChessBoard;
import com.example.anne.chess.model.Piece;
import com.example.anne.chess.model.Player;

public class ReplayActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView[][] pieces = new ImageView[8][8];
    private Piece[][] positions = new Piece[8][8];
    private Chess game;
    private static ChessBoard board;
    private int startRow = -1;
    private int startCol = -1;
    private int endRow = -1;
    private int endCol = -1;
    private Button nextButton;
    private int moveIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);

        textView = findViewById(R.id.textView);

        nextButton = findViewById(R.id.nextButton);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);

        if(index != -1) {
            game = MainActivity.chessGames.get(index);
        }
        board = new ChessBoard();

        getAllImageViews();

        drawBoard();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(moveIndex < (game.moves.size() -1)){
                    moveIndex++;
                    int[] move = game.moves.get(moveIndex);
                    Piece piece = board.getPiece(move[0], move[1]);
                    board.setPiece(move[0], move[1], null);
                    board.setPiece(move[2], move[3], piece);
                    drawBoard();
                }else{
                    Intent intent = new Intent(ReplayActivity.this, GameOverActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private void drawBoard() {
        int image;
        positions = board.getPositions();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(positions[i][j] == null){
                    pieces[i][j].setImageDrawable(null);
                }
                else if((image = positions[i][j].getImageId()) != 0){
                    pieces[i][j].setImageDrawable(ContextCompat.getDrawable(this, image));
                }
                else{
                    pieces[i][j].setImageDrawable(null);
                }
            }
        }
    }

    private void getAllImageViews() {
        pieces[0][0] = findViewById(R.id.a8);
        pieces[0][1] = findViewById(R.id.b8);
        pieces[0][2] = findViewById(R.id.c8);
        pieces[0][3] = findViewById(R.id.d8);
        pieces[0][4] = findViewById(R.id.e8);
        pieces[0][5] = findViewById(R.id.f8);
        pieces[0][6] = findViewById(R.id.g8);
        pieces[0][7] = findViewById(R.id.h8);

        pieces[1][0] = findViewById(R.id.a7);
        pieces[1][1] = findViewById(R.id.b7);
        pieces[1][2] = findViewById(R.id.c7);
        pieces[1][3] = findViewById(R.id.d7);
        pieces[1][4] = findViewById(R.id.e7);
        pieces[1][5] = findViewById(R.id.f7);
        pieces[1][6] = findViewById(R.id.g7);
        pieces[1][7] = findViewById(R.id.h7);

        pieces[2][0] = findViewById(R.id.a6);
        pieces[2][1] = findViewById(R.id.b6);
        pieces[2][2] = findViewById(R.id.c6);
        pieces[2][3] = findViewById(R.id.d6);
        pieces[2][4] = findViewById(R.id.e6);
        pieces[2][5] = findViewById(R.id.f6);
        pieces[2][6] = findViewById(R.id.g6);
        pieces[2][7] = findViewById(R.id.h6);

        pieces[3][0] = findViewById(R.id.a5);
        pieces[3][1] = findViewById(R.id.b5);
        pieces[3][2] = findViewById(R.id.c5);
        pieces[3][3] = findViewById(R.id.d5);
        pieces[3][4] = findViewById(R.id.e5);
        pieces[3][5] = findViewById(R.id.f5);
        pieces[3][6] = findViewById(R.id.g5);
        pieces[3][7] = findViewById(R.id.h5);

        pieces[4][0] = findViewById(R.id.a4);
        pieces[4][1] = findViewById(R.id.b4);
        pieces[4][2] = findViewById(R.id.c4);
        pieces[4][3] = findViewById(R.id.d4);
        pieces[4][4] = findViewById(R.id.e4);
        pieces[4][5] = findViewById(R.id.f4);
        pieces[4][6] = findViewById(R.id.g4);
        pieces[4][7] = findViewById(R.id.h4);

        pieces[5][0] = findViewById(R.id.a3);
        pieces[5][1] = findViewById(R.id.b3);
        pieces[5][2] = findViewById(R.id.c3);
        pieces[5][3] = findViewById(R.id.d3);
        pieces[5][4] = findViewById(R.id.e3);
        pieces[5][5] = findViewById(R.id.f3);
        pieces[5][6] = findViewById(R.id.g3);
        pieces[5][7] = findViewById(R.id.h3);

        pieces[6][0] = findViewById(R.id.a2);
        pieces[6][1] = findViewById(R.id.b2);
        pieces[6][2] = findViewById(R.id.c2);
        pieces[6][3] = findViewById(R.id.d2);
        pieces[6][4] = findViewById(R.id.e2);
        pieces[6][5] = findViewById(R.id.f2);
        pieces[6][6] = findViewById(R.id.g2);
        pieces[6][7] = findViewById(R.id.h2);

        pieces[7][0] = findViewById(R.id.a1);
        pieces[7][1] = findViewById(R.id.b1);
        pieces[7][2] = findViewById(R.id.c1);
        pieces[7][3] = findViewById(R.id.d1);
        pieces[7][4] = findViewById(R.id.e1);
        pieces[7][5] = findViewById(R.id.f1);
        pieces[7][6] = findViewById(R.id.g1);
        pieces[7][7] = findViewById(R.id.h1);
    }

}
