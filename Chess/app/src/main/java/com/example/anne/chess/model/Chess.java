package com.example.anne.chess.model;

import android.util.Log;
import java.util.List;


public class Chess{

    private boolean gameOver = false;

    private Player turn = Player.WHITE;

    private boolean draw = false;

    private boolean promote = false;

    private ChessBoard board;

    private void removeEnpassant() {
        int i = 0;
        //if current turn is white, remove any GhostPawn's from rank 3.
        if(turn == Player.WHITE) {
            while(i < 8) {
                if(board.getPiece(5, i) != null && board.getPiece(5, i).getName() == PieceName.GHOST) {
                    board.setPiece(5, i, null);
                    break;
                }
                i++;
            }
        }
        //if current turn in Black, remove any GhostPawn's from rank 6
        else {
            while(i < 8) {
                if(board.getPiece(2,  i) != null && board.getPiece(2, i).getName() == PieceName.GHOST) {
                    board.setPiece(2, i, null);
                    break;
                }
                i++;
            }

        }
    }

    private boolean validMoveForPiece(Piece piece, int startRow, int endRow, int startCol, int endCol) {
        //is there a piece?
        if(piece == null) {
            return false;
        }

        //does it belong to this player?
        if(piece.getColor() != turn) {
            return false;
        }

        //check if move is legal.
        if(!(piece.isLegalMove(startRow, startCol, endRow, endCol, board))) {
            return false;
        }
        //check for pieces in the way
        if(!(piece.coastClear(startRow, startCol, endRow, endCol, board))){
            return false;
        }

        //check if another piece occupies destination (must be opposite color)
        if(board.getPiece(endRow,  endCol) != null && board.getPiece(endRow, endCol).getColor() == turn) {
            return false;
        }

        return true;
    }

    public boolean checkForPromotion(int startRow, int startCol) {
        Piece piece = board.getPiece(startRow, startCol);
        if(piece.getName() == PieceName.PAWN) {
            if((turn == Player.WHITE && startRow == 1) || (turn == Player.BLACK && startRow == 6)) {
               return true;
            }
        }
        return false;
//        //handle promotion
//        if(promote) {
//            String promoteTo = "";
//            if(moves.length < 3) {
//                promoteTo = "Q";
//            }
//            else {
//                promoteTo = moves[2];
//            }
//            piece = promote(promoteTo);
//            promote = false;
//        }
//        return piece;
    }

    private int checkForCastle(Piece piece, int startRow, int endRow, int startCol, int endCol) {
        //check for Castle
        if(piece.getName() == PieceName.KING && Math.abs(startCol - endCol) == 2) {
            //if not in check, then you're good.
            if((turn == Player.BLACK && !blackCheck(board)) || turn == Player.WHITE && !whiteCheck(board)){
                //check to see if squares between are in check
                if(startCol - endCol == 2) {
                    //Queen Side Castle
                    ChessBoard tempBoard = board.makeCopy();
                    tempBoard.setPiece(startRow,  startCol - 1, piece);
                    if(turn == Player.WHITE){
                        int oldCol = board.getWhiteKingCol();
                        board.setWhiteKingCol(--oldCol);
                    }else{
                        int oldCol = board.getBlackKingCol();
                        board.setBlackKingCol(--oldCol);
                    }
                    if(turn == Player.BLACK && blackCheck(tempBoard)){
                        int oldCol = board.getBlackKingCol();
                        board.setBlackKingCol(++oldCol);
                        return -1;
                    }
                    if(turn == Player.WHITE && whiteCheck(tempBoard)) {
                        int oldCol = board.getWhiteKingCol();
                        board.setWhiteKingCol(++oldCol);
                        return -1;
                    }
                }
                if(endCol - startCol == 2) {
                    //King Side Castle
                    ChessBoard tempBoard = board.makeCopy();
                    tempBoard.setPiece(startRow,  startCol + 1, piece);
                    if(turn == Player.WHITE){
                        int oldCol = board.getWhiteKingCol();
                        board.setWhiteKingCol(++oldCol);
                    }else{
                        int oldCol = board.getBlackKingCol();
                        board.setBlackKingCol(++oldCol);
                    }
                    if(turn == Player.BLACK && blackCheck(tempBoard)){
                        int oldCol = board.getBlackKingCol();
                        board.setBlackKingCol(--oldCol);
                        return -1;
                    }
                    if(turn == Player.WHITE && whiteCheck(tempBoard)) {
                        int oldCol = board.getWhiteKingCol();
                        board.setWhiteKingCol(--oldCol);
                        return -1;
                    }
                }
                performCastle(piece, startRow, endRow, startCol, endCol);
                return 0;
            }
            //if in check, not so good.
            else{
                return -1;
            }
        }
        //if not King, no error, but return 1 in case we want to know not King.
        return 1;
    }

