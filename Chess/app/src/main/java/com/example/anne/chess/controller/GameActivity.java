package com.example.anne.chess.controller;


import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anne.chess.R;
import com.example.anne.chess.model.Bishop;
import com.example.anne.chess.model.ChessBoard;
import com.example.anne.chess.model.Chess;
import com.example.anne.chess.model.Knight;
import com.example.anne.chess.model.Piece;
import com.example.anne.chess.model.Player;
import com.example.anne.chess.model.Queen;
import com.example.anne.chess.model.Rook;

public class GameActivity extends AppCompatActivity {
    private Button undoButton;
    private Button resignButton;
    private Button AIbutton;
    private Button drawButton;

    private TextView textView;
    private ImageView[][] pieces = new ImageView[8][8];
    private Piece[][] positions = new Piece[8][8];
    private Chess game;
    private ChessBoard board;
    private int startRow = -1;
    private int startCol = -1;
    private int endRow = -1;
    private int endCol = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        undoButton = findViewById(R.id.undoButton);
        resignButton = findViewById(R.id.resignButton);
        AIbutton = findViewById(R.id.AIButton);
        drawButton = findViewById(R.id.drawButton);

        textView = findViewById(R.id.textView);

        game = new Chess();

        board = new ChessBoard();

        game.startGame(board);

        getAllImageViews();

        drawBoard();

        setListeners();
    }

    private void setListeners() {


        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: logic
                Toast.makeText(GameActivity.this,"Undo", Toast.LENGTH_SHORT).show();
            }
        });

        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: logic for new screen to announce winner and ask to save game
                Toast.makeText(GameActivity.this,"Resign", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                if(game.getTurn() == Player.WHITE){
                    intent.putExtra("gameOver", "\nWhite Resigns!");
                    intent.putExtra("winner", "Black Wins!");
                }
                else {
                    intent.putExtra("gameOver", "\nBlack Resigns!!");
                    intent.putExtra("winner", "White Wins!");

                }
                startActivity(intent);

            }
        });

        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: logic for new screen to announce winner and ask to save game
                Toast.makeText(GameActivity.this,"Draw", Toast.LENGTH_SHORT).show();
            }
        });

        AIbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: logic
                Toast.makeText(GameActivity.this,"AI", Toast.LENGTH_SHORT).show();
            }
        });


        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                final int finalI = i;
                final int finalJ = j;

                // TODO: TRY TO CHANGE THIS TO ONDRAG LISTENER

                pieces[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(startRow == -1 || startCol == -1){
                            //Set the starting coordinates
                            startRow = finalI;
                            startCol = finalJ;

                            pieces[startRow][startCol].setBackgroundTintList(ColorStateList.valueOf(Color.RED));


                        }
                        else{
                            pieces[startRow][startCol].setBackgroundTintList(null);
                            //starting coordinates already set, so this click must be ending coordinates
                            endRow = finalI;
                            endCol = finalJ;

                            Log.d("GameActivity: ","Move from (" + startRow + ", " + startCol + ") to (" + endRow + ", " + endCol + ")");

                            int status = game.move(startRow, endRow, startCol, endCol);

                            if(status == -1){
                                Toast.makeText(GameActivity.this,"Invalid Move!", Toast.LENGTH_LONG).show();
                                startRow = startCol = endRow = endCol = -1;

                            }else{
                                //TODO: figure out how to add callbacks
                                if(game.checkForPromotion(startRow, startCol)){
                                    //pop up window to ask what to promote to and handle promotion.
                                    showButtonDialog(startRow, startCol, game);
                                }else {
                                    doStuff();
                                }
                            }
                        }
                    }
                });

            }
        }
    }

    private void doStuff() {
        int status;
        status = game.completeTheMove(startRow, endRow, startCol, endCol);

        if(status == -1){
            Toast.makeText(GameActivity.this,"Invalid Move!", Toast.LENGTH_LONG).show();
        }
        else{
            status = game.newTurn();
            if(game.getTurn() == Player.WHITE) {
                if(status == 1){
                    textView.setText("White's move -- Check!");
                }else if(status == 2){
                    Intent intent = new Intent(this, GameOverActivity.class);
                    intent.putExtra("gameOver", "\nCheckMate!");
                    intent.putExtra("winner", "Black Wins!");
                    startActivity(intent);
                    textView.setText("Checkmate! Black Wins!");
                }else {
                    textView.setText("White's move");
                }
            }else {
                if (status == 1) {
                    textView.setText("Black's move -- Check!");
                } else if (status == 2) {
                    //TODO: remove this code and change screens.
                    Intent intent = new Intent(this, GameOverActivity.class);
                    intent.putExtra("gameOver", "\nCheckMate!");
                    intent.putExtra("winner", "White Wins!");
                    startActivity(intent);
                    textView.setText("Checkmate! White Wins!");
                } else {
                    textView.setText("Black's move");
                }
            }
            drawBoard();
            startRow = startCol = endRow = endCol = -1;

        }
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

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    private void showButtonDialog(final int row, final int col, final Chess game) {

        final Piece[] piece = new Piece[1];
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.button_dialog);

        Button rook = dialog.findViewById(R.id.rook);
        Button knight = dialog.findViewById(R.id.knight);
        Button bishop = dialog.findViewById(R.id.bishop);
        Button queen = dialog.findViewById(R.id.queen);

        dialog.show();

        rook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piece[0] = new Rook(game.getTurn());
                board.setPiece(row, col, piece[0]);
                dialog.dismiss();
                doStuff();

            }
        });

        knight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piece[0] = new Knight(game.getTurn());
                board.setPiece(row, col, piece[0]);
                dialog.dismiss();
                doStuff();

            }
        });

        bishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piece[0] = new Bishop(game.getTurn());
                board.setPiece(row, col, piece[0]);
                doStuff();
                dialog.dismiss();
            }
        });

        queen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                piece[0] = new Queen(game.getTurn());
                board.setPiece(row, col, piece[0]);
                dialog.dismiss();
                doStuff();

            }
        });
    }

}
