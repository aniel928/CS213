package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	
	
	public Pawn(String color) {
		this.color = color;
		this.piece = "Pawn";
	}
	
	@Override
	protected String getColor() {
		return this.color;
	}

	@Override
	protected String getPiece() {
		return this.piece;
	}
	
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {	
		//if White pawn
		if(this.color.equals("White")) {
			//cover first move - white
			if(startCol == endCol && startRow == 2 && endRow == 4) {
				if(board.getPiece(board.ROWS - endRow, endCol) != null) {
					return false;
				}
				return true;
			}
			if(startCol == endCol && (startRow == (endRow - 1))){
				if(board.getPiece(board.ROWS - endRow, endCol) != null && board.getPiece(board.ROWS - endRow,  endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
			
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow - 1))) {
				if(board.getPiece(board.ROWS - endRow, endCol) == null) {
					return false;
				}if(board.getPiece(board.ROWS - endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
		}
		//black pawn
		else {
			//cover first move - black
			if(startCol == endCol && startRow == 7 && endRow == 5) {
				if(board.getPiece(board.ROWS - endRow, endCol) != null) {
					return false;
				}
				return true;
			}
			if(startCol == endCol && (startRow == (endRow + 1))){
				if(board.getPiece(board.ROWS - endRow, endCol) != null && board.getPiece(board.ROWS - endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
				if(board.getPiece(board.ROWS - endRow, endCol) == null || board.getPiece(board.ROWS - endRow, endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		if(this.color.equals("White")){
			if(startRow == 6 && endRow == 4) {
				if(board.getPiece(5, startCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn(this.color);
				board.setPiece(5, startCol, ghostPawn);
			}
		}
		else{
			if(startRow == 1 && endRow == 3) {
				if(board.getPiece(2, startCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn(this.color);
				board.setPiece(2, startCol, ghostPawn);
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wp";
		}
		else {
			return "bp";
		}
	}

	@Override
	protected boolean checkForCheckMate(int startRow, int startCol, int[] kingSpot){	
		return false;
	}


}
