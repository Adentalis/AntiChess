package Pieces;

import helper.Colour;
import logic.Cell;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bishop extends Piece {

    //fertig
    public Bishop(int x, int y, Colour colour) {
        super(x, y, colour);
        ImageIcon icon;
        if (colour == Colour.WHITE) {
            icon = new ImageIcon("src/images/White_Bishop.png");
        } else {
            icon = new ImageIcon("src/images/Black_Bishop.png");
        }

        this.pieceIcon = icon;
        this.pieceId = "BISHOP";
    }

    public ArrayList<Cell> getValidMoves(Cell[][] map, int x, int y, boolean var4) {
        this.validMoves.clear();
        this.pseudoValidMoves.clear();
        int[] validMovesX = new int[]{1, -1, 1, -1};
        int[] validMovesY = new int[]{1, 1, -1, -1};
        int tempX = x;
        int tempY = y;

        for(int i = 0; i < 4; ++i) {
            tempX += validMovesX[i];

            for(tempY += validMovesY[i]; this.isValid(tempX, tempY) && map[tempX][tempY].isEmpty(); tempY += validMovesY[i]) {
                this.pseudoValidMoves.add(map[tempX][tempY]);
                tempX += validMovesX[i];
            }

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