    private void performCastle(Piece piece, int startRow, int endRow, int startCol, int endCol){
        //Queen side castle
        if(startCol - endCol == 2) {
            board.setPiece(endRow,  endCol, piece);
            Piece rook = board.getPiece(endRow, 0);
            board.setPiece(endRow, 3, rook);
            board.setPiece(startRow, startCol, null);
            board.setPiece(endRow, 0, null);
            piece.setMoved(true);
            rook.setMoved(true);
        }
        //King-side castle
        else if(startCol - endCol == -2) {
            board.setPiece(endRow,  endCol, piece);
            Piece rook = board.getPiece(endRow, 7);
            board.setPiece(endRow, 5, rook);
            board.setPiece(startRow, startCol, null);
            board.setPiece(endRow, 7, null);
            piece.setMoved(true);
            rook.setMoved(true);
        }
    }

    private void enforceEnpassant(int endCol) {
        if(turn == Player.WHITE) {
            board.setPiece(3, endCol, null);
        }
        else {
            board.setPiece(4,  endCol,  null);
        }
    }

    private boolean whiteCheck(ChessBoard board) {
        for(int i = 0; i < board.ROWS; i++) {
            for(int j = 0; j < board.COLS; j++) {
                Piece piece = board.getPiece(i, j);
                if(piece != null && piece.getColor() == Player.BLACK) {
                    if(piece.isLegalMove(i, j, board.getWhiteKingRow(), board.getWhiteKingCol(), board) && piece.coastClear(i, j, board.getWhiteKingRow(), board.getWhiteKingCol(), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean blackCheck(ChessBoard board) {
        for(int i = 0; i < board.ROWS; i++) {
            for(int j = 0; j < board.COLS; j++) {
                Piece piece = board.getPiece(i, j);
                if(piece != null && piece.getColor() == Player.WHITE) {
                    Log.d("Chess.java: ", "Black row: " + board.getBlackKingRow() + " --- Black Col:" + board.getBlackKingCol());
                    if(piece.isLegalMove(i, j, board.getBlackKingRow(), board.getBlackKingCol(), board) && piece.coastClear(i, j, board.getBlackKingRow(), board.getBlackKingCol(), board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkForMate() {
        //for each row and each col,
        for(int i = 0; i < board.ROWS; i++) {
            for(int j = 0; j < board.COLS; j++) {
                //grab the piece
                Piece tempPiece = board.getPiece(i, j);
                //if it's a piece of the opposite color, get all of it's moves
                if(tempPiece != null  && tempPiece.getColor() != turn) {
                    List <int[]> moves = tempPiece.allLegalMoves(i, j, board);
                    //for each move, create a temporary board and move the piece
                    if(moves != null) {
                        for(int[] move : moves) {
                            ChessBoard tempBoard = board.makeCopy();
                            if((tempBoard.getPiece(move[0], move[1]) == null || tempBoard.getPiece(move[0], move[1]).getColor() == turn) && tempPiece.isLegalMove(i,  j,  move[0],  move[1],  tempBoard) && tempPiece.coastClear(i,  j,  move[0],  move[1],  tempBoard)) {
                                tempBoard.setPiece(move[0],  move[1],  tempPiece);
                                tempBoard.setPiece(i,  j,  null);
                                if(tempPiece.getName() == PieceName.KING) {
                                    if(turn == Player.WHITE) {
                                        board.setBlackKing(move);
                                    }else {
                                        board.setWhiteKing(move);
                                    }
                                }
                                //now check to see if there's still check
                                boolean check = false;
                                if(turn == Player.BLACK) {
                                    check = blackCheck(tempBoard);
                                    if(tempPiece.getName() == PieceName.KING) {
                                        board.setBlackKingRow(i);
                                        board.setBlackKingCol(j);
                                    }
                                }
                                else{
                                    check = whiteCheck(tempBoard);
                                    if(tempPiece.getName() == PieceName.KING) {
                                        board.setWhiteKingRow(i);
                                        board.setWhiteKingCol(j);
                                    }
                                }
                                //if no longer in check, not checkmate
                                if(!check) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        //if get through entire double loop and never returned, checkmate
        return true;
    }

    private void changeTurns() {
        //Change turns
        if(turn == Player.WHITE) {
            turn = Player.BLACK;
        }
        else {
            turn = Player.WHITE;
        }
    }

    public Player getTurn(){
        return turn;
    }

    public int move(int startRow, int endRow, int startCol, int endCol){
        Piece piece = board.getPiece(startRow, startCol);

        boolean validMove = validMoveForPiece(piece, startRow, endRow, startCol, endCol);

        if(!validMove) {
            return -1;
        }else{
            return 0;
        }
    }

    public int completeTheMove(int startRow, int endRow, int startCol, int endCol){
        //store some important stuff
        Piece piece = board.getPiece(startRow, startCol);
        Piece oldPiece = board.getPiece(endRow, endCol);
        ChessBoard oldBoard = board.makeCopy();

        //see if castling is valid and then perform castle
        int status = checkForCastle(piece, startRow, endRow, startCol, endCol);

        if(status == -1) {
            return -1;
        }

        //if we didn't castle, then it's a normal move.
        if(status != 0) {
            //move current to new and remove current position
            board.setPiece(endRow, endCol, piece);
            piece.setMoved(true);
            board.setPiece(startRow, startCol, null);

            //if captured en passant, then enforce.
            if(piece.getName() == PieceName.PAWN && oldPiece != null && oldPiece.getName() == PieceName.GHOST) {
                enforceEnpassant(endCol);
            }
        }

        //update King's location so that we can check for Check
        if(piece.getName() == PieceName.KING) {
            if(turn == Player.WHITE) {
                board.setWhiteKingRow(endRow);
                board.setWhiteKingCol(endCol);
            }
            else {
                board.setBlackKingRow(endRow);
                board.setBlackKingCol(endCol);
            }
        }

        //if current color in check, return error and revert move
        if((turn == Player.WHITE && whiteCheck(board)) || (turn == Player.BLACK && blackCheck(board))) {

            //put board back to the one we saved earlier
            board = oldBoard.makeCopy();

            //if King was moved, change locations back.
            if(piece.getName() == PieceName.KING) {
                if(turn == Player.WHITE) {
                    board.setWhiteKingRow(startRow);
                    board.setWhiteKingCol(startCol);
                }
                else {
                    board.setBlackKingRow(startRow);
                    board.setBlackKingCol(startCol);
                }
            }
            return -1;
        }

        return 0;
    }

    public int newTurn(){
        removeEnpassant();

        boolean check;
        boolean checkmate = false;

        //change player
        changeTurns();

        //check for check and mate
        if(turn == Player.WHITE){
            check = whiteCheck(board);
        }else{
            check = blackCheck(board);
        }


        if(check){
            //check for mate
            checkmate = checkForMate();
        }

     if(checkmate){
            return 2;
        }else if(check){
            return 1;
        }else {
            return 0;
        }
    }

    public void startGame(ChessBoard board){
        this.board = board;

        this.board.setWhiteKingCol(4);
        this.board.setWhiteKingRow(7);
        this.board.setBlackKingCol(4);
        this.board.setBlackKingRow(0);

        turn = Player.WHITE;
    }

    public void main(String[] args){
//		//set up scanner
//		board = new ChessBoard();
//		//initialize chessboard - put all pieces in starting positions
//		board.initializeBoard();
//		WhiteKing = new int[2];
//		WhiteKing[0] = 7;
//		WhiteKing[1] = 4;
//		BlackKing = new int[2];
//		BlackKing[0] = 0;
//		BlackKing[1] = 4;
//
//		System.out.println(board.getBoard());
//
//		//play the game!
//		playGame(scanner);

        //game is over, so close up shop.
//		scanner.close();

    }

}
