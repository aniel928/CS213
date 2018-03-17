package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {

	public Knight(String color) {
		this.color = color;
		this.piece = "Knight";
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
	protected List<int[]> allLegalMoves(int row, int col, ChessBoard board){
		List<int[]> moves = new ArrayList<int[]>();
		//top
		if(row > 1) {
			if(col > 0) {
				int[] arr = {row - 2, col - 1};
				moves.add(arr);
			}
			if(col < 7) {
				int[] arr = {row - 2, col + 1};
				moves.add(arr);
			}
		}
		//left
		if(col > 1) {
			if(row > 0) {
				int[] arr = {row - 1, col - 2};
				moves.add(arr);
			}
			if(row < 7) {
				int[] arr = {row + 1, col - 2};
				moves.add(arr);
			}
		}
		//right
		if(col < 6) {
			if(row > 0) {
				int[] arr = {row - 1, col + 2};
				moves.add(arr);
			}
			if(row < 7) {
				int[] arr = {row + 1, col + 2};
				moves.add(arr);		
			}
		}
		//bottom
		if(row < 6) {
			if(col > 0) {
				int[] arr = {row + 2, col - 1};
				moves.add(arr);
			}
			if(col < 7) {
				int[] arr = {row + 2, col + 1};
				moves.add(arr);				
			}
		}
		return moves;
	}
	
	@Override
	protected boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		return (Math.abs(startRow - endRow) == 1 && Math.abs(startCol - endCol) == 2) || (Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 1);
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wN";
		}
		else {
			return "bN";
		}
	}

}
