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
    private int[] WhiteKing, BlackKing;

    public int[] getWhiteKing() {
        return WhiteKing;
    }

    public void setWhiteKing(int[] whiteKing) {
        WhiteKing = whiteKing;
    }

    public int[] getBlackKing() {
        return BlackKing;
    }

    public void setBlackKing(int[] blackKing) {
        BlackKing = blackKing;
    }

    public void setWhiteKingCol(int col){
        WhiteKing[1] = col;
    }

    public void setBlackKingCol(int col){
        BlackKing[1] = col;
    }

    public int getWhiteKingCol(){
        return WhiteKing[1];
    }

    public int getBlackKingCol(){
        return BlackKing[1];
    }

    public int getWhiteKingRow(){
        return WhiteKing[0];
    }

    public int getBlackKingRow(){
        return BlackKing[0];
    }

    public void setWhiteKingRow(int row){
        WhiteKing[0] = row;
    }

    public void setBlackKingRow(int row){
        BlackKing[0] = row;
    }




    public ChessBoard(){
        initialize();
        WhiteKing = new int[2];
        BlackKing = new int[2];
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

    protected Piece getPiece(int row, int col) {
        return positions[row][col];
    }

    /**
     * Sets the piece given on the square indicated
     * @param row an integer representing an index in the array for rank
     * @param col an integer representing an index in the array for file
     * @param piece a {@link Piece} object to put onto the square
     */
    protected void setPiece(int row, int col, Piece piece) {
        positions[row][col] = piece;
    }

    /**
     * Makes a copy of this board and returns that copy.
     * @return a new instance of ChessBoard.
     */
    protected ChessBoard makeCopy() {
        ChessBoard newBoard = new ChessBoard();
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS; j++) {
                newBoard.positions[i][j] = this.positions[i][j];
            }
        }
        return newBoard;
    }

    /*
        HELPER METHODS
     */

    public void initialize() {
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
