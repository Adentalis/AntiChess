package Pieces;

import helper.Colour;
import logic.Cell;
import helper.Check;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Icon;

public abstract class Piece implements Serializable {
    public Icon pieceIcon;
    public int x;
    public int y;
    public Colour colour;
    public String pieceId;
    public int alreadyMoved = 0; //0 = piece not moved yet
    public Piece pieceObj;
    public Piece tempPiece;
    private Cell tempCell;
    public ArrayList<Cell> validMoves = new ArrayList();
    public ArrayList<Cell> pseudoValidMoves = new ArrayList();

    public Icon getPieceIcon() {
        return this.pieceIcon;
    }

    public abstract ArrayList<Cell> getValidMoves(Cell[][] map, int x, int y, boolean var4);

    //fertig
    public Piece(int var1, int var2, Colour colour) {
        this.x = var1;
        this.y = var2;
        this.colour = colour;
        this.pieceObj = this;

    }
    //fertig
    public void setXY(int x, int y) {
        this.alreadyMoved = 1;
        this.x = x;
        this.y = y;
    }

    public Colour getColour() {
        return this.colour;
    }

    public String getPieceId() {
        return this.pieceId;
    }
    //just checks if x and y are in 1-8 range
    public boolean isValid(int x, int y) {
        return x >= 1 && x <= 8 && y >= 1 && y <= 8;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract void move(int var1, int var2);

    public ArrayList<Cell> checkFreeMoves(Cell[][] map, int x, int y) {

        for(int i = 0; i < this.pseudoValidMoves.size(); ++i) {
            this.tempCell = (Cell)this.pseudoValidMoves.get(i);
            map[x][y].pseudoRemovePiece();
            this.tempPiece = this.tempCell.getPiece();
            this.tempCell.pseudoAddPiece(this);
            this.x = this.tempCell.x;
            this.y = this.tempCell.y;
            if (!Check.check(map, this.colour)) {
                this.validMoves.add(this.tempCell);
            }

            this.tempCell.pseudoRemovePiece();
            this.tempCell.pseudoAddPiece(this.tempPiece);
            map[x][y].pseudoAddPiece(this);
            this.x = x;
            this.y = y;
        }

        return this.validMoves;
    }

    public String toString(){
        return pieceId+"  x:"+x+" y:"+y+ " colour: "+colour;
    }
}
