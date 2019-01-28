package Pieces;
import helper.Colour;
import logic.Cell;

import javax.swing.ImageIcon;
import java.util.ArrayList;

public class Rook extends Piece {

    //fertig
    public Rook(int x, int y, Colour colour) {
        super(x, y, colour);
        ImageIcon icon;
        if (colour == Colour.WHITE) {
            icon = new ImageIcon("src/images/White_Rook.png");
        } else {
            icon = new ImageIcon("src/images/Black_Rook.png");
        }

        this.pieceIcon = icon;
        this.pieceId = "ROOK";
    }

    public ArrayList<Cell> getValidMoves(Cell[][] map, int x, int y, boolean var4) {
        this.validMoves.clear();
        this.pseudoValidMoves.clear();
        int[] moveListX = new int[]{1, -1, 0, 0};
        int[] moveListY = new int[]{0, 0, 1, -1};
        int tempX = x;
        int tempY = y;

        for(int i = 0; i < 4; ++i) {
            tempX += moveListX[i];

            for(tempY += moveListY[i]; this.isValid(tempX, tempY) && map[tempX][tempY].isEmpty(); tempY += moveListY[i]) {
                this.pseudoValidMoves.add(map[tempX][tempY]);
                tempX += moveListX[i];
            }

            //if enemy Pieces.Piece it can be attacked as well
            if (this.isValid(tempX, tempY) && map[tempX][tempY].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[tempX][tempY]);
            }

            tempX = x;
            tempY = y;
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
    }
}