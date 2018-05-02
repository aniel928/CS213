package com.example.anne.chess.model;

import com.example.anne.chess.R;

import java.util.List;
import java.util.Random;

public abstract class Piece {
    private int imageId = 0;
    private Player color;
    private PieceName name;
    private boolean moved;

    /*
        CONSTRUCTOR
     */
    public Piece(Player color, PieceName name){
        this.color = color;
        this.name = name;

        setImageId(findImage(color, name));
    }

    /*
        GETTERS / SETTERS
     */

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Player getColor() {
        return color;
    }

    public void setColor(Player color) {
        this.color = color;
    }

    public PieceName getName() {
        return name;
    }

    public void setName(PieceName name) {
        this.name = name;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }


    /*
        HELPER METHODS
     */

    public abstract List<int[]> allLegalMoves(int row, int col, ChessBoard board);

    public abstract boolean isLegalMove(int startRow, int startCol, int endRow, int endCol, ChessBoard board);

    public abstract boolean coastClear(int startRow, int startCol, int endRow, int endCol, ChessBoard board);

    private int findImage(Player color, PieceName piece) {
        int image = 0;
        switch (piece) {
            case KING:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_bk;
                } else {
                    image = R.drawable.ic_wk;
                }
                break;
            case QUEEN:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_bq;
                } else {
                    image = R.drawable.ic_wq;
                }
                break;
            case BISHOP:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_bb;
                } else {
                    image = R.drawable.ic_wb;
                }
                break;
            case KNIGHT:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_bn;
                } else {
                    image = R.drawable.ic_wn;
                }
                break;
            case ROOK:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_br;
                } else {
                    image = R.drawable.ic_wr;
                }
                break;
            case PAWN:
                if (color == Player.BLACK) {
                    image = R.drawable.ic_bp;
                } else {
                    image = R.drawable.ic_wp;
                }
                break;
            default:
                image = 0;
        }
        return image;
    }

}
