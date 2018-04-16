package com.example.anne.chess.model;

import java.util.List;

/**
 * Created by Anne on 4/16/18.
 */

public class King extends Piece {
    public King(Player color) {
        super(color, PieceName.KING);
    }

    @Override
    protected List<int[]> allLegalMoves(int row, int col, ChessBoard board) {
        return null;
    }

    @Override
    protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        return false;
    }

    @Override
    protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        return false;
    }
}
