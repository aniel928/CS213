package com.example.anne.chess.model;

import java.util.List;
import java.util.Scanner;


public class Chess{

    private boolean gameOver = false;

    private Player turn = Player.WHITE;

    private boolean draw = false;

    private boolean promote = false;

    private ChessBoard board;

    private int[] WhiteKing, BlackKing;

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

    private boolean checkMoves(String[] moves) {

        //should have at most 3 parameters
        if(moves.length > 3) {
            return false;
        }

        //if there are three, it's either offering draw or a request to promote
        else if(moves.length == 3) {
            //offer to draw sets variable so on the next turn we know the user offered a draw.
            if(moves[2].equals("draw?")) {
                draw = true;
            }
            //request to promote flags a variable for when we're ready to promote. (Does not guarantee validity since we don't know piece yet)
            else if(moves[2].equals("N") || moves[2].equals("R") || moves[2].equals("B") || moves[2].equals("Q")){
                promote = true;
            }
            //otherwise, invalid input
            else {
                return false;
            }
        }
        //if there is only one parameter it should either be resign, or draw (in response to draw offer of other player)
        else if(moves.length == 1) {
            //if resign then announce winner
            if(moves[0].equals("resign")) {
                gameOver = true;
                if(turn == Player.WHITE) {
                    System.out.println("Black wins");
                }else {
                    System.out.println("White wins");
                }
                return true;
            }
            //if draw, make sure variable is set then announce draw.
            if(draw == true && moves[0].equals("draw")){
                gameOver = true;
                System.out.println("Draw");
                return true;
            }
            //otherwise bad format
            else {
                return false;
            }
        }
        //if it's not a case above then there should only be two parameters, the starting and ending positions
        else if(moves.length != 2) {
            return false;
        }

        //If we're here, then user didn't accept draw. Change draw back to false. (Unless of course this user just requested a draw)
        if(!(moves.length == 3 && moves[2].equals("draw?"))) {
            draw = false;
        }

        //Finally, validate that the first two parameters of input are only two characters long each (rank and file)
        if(moves[0].length() != 2 || moves[1].length() != 2) {
            return false;
        }

        //if we passed all of that, then input is good.
        return true;
    }

    private void illegalMove() {
        System.out.println("Illegal move, try again.");
    }

