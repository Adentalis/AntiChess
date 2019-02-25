package helper;

import Pieces.*;
import logic.Cell;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
/*
This class init all necesarry things to start a game
 */
public class Initalizer {

    private static Cell[][] map;
    private static ArrayList<Piece> whitePieces_arrayList;
    private static ArrayList<Piece> blackPieces_arrayList;
    private static Color blackCellColor;
    private static Color whiteCellColor;
    private static HashMap<String, Integer> blackPiece;
    private static HashMap<String, Integer> whitePiece;


    public static void initPiecesAndMap(){
        initMap();
        initHashMap();
        initWhitePieces();
        initBlackPieces();

    }

    private static void initHashMap() {
        whitePiece = new HashMap();
        blackPiece = new HashMap();
        whitePiece.put("PAWN", 8);
        blackPiece.put("PAWN", 8);
        whitePiece.put("KING", 1);
        blackPiece.put("KING", 1);
        whitePiece.put("QUEEN", 1);
        blackPiece.put("QUEEN", 1);
        whitePiece.put("ROOK", 2);
        blackPiece.put("ROOK", 2);
        whitePiece.put("KNIGHT", 2);
        blackPiece.put("KNIGHT", 2);
        whitePiece.put("BISHOP", 2);
        blackPiece.put("BISHOP", 2);
    }

    private static void initMap() {
        whiteCellColor = new Color(204, 153, 255);
        blackCellColor = new Color(102, 0, 102);
        map= new Cell[9][9];

        for( int i = 1; i < 9; ++i) {

            for(int k = 1; k < 9; ++k) {
                map[i][k] = new Cell(i, k);
                if ((i % 2 == 0 || i * k % 2 != 1) && (i % 2 != 0 || k % 2 != 0)) {
                    if (i % 2 != 0 && i * k % 2 == 0 || i % 2 == 0 && k % 2 == 1) {
                        map[i][k].setBackground(blackCellColor);
                    }
                } else {
                    map[i][k].setBackground(whiteCellColor);
                }
            }
        }
    }

    private static void initWhitePieces(){
        whitePieces_arrayList = new ArrayList();

        //create Pieces.King
        King king = new King(8, 5, Colour.WHITE);
        whitePieces_arrayList.add(king);
        map[8][5].addPiece(king);

        //create pawns
        for(int i = 1; i < 9; ++i) {
            Pawn pawn = new Pawn(7, i, Colour.WHITE);
            map[7][i].addPiece(pawn);
            whitePieces_arrayList.add(pawn);
        }
        //create Pieces.Queen
        Queen queen = new Queen(8, 4, Colour.WHITE);
        map[8][4].addPiece(queen);
        whitePieces_arrayList.add(queen);

        //create bishops
        Bishop bishop = new Bishop(8, 3, Colour.WHITE);
        map[8][3].addPiece(bishop);
        whitePieces_arrayList.add(bishop);
        bishop = new Bishop(8, 6, Colour.WHITE);
        map[8][6].addPiece(bishop);
        whitePieces_arrayList.add(bishop);
        //create Rooks
        Rook rook = new Rook(8, 1, Colour.WHITE);
        map[8][1].addPiece(rook);
        whitePieces_arrayList.add(rook);
        rook = new Rook(8, 8, Colour.WHITE);
        map[8][8].addPiece(rook);
        whitePieces_arrayList.add(rook);
        //create Knights
        Knight knight = new Knight(8, 2, Colour.WHITE);
        map[8][2].addPiece(knight);
        whitePieces_arrayList.add(knight);
        knight = new Knight(8, 7, Colour.WHITE);
        map[8][7].addPiece(knight);
        whitePieces_arrayList.add(knight);

    }

    private static void initBlackPieces(){
        blackPieces_arrayList = new ArrayList();

        //create Pieces.King
        King king = new King(1, 5, Colour.BLACK);
        map[1][5].addPiece(king);
        blackPieces_arrayList.add(king);

        //create Pawns
        for(int i = 1; i < 9; ++i) {
            Pawn var2 = new Pawn(2, i, Colour.BLACK);
            map[2][i].addPiece(var2);
            blackPieces_arrayList.add(var2);
        }
        //create Pieces.Queen
        Queen queen = new Queen(1, 4, Colour.BLACK);
        map[1][4].addPiece(queen);
        blackPieces_arrayList.add(queen);
        //create Bishops
        Bishop bishop = new Bishop(1, 3, Colour.BLACK);
        map[1][3].addPiece(bishop);
        blackPieces_arrayList.add(bishop);
        bishop = new Bishop(1, 6, Colour.BLACK);
        map[1][6].addPiece(bishop);
        blackPieces_arrayList.add(bishop);
        //create Kings
        Rook rook = new Rook(1, 1, Colour.BLACK);
        map[1][1].addPiece(rook);
        blackPieces_arrayList.add(rook);
        rook = new Rook(1, 8, Colour.BLACK);
        map[1][8].addPiece(rook);
        blackPieces_arrayList.add(rook);
        //create Knights
        Knight knight = new Knight(1, 2, Colour.BLACK);
        map[1][2].addPiece(knight);
        blackPieces_arrayList.add(knight);
        knight = new Knight(1, 7, Colour.BLACK);
        map[1][7].addPiece(knight);
        blackPieces_arrayList.add(knight);
    }

    public void setCellColor(Color var1, Color var2) {
        this.whiteCellColor = var1;
        this.blackCellColor = var2;

        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                if (i % 2 != 0 && i * j % 2 == 1 || i % 2 == 0 && j % 2 == 0) {
                    this.map[i][j].setBackground(this.whiteCellColor);
                } else if (i % 2 != 0 && i * j % 2 == 0 || i % 2 == 0 && j % 2 == 1) {
                    this.map[i][j].setBackground(this.blackCellColor);
                }
            }
        }

    }

    public static Cell[][] getMap() {
        return map;
    }

    public static ArrayList<Piece> getWhitePieces_arrayList() {
        return whitePieces_arrayList;
    }

    public static ArrayList<Piece> getBlackPieces_arrayList() {
        return blackPieces_arrayList;
    }

    public static HashMap<String, Integer> getBlackPiece() {
        return blackPiece;
    }

    public static HashMap<String, Integer> getWhitePiece() {
        return whitePiece;
    }
}
