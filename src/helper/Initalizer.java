package helper;

import Pieces.*;
import logic.Cell;

import java.util.ArrayList;

public class Initalizer {

    public static  Cell[][] map;
    public static ArrayList<Piece> whitePieces_arrayList;
    public static  ArrayList<Piece> blackPieces_arrayList;

    public static void initPiecesAndMap(Cell[][]m){
        map = m;
        initWhitePieces();
        initBlackPieces();

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
}