    private int[] getArrayVals(String move) {
        int row;
        try{
            //row 8 is at top of board, so we need to reverse the input row # to match the index
            row = board.ROWS-Integer.parseInt(String.valueOf(move.charAt(1)));
        }
        catch(Exception e){
            //if not a valid row number (1-8), return -1's so we flag as error.
            row = -1;
        }

        int col;
        //a is left-most and g-is right most, so assign indexes as in array.
        switch(move.charAt(0)) {
            case 'a':
                col = 0;
                break;
            case 'b':
                col = 1;
                break;
            case 'c':
                col = 2;
                break;
            case 'd':
                col = 3;
                break;
            case 'e':
                col = 4;
                break;
            case 'f':
                col = 5;
                break;
            case 'g':
                col = 6;
                break;
            case 'h':
                col = 7;
                break;
            default:
                //if not a valid col letter (a-h), return -1's so we flag as error.
                col = -1;
        }

        //return positions (or -1's for error)
        int[] array = {row, col};
        return array;

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

        //if promotion got flagged and this piece is not a pawn, illegal move
        if(promote && piece.getName() != PieceName.PAWN){
            promote = false;
            return false;
        }
        //if promotion got flagged and pawn is not moving to last row, illegal move.
        if(promote && ((piece.getColor() == Player.WHITE && endRow != 0) || (piece.getColor() == Player.BLACK && endRow != 7))) {
            promote = false;
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

    private Piece checkForPromotion(Piece piece, int row, String[] moves) {
        if(piece.getName() == PieceName.PAWN) {
            if((turn == Player.WHITE && row == 1) || (turn == Player.BLACK && row == 6)) {
                promote = true;
            }
        }
        //handle promotion
        if(promote) {
            String promoteTo = "";
            if(moves.length < 3) {
                promoteTo = "Q";
            }
            else {
                promoteTo = moves[2];
            }
            piece = promote(promoteTo);
            promote = false;
        }
        return piece;
    }

    private Piece promote(String strPiece) {
        Piece piece = null;
        switch(strPiece) {
            //Rook
            case "R":
                piece = new Rook(turn);
                break;
            //Knight
            case "N":
                piece = new Knight(turn);
                break;
            //Bishop
            case "B":
                piece = new Bishop(turn);
                break;
            //Queen
            case "Q":
                piece = new Queen(turn);
                break;
            //Shouldn't enter here, but just in case.
            default:
                piece = null;
        }
        return piece;
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
                        WhiteKing[1]--;
                    }else{
                        BlackKing[1]--;
                    }
                    if(turn == Player.WHITE && blackCheck(tempBoard)){
                        BlackKing[1]++;
                        return -1;
                    }
                    if(turn == Player.WHITE && whiteCheck(tempBoard)) {
                        WhiteKing[1]++;
                        return -1;
                    }
                }
                if(endCol - startCol == 2) {
                    //King Side Castle
                    ChessBoard tempBoard = board.makeCopy();
                    tempBoard.setPiece(startRow,  startCol + 1, piece);
                    if(turn == Player.WHITE){
                        WhiteKing[1]++;
                    }else{
                        BlackKing[1]++;
                    }
                    if(turn == Player.BLACK && blackCheck(tempBoard)){
                        BlackKing[1]--;
                        return -1;
                    }
                    if(turn == Player.WHITE && whiteCheck(tempBoard)) {
                        WhiteKing[1]--;
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
                    if(piece.isLegalMove(i, j, WhiteKing[0], WhiteKing[1], board) && piece.coastClear(i, j, WhiteKing[0], WhiteKing[1], board)) {
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
                    if(piece.isLegalMove(i, j, BlackKing[0], BlackKing[1], board) && piece.coastClear(i, j, BlackKing[0], BlackKing[1], board)) {
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
                                        BlackKing = move;
                                    }else {
                                        WhiteKing = move;
                                    }
                                }
                                //now check to see if there's still check
                                boolean check = false;
                                if(turn == Player.WHITE) {
                                    check = blackCheck(tempBoard);
                                    if(tempPiece.getName() == PieceName.KING) {
                                        BlackKing[0] = i;
                                        BlackKing[1] = j;
                                    }
                                }
                                else{
                                    check = whiteCheck(tempBoard);
                                    if(tempPiece.getName() == PieceName.KING) {
                                        WhiteKing[0] = i;
                                        WhiteKing[1] = j;
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
        return -1;
    }

    public int newTurn(){
        //change players
        //check for check and mate
        return -1;
    }


    private void playGame(Scanner scanner) {
        while(!gameOver) {

            //in case pawn double moved last time, remove en passant.
            removeEnpassant();
            if(turn == Player.WHITE) {
                System.out.print("White's move: ");
            }else {
                System.out.print("Black's move: ");
            }

            //Get the user input and split it out into portions
            String move = scanner.nextLine();
            String moves[] = move.split(" ");

            boolean validInput = checkMoves(moves);
            if(!validInput) {
                illegalMove();
                continue;
            }

            //check if someone resigned or agreed to draw
            if(gameOver) {
                continue;
            }

            //get positions of starting spot
            int[] pos = getArrayVals(moves[0]);
            int startRow = pos[0];
            int startCol = pos[1];

            //get position of ending spot
            pos = getArrayVals(moves[1]);
            int endRow = pos[0];
            int endCol = pos[1];

            //check to see if something went wrong when getting positions
            if(startRow == -1 || startCol == -1 || endRow == -1 || endCol == -1) {
                illegalMove();
                continue;
            }

            //Get the piece from the starting position and store it in a variable
            Piece piece = board.getPiece(startRow,  startCol);

            //make sure wherever this piece is trying to go, it's a legal move
            boolean validMove = validMoveForPiece(piece, startRow, endRow, startCol, endCol);
            if(!validMove) {
                illegalMove();
                continue;
            }

            //store piece we're eviciting/capturing to check en passant
            Piece oldPiece = board.getPiece(endRow, endCol);

            //Before we start moving pieces, take a copy of the board so we can revert easily.
            ChessBoard oldBoard = board.makeCopy();

            //check for promotion
            if(piece.getName() == PieceName.PAWN) {
                piece = checkForPromotion(piece, startRow, moves);
            }

            //if promotion wasn't valid, then it's illegal
            if(piece == null) {
                illegalMove();
                continue;
            }

            //see if castling is valid and then perform castle
            int status = checkForCastle(piece, startRow, endRow, startCol, endCol);

            if(status == -1) {
                illegalMove();
                continue;
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
                    WhiteKing[0] = endRow;
                    WhiteKing[1] = endCol;
                }
                else {
                    BlackKing[0] = endRow;
                    BlackKing[1] = endCol;
                }
            }

            //Check both Kings in Check/CheckMate
            boolean whiteCheck = whiteCheck(board);
            boolean blackCheck = blackCheck(board);

            //if current color in check, return error and revert move
            if((turn == Player.WHITE && whiteCheck) || (turn == Player.BLACK && blackCheck)) {
                illegalMove();

                //put board back to the one we saved earlier
                board = oldBoard.makeCopy();

                //if King was moved, change locations back.
                if(piece.getName() == PieceName.KING) {
                    if(turn == Player.WHITE) {
                        WhiteKing[0] = startRow;
                        WhiteKing[1] = startCol;
                    }
                    else {
                        BlackKing[0] = startRow;
                        BlackKing[1] = startCol;
                    }
                }
                continue;
            }
            //if other color in check, check for checkmate, then return either "Check" or "Checkmate"
            else if((turn == Player.WHITE && blackCheck) || (turn == Player.BLACK && whiteCheck)) {
                //check for checkmate
                boolean checkMate = checkForMate();
                if(checkMate) {
//                    System.out.println(board.getBoard());

                    if(turn == Player.WHITE) {
                        System.out.println("Checkmate\nWhite wins");
                    }else {
                        System.out.println("Checkmate\nBlack wins");
                    }
                    gameOver = true;
                }else {
//                    System.out.println(board.getBoard());
                    System.out.println("Check");
                }

            }
            //Stalemate here
            else if(checkForMate()) {
//                System.out.println(board.getBoard());
                System.out.println("Stalemate");
                System.out.println("Draw");
                gameOver = true;
            }
            else {
//                System.out.println(board.getBoard());
            }

            changeTurns();
        }
    }

    public void startGame(ChessBoard board){
        this.board = board;
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
