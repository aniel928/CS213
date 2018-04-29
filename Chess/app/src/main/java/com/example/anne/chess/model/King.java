package com.example.anne.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne on 4/16/18.
 */

public class King extends Piece {
    public King(Player color) {
        super(color, PieceName.KING);
    }

    @Override
    protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
        List<int[]> moves = new ArrayList<int[]>();
        if(!this.isMoved()) {
            if(this.getColor() == Player.WHITE) {
                int[] arr = {7, 2};
                moves.add(arr);
                int[] arr2 = {7, 6};
                moves.add(arr2);
            }
            else {
                int[] arr = {0, 2};
                moves.add(arr);
                int[] arr2 = {0, 6};
                moves.add(arr2);
            }
        }
        if(row < 7) {
            int[] arr = {row + 1, col};
            moves.add(arr);
            if(col > 0) {
                int[] arr2 = {row + 1, col - 1};
                moves.add(arr2);
            }
            if(col < 7) {
                int arr2[] = {row + 1, col + 1};
                moves.add(arr2);
            }
        }
        if(col > 0) {
            int[] arr = {row, col - 1};
            moves.add(arr);
        }
        if(col < 7) {
            int[] arr = {row, col + 1};
            moves.add(arr);
        }
        if(row > 0) {
            int[] arr = {row - 1, col};
            moves.add(arr);
            if(col > 0) {
                int[] arr2 = {row - 1, col - 1};
                moves.add(arr2);
            }
            if(col < 7) {
                int[] arr2 = {row - 1, col + 1};
                moves.add(arr2);
            }
        }
        return moves;
    }

    @Override
    protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        //if piece hasn't moved and king is trying to move two spaces to the left or right, then look for castle
        if(!this.isMoved() && startRow == endRow) {
            //right side of board
            if(endCol == 6) {
                if(board.getPiece(startRow,  7) != null) {
                    if(board.getPiece(startRow,7).getName() == PieceName.ROOK && !board.getPiece(startRow, 7).isMoved()) {
                        return true;
                    }
                }
            }
            //left side of board.
            if(endCol == 2) {
                if(board.getPiece(startRow,  0) != null) {
                    if(board.getPiece(startRow,  0).getName() == PieceName.ROOK && !board.getPiece(startRow,  0).isMoved()) {
                        return true;
                    }
                }
            }
        }
        return ((startRow == endRow || Math.abs(startRow - endRow) == 1) && (startCol == endCol || Math.abs(startCol - endCol) == 1));
    }

    @Override
    protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        if(startCol - endCol == 2) {
            //Queen Side Castling
            if(board.getPiece(startRow, startCol-1) != null || board.getPiece(startRow, startCol-2) != null || board.getPiece(startRow, startCol-3) != null) {
                return false;
            }
        }else if(endCol - startCol == 2) {
            //Regular Castling
            if(board.getPiece(startRow, startCol+1) != null || board.getPiece(startRow, startCol+2) != null) {
                return false;
            }
        }
        return true;
    }
}
