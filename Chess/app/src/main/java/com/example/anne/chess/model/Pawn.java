package com.example.anne.chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anne on 4/16/18.
 */

public class Pawn extends Piece {
    public Pawn(Player color) {
        super(color, PieceName.PAWN);
    }

    @Override
    protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
        List<int[]> moves = new ArrayList<int[]>();
        if(this.getColor() == Player.WHITE) {
            //first move
            if(row == 6) {
                if(board.getPiece(row - 2, col) == null) {
                    int[] arr = {row - 2, col};
                    moves.add(arr);
                }
            }
            if(board.getPiece(row - 1, col) == null) {
                int[] arr = {row - 1, col};
                moves.add(arr);
            }
            if(col > 0) {
                if(board.getPiece(row - 1, col - 1) != null && board.getPiece(row - 1, col - 1).getName() != PieceName.GHOST) {
                    int[] arr = {row - 1, col - 1};
                    moves.add(arr);
                }
            }
            if(col < 7) {
                if(board.getPiece(row - 1, col + 1) != null && board.getPiece(row - 1, col + 1).getName() != PieceName.GHOST) {
                    int[] arr = {row - 1, col + 1};
                    moves.add(arr);
                }
            }
        }else {
            //first move
            if(row == 1) {
                if(board.getPiece(row + 2, col) == null) {
                    int[] arr = {row + 2, col};
                    moves.add(arr);
                }

            }
            if(board.getPiece(row + 1, col) == null) {
                int[] arr = {row + 1, col};
                moves.add(arr);
            }
            if(col > 0) {
                if(board.getPiece(row + 1, col - 1) != null && board.getPiece(row + 1, col - 1).getName() != PieceName.GHOST) {
                    int[] arr = {row + 1, col - 1};
                    moves.add(arr);
                }
            }
            if(col < 7) {
                if(board.getPiece(row + 1, col + 1) != null && board.getPiece(row + 1, col + 1).getName() != PieceName.GHOST) {
                    int[] arr = {row + 1, col + 1};
                    moves.add(arr);
                }
            }
        }
        return moves;
    }

    @Override
    protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        //if White pawn
        if(this.getColor() == Player.WHITE) {
            //cover first move - white
            if(startCol == endCol && startRow == 6 && endRow == 4) {
                if(board.getPiece(endRow, endCol) != null) {
                    return false;
                }
                return true;
            }
            if(startCol == endCol && (startRow == (endRow + 1))){
                if(board.getPiece(endRow, endCol) != null && board.getPiece(endRow,  endCol).getColor() == Player.BLACK) {
                    return false;
                }
                return true;
            }

            if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
                if(board.getPiece(endRow, endCol) == null) {
                    return false;
                }if(board.getPiece(endRow, endCol).getColor() == Player.WHITE) {
                    return false;
                }
                return true;
            }
        }
        //black pawn
        else {
            //cover first move - black
            if(startCol == endCol && startRow == 1 && endRow == 3) {
                if(board.getPiece(endRow, endCol) != null) {
                    return false;
                }
                return true;
            }
            if(startCol == endCol && (startRow == (endRow - 1))){
                if(board.getPiece( endRow, endCol) != null && board.getPiece(endRow, endCol).getColor() == Player.WHITE) {
                    return false;
                }
                return true;
            }
            if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow - 1))) {
                if(board.getPiece(endRow, endCol) == null || board.getPiece(endRow, endCol).getColor() == Player.BLACK) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
        if(this.getColor() == Player.WHITE){
            if(startRow == 6 && endRow == 4) {
                if(board.getPiece(5, startCol) != null) {
                    return false;
                }
                Piece ghostPawn = new GhostPawn(this.getColor());
                board.setPiece(5, startCol, ghostPawn);
            }
        }
        else{
            if(startRow == 1 && endRow == 3) {
                if(board.getPiece(2, startCol) != null) {
                    return false;
                }
                Piece ghostPawn = new GhostPawn(this.getColor());
                board.setPiece(2, startCol, ghostPawn);
            }
        }
        return true;
    }
}
