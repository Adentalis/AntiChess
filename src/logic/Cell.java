package logic;

import Pieces.Piece;

import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JButton;

public class Cell extends JButton implements Serializable {
    public int x;
    public int y;
    private Piece piece;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addPiece(Piece piece) {
        this.piece = piece;
        if (piece != null) {
            this.setIcon(piece.getPieceIcon());
        }

    }

    public void removePiece() {
        if (this.piece != null) {
            this.piece = null;
            this.setIcon((Icon)null);
        }

    }

    public void pseudoAddPiece(Piece var1) {
        this.piece = var1;
    }

    public void pseudoRemovePiece() {
        if (this.piece != null) {
            this.piece = null;
        }

    }

    public Piece getPiece() {
        return this.piece;
    }

    public int getColour() {
        return this.piece != null ? this.piece.getColour() : -1;
    }

    public String getPieceId() {
        return this.piece.getPieceId();
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public String toString(){
        return "Cell x:"+this.x+"  y:"+this.y;
    }
}

