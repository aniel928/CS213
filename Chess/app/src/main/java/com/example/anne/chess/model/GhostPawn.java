package com.example.anne.chess.model;

import java.util.List;

/**
 * Created by Anne on 4/16/18.
 */

public class GhostPawn extends Piece {

    public GhostPawn(Player color) {
        super(color, PieceName.GHOST);
    }

    @Override
    public List<int[]> allLegalMoves(int row, int col, ChessBoard board) {
        return null;
    }

    @Override
    public boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        return false;
    }

    @Override
    public boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        return false;
    }

}
