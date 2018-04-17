package com.example.anne.chess.model;

import android.widget.ImageView;

import com.example.anne.chess.R;
import com.example.anne.chess.controller.GameActivity;

/**
 * Created by Anne on 4/16/18.
 */

public class ChessBoard {
    final static int ROWS = 8;
    final static int COLS = 8;
    private Piece[][] positions = new Piece[ROWS][COLS];

    public ChessBoard(){
        initialize();
    }

    /*
        GETTERS / SETTERS
     */
    public static int getROWS() {
        return ROWS;
    }

    public static int getCOLS() {
        return COLS;
    }

    public Piece[][] getPositions() {
        return positions;
    }

    public void setPositions(Piece[][] positions) {
        this.positions = positions;
    }

    /*
        HELPER METHODS
     */

    private void initialize() {
        positions[0][0] = new Rook(Player.BLACK);
        positions[0][1] = new Knight(Player.BLACK);
        positions[0][2] = new Bishop(Player.BLACK);
        positions[0][3] = new Queen(Player.BLACK);
        positions[0][4] = new King(Player.BLACK);
        positions[0][5] = new Bishop(Player.BLACK);
        positions[0][6] = new Knight(Player.BLACK);
        positions[0][7] = new Rook(Player.BLACK);

        positions[1][0] = new Pawn(Player.BLACK);
        positions[1][1] = new Pawn(Player.BLACK);
        positions[1][2] = new Pawn(Player.BLACK);
        positions[1][3] = new Pawn(Player.BLACK);
        positions[1][4] = new Pawn(Player.BLACK);
        positions[1][5] = new Pawn(Player.BLACK);
        positions[1][6] = new Pawn(Player.BLACK);
        positions[1][7] = new Pawn(Player.BLACK);

        positions[6][0] = new Pawn(Player.WHITE);
        positions[6][1] = new Pawn(Player.WHITE);
        positions[6][2] = new Pawn(Player.WHITE);
        positions[6][3] = new Pawn(Player.WHITE);
        positions[6][4] = new Pawn(Player.WHITE);
        positions[6][5] = new Pawn(Player.WHITE);
        positions[6][6] = new Pawn(Player.WHITE);
        positions[6][7] = new Pawn(Player.WHITE);

        positions[7][0] = new Rook(Player.WHITE);
        positions[7][1] = new Knight(Player.WHITE);
        positions[7][2] = new Bishop(Player.WHITE);
        positions[7][3] = new Queen(Player.WHITE);
        positions[7][4] = new King(Player.WHITE);
        positions[7][5] = new Bishop(Player.WHITE);
        positions[7][6] = new Knight(Player.WHITE);
        positions[7][7] = new Rook(Player.WHITE);
    }
}
