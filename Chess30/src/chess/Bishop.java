package chess;

import java.util.List;

public class Bishop extends Piece {
	
	public Bishop(String color) {
		this.color = color;
		this.piece = "Bishop";
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
		return Math.abs(startRow - endRow) == Math.abs(startCol - endCol);
	}
	
	@Override
	protected boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board) {
		boolean properOrder = true;
		
		//move is up/left, make it the same as down/right (eliminate endpoints)
		if(startRow > endRow && startCol > endCol) {
			int temp = --startRow;   //6
			startRow = ++endRow;     //6
			endRow = temp;			//6
			temp = --startCol;      
			startCol = ++endCol;    
			endCol = temp;          

		}
		//move is down/right, eliminate endpoints
		else if(startRow < endRow && startCol < endCol) {
			startRow++;
			endRow--;
			startCol++;
			endCol--;
		}
		//move is up/right, make sme as down/left (elminate endpoints)
		else if(startRow > endRow && startCol < endCol) {
			properOrder = false;
			int temp = --startRow;	
			startRow = ++endRow;
			endRow = temp;
			temp = ++startCol;				
			startCol = --endCol;
			endCol = temp;
		}
		//move is down/left, eliminate endpoints
		else if(startRow < endRow && startCol > endCol) {
			properOrder = false;
			startRow++;
			endRow--;
			startCol--;
			endCol++;
		}else {
			System.out.println("this should never happen");
		}
		int i = startRow;
		int j = startCol;
		
			while(i <= endRow) {
				if(board.getPiece(i, j) != null) {
					return false;
				}
				i++;
				if(properOrder) {
					j++;
				}else {
					j--;
				}
			}
		
		return true;
	}
	
	@Override
	public String toString() {
		if(this.color == "White") {
			return "wB";
		}
		else {
			return "bB";
		}
	}

	@Override
	protected List<Integer[]> validMoves(int startRow, int startCol) {
		// TODO Auto-generated method stub
		return null;
	}

}
