
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.Icon;

public abstract class Piece implements Serializable {
    public Icon pieceIcon;
    public int x;
    public int y;
    public int colour;
    public String pieceId;
    public int flag = 0;
    public Piece pieceObj;
    public Piece tempPiece;
    private Cell tempCell;
    public ArrayList<Cell> validMoves = new ArrayList();
    public ArrayList<Cell> pseudoValidMoves = new ArrayList();

    public Icon getPieceIcon() {
        return this.pieceIcon;
    }

    public abstract ArrayList<Cell> getValidMoves(Cell[][] var1, int var2, int var3, boolean var4);

    //fertig
    public Piece(int var1, int var2, int colour) {
        this.x = var1;
        this.y = var2;
        this.colour = colour;
        this.pieceObj = this;
    }
    //flag?
    public void setXY(int var1, int var2) {
        this.flag = 1;
        this.x = var1;
        this.y = var2;
    }

    public int getColour() {
        return this.colour;
    }

    public String getPieceId() {
        return this.pieceId;
    }

    public boolean isValid(int var1, int var2) {
        return var1 >= 1 && var1 <= 8 && var2 >= 1 && var2 <= 8;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public abstract void move(int var1, int var2);

    public ArrayList<Cell> checkFreeMoves(Cell[][] var1, int var2, int var3) {
        int var5 = this.pseudoValidMoves.size();

        for(int var4 = 0; var4 < var5; ++var4) {
            this.tempCell = (Cell)this.pseudoValidMoves.get(var4);
            var1[var2][var3].pseudoRemovePiece();
            this.tempPiece = this.tempCell.getPiece();
            this.tempCell.pseudoAddPiece(this);
            this.x = this.tempCell.x;
            this.y = this.tempCell.y;
            if (!Check.check(var1, this.colour)) {
                this.validMoves.add(this.tempCell);
            }

            this.tempCell.pseudoRemovePiece();
            this.tempCell.pseudoAddPiece(this.tempPiece);
            var1[var2][var3].pseudoAddPiece(this);
            this.x = var2;
            this.y = var3;
        }

        return this.validMoves;
    }
}
