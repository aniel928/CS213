package com.example.anne.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne on 4/16/18.
 */

public class Rook extends Piece {
    public Rook(Player color) {
        super(color, PieceName.ROOK);
    }

    @Override
    public List<int[]> allLegalMoves(int row, int col, ChessBoard board){
        List<int[]> moves = new ArrayList<int[]>();

        //all of this column
        for(int i = 0; i < 8; i++) {
            if(row != i) {
                int arr[] = {i, col};
                moves.add(arr);
            }
        }

        //all of this row
        for(int i = 0; i < 8; i++) {
            if(col != i) {
                int arr[] = {row, i};
                moves.add(arr);
            }
        }
        return moves;
    }

    @Override
    public boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        return ((startRow == endRow) || (startCol == endCol));
    }

    @Override
    public boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        if(startRow == endRow) {
            if(startCol > endCol) {
                int temp = --startCol;
                startCol = ++endCol;
                endCol = temp;
            }
            else {
                startCol++;
                endCol--;
            }
            int i = startCol;
            while(i <= endCol) {
                if(board.getPiece(startRow, i) != null) {
                    if(board.getPiece(startRow, i).getName() != PieceName.GHOST) {
                        return false;
                    }
                }
                i++;
            }
        }
        else if(startCol == endCol){
            if(startRow > endRow) {
                int temp = --startRow;
                startRow = ++endRow;
                endRow = temp;
            }
            else {
                startRow++;
                endRow--;
            }
            int i = startRow;
            while(i <= endRow) {
                if(board.getPiece(i, startCol) != null) {
                    if(board.getPiece(i, startCol).getName() != PieceName.GHOST) {
                        return false;
                    }
                }
                i++;
            }

        }
        return true;
    }
}
