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
		// TODO Auto-generated method stub
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
