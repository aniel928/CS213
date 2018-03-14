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
				if(board.getPiece(endRow, endCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn();
				board.setPiece(3, startCol, ghostPawn);
				return true;
			}
			if(startCol == endCol && (startRow == (endRow - 1))){
				if(board.getPiece(board.ROWS - endRow, endCol) != null && board.getPiece(board.ROWS - endRow,  endCol).color.equals("Black")) {
					return false;
				}
				return true;
			}
			System.out.println("sc"+startCol + "ec"+endCol +"sr"+ startRow + "er"+endRow);
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow - 1))) {
				System.out.println("one");
				if(board.getPiece(board.ROWS - endRow, endCol) == null) {
					System.out.println("NULL");
					return false;
				}if(board.getPiece(board.ROWS - endRow, endCol).color.equals("White")) {
					System.out.println("White");
					return false;
				}
				System.out.println("three");
				return true;
			}
		}
		//black pawn
		else {
			//cover first move - black
			if(startCol == endCol && startRow == 7 && endRow == 5) {
				if(board.getPiece(endRow, endCol) != null) {
					return false;
				}
				Piece ghostPawn = new GhostPawn();
				board.setPiece(3, startCol, ghostPawn);
				return true;
			}
			if(startCol == endCol && (startRow == (endRow + 1))){
				if(board.getPiece(board.ROWS - endRow, endCol) != null && board.getPiece(board.ROWS - endRow, endCol).color.equals("White")) {
					return false;
				}
				return true;
			}
			System.out.println("sc"+startCol + "ec"+endCol +"sr"+ startRow + "er"+endRow);
			if(((startCol == (endCol - 1)) || (startCol == endCol + 1)) && (startRow == (endRow + 1))) {
				System.out.println("four");
				if(board.getPiece(board.ROWS - endRow, endCol) == null || board.getPiece(board.ROWS - endRow, endCol).color.equals("Black")) {
					System.out.println("five");
					return false;
				}
				System.out.println("six");
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
			}
		}
		else{
			if(startRow == 1 && endRow == 3) {
				if(board.getPiece(2, startCol) != null) {
					return false;
				}
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
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		
		List<Integer[]> valid = new ArrayList<>();
		
		if(this.color.equals("White") && startRow > 0){
			Integer[] arr = {--startRow, startCol};
			valid.add(arr);
		}
		
		if(this.color.equals("White") && startRow < 7){
			Integer[] arr = {++startRow, startCol};
			valid.add(arr);
		}
		
		//find a way to check for kill move
		
		return null;
	}


}
