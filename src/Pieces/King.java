package Pieces;

import helper.Colour;
import logic.Cell;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class King extends Piece implements Serializable {

    //fertig
    public King(int x, int y, Colour colour) {
        super(x, y, colour);
        ImageIcon icon;
        if (colour == Colour.WHITE) {
            icon = new ImageIcon("src/images/White_King.png");
        } else {
            icon = new ImageIcon("src/images/Black_King.png");
        }

        this.pieceIcon = icon;
        this.pieceId = "KING";
    }

    public ArrayList<Cell> getValidMoves(Cell[][] map, int x, int y, boolean var4) {
        this.validMoves.clear();
        this.pseudoValidMoves.clear();
        int[] var5 = new int[]{1, -1, 0, 0, 1, -1, 1, -1};
        int[] var6 = new int[]{0, 0, 1, -1, 1, 1, -1, -1};
        int var7 = x;
        int var8 = y;

        for(int var9 = 0; var9 < 8; ++var9) {
            var7 += var5[var9];
            var8 += var6[var9];
            if (this.isValid(var7, var8) && map[var7][var8].isEmpty()) {
                this.pseudoValidMoves.add(map[var7][var8]);
            } else if (this.isValid(var7, var8) && map[var7][var8].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[var7][var8]);
            }

            var7 = x;
            var8 = y;
        }

        if (var4) {
            return this.pseudoValidMoves;
        } else {
            this.validMoves = this.checkFreeMoves(map, x, y);
            return this.validMoves;
        }
    }

    public void move(int var1, int var2) {
        this.x = this.x;
        this.y = this.y;
        this.alreadyMoved = 1;
    }
}
