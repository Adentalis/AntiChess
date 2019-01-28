package Pieces;

import logic.Bishop;
import logic.Cell;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Pawn extends Piece implements Serializable, ActionListener {
    private Dialog promoterFrame;
    private Label headerLabel;
    private Label statusLabel;
    private Panel controlPanel;
    private String newPieceId = "QUEEN";
    private Choice newPiece;
    private Cell[][] squares;

    //fertig
    public Pawn(int x, int y, int colour) {
        super(x, y, colour);
        ImageIcon icon;
        if (colour == 0) {
            icon = new ImageIcon("src/images/White_Pawn.png");
        } else {
            icon = new ImageIcon("src/images/Black_Pawn.png");
        }

        this.pieceIcon = icon;
        this.pieceId = "PAWN";
    }
    //TODO - schlagen en pasoe
    public ArrayList<Cell> getValidMoves(Cell[][] map, int x, int y, boolean var4) {
        this.validMoves.clear();
        this.pseudoValidMoves.clear();

        //if not moved yet, 2 steps forward are possible
        if (this.alreadyMoved == 0) {
            if (this.colour == 0 && map[x - 2][y].isEmpty() && map[x - 1][y].isEmpty()) {
                this.pseudoValidMoves.add(map[x - 2][y]);
            }

            if (this.colour == 1 && map[x + 2][y].isEmpty() && map[x + 1][y].isEmpty()) {
                this.pseudoValidMoves.add(map[x + 2][y]);
            }
        }
        //White
        if (this.colour == 0) {
            //one step forward
            if (this.isValid(x - 1, y) && map[x - 1][y].isEmpty()) {
                this.pseudoValidMoves.add(map[x - 1][y]);
            }
            //check if one step forward right is a enemy piece to attack
            if (this.isValid(x - 1, y + 1) && !map[x - 1][y + 1].isEmpty() && map[x - 1][y + 1].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[x - 1][y + 1]);
            }
            //check the same for left one
            if (this.isValid(x - 1, y - 1) && !map[x - 1][y - 1].isEmpty() && map[x - 1][y - 1].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[x - 1][y - 1]);
            }
        } else {
            //forward
            if (this.isValid(x + 1, y) && map[x + 1][y].isEmpty()) {
                this.pseudoValidMoves.add(map[x + 1][y]);
            }
            //forward left
            if (this.isValid(x + 1, y - 1) && !map[x + 1][y - 1].isEmpty() && map[x + 1][y - 1].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[x + 1][y - 1]);
            }
            //forward right
            if (this.isValid(x + 1, y + 1) && !map[x + 1][y + 1].isEmpty() && map[x + 1][y + 1].getColour() != this.colour) {
                this.pseudoValidMoves.add(map[x + 1][y + 1]);
            }
        }

        if (var4) {
            return this.pseudoValidMoves;
        } else {
            this.validMoves = this.checkFreeMoves(map, x, y);
            return this.validMoves;
        }
    }

    public void move(int var1, int var2) {
    }

    public void promotePawn(JFrame var1, Cell[][] var2) {
        this.promoterFrame = new Dialog(var1);
        this.promoterFrame.setSize(400, 250);
        GridLayout var3 = new GridLayout(3, 1);
        this.promoterFrame.setLayout(var3);
        this.headerLabel = new Label();
        this.headerLabel.setAlignment(1);
        this.statusLabel = new Label();
        this.statusLabel.setAlignment(1);
        this.statusLabel.setSize(350, 100);
        this.controlPanel = new Panel();
        this.controlPanel.setLayout(new FlowLayout());
        this.promoterFrame.add(this.headerLabel);
        this.promoterFrame.add(this.controlPanel);
        this.promoterFrame.add(this.statusLabel);
        this.headerLabel.setText("Choose a piece you want your pawn to be promoted to : ");
        this.newPiece = new Choice();
        this.newPiece.add("KNIGHT");
        this.newPiece.add("BISHOP");
        this.newPiece.add("ROOK");
        this.newPiece.add("QUEEN");
        Button var4 = new Button("Select");
        var4.addActionListener(this);
        this.promoterFrame.add(this.newPiece);
        this.promoterFrame.add(var4);
        this.promoterFrame.setVisible(true);
        this.squares = var2;
    }

    public void actionPerformed(ActionEvent var1) {
        this.newPieceId = this.newPiece.getItem(this.newPiece.getSelectedIndex());
        this.statusLabel.setText("Pieces.Piece Selected: " + this.newPieceId);
        String var2;
        byte var3;
        Knight var4;
        Queen var5;
        Bishop var6;
        Rook var7;
        if (this.colour == 0) {
            var2 = this.newPieceId;
            var3 = -1;
            switch(var2.hashCode()) {
                case -2073501043:
                    if (var2.equals("KNIGHT")) {
                        var3 = 0;
                    }
                    break;
                case 2521305:
                    if (var2.equals("ROOK")) {
                        var3 = 3;
                    }
                    break;
                case 77405962:
                    if (var2.equals("QUEEN")) {
                        var3 = 1;
                    }
                    break;
                case 1959485373:
                    if (var2.equals("BISHOP")) {
                        var3 = 2;
                    }
            }

            switch(var3) {
                case 0:
                    var4 = new Knight(this.x, this.y, 0);
                    this.squares[this.x][this.y].addPiece(var4);
                    break;
                case 1:
                    var5 = new Queen(this.x, this.y, 0);
                    this.squares[this.x][this.y].addPiece(var5);
                    break;
                case 2:
                    var6 = new Bishop(this.x, this.y, 0);
                    this.squares[this.x][this.y].addPiece(var6);
                    break;
                case 3:
                    var7 = new Rook(this.x, this.y, 0);
                    this.squares[this.x][this.y].addPiece(var7);
            }
        } else {
            var2 = this.newPieceId;
            var3 = -1;
            switch(var2.hashCode()) {
                case -2073501043:
                    if (var2.equals("KNIGHT")) {
                        var3 = 0;
                    }
                    break;
                case 2521305:
                    if (var2.equals("ROOK")) {
                        var3 = 3;
                    }
                    break;
                case 77405962:
                    if (var2.equals("QUEEN")) {
                        var3 = 1;
                    }
                    break;
                case 1959485373:
                    if (var2.equals("BISHOP")) {
                        var3 = 2;
                    }
            }

            switch(var3) {
                case 0:
                    var4 = new Knight(this.x, this.y, 1);
                    this.squares[this.x][this.y].addPiece(var4);
                    break;
                case 1:
                    var5 = new Queen(this.x, this.y, 1);
                    this.squares[this.x][this.y].addPiece(var5);
                    break;
                case 2:
                    var6 = new Bishop(this.x, this.y, 1);
                    this.squares[this.x][this.y].addPiece(var6);
                    break;
                case 3:
                    var7 = new Rook(this.x, this.y, 1);
                    this.squares[this.x][this.y].addPiece(var7);
            }
        }

        this.promoterFrame.setVisible(false);
        this.promoterFrame.dispose();
    }
}