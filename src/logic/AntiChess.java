package logic;


import Pieces.Piece;
import Players.RandomBot;
import helper.Initalizer;

import java.util.ArrayList;


public class AntiChess {

    public static void main(String[] var0) {


        Board board = new Board();
        board.setPlayerJLabels("Daniel", "Bot12");
        board.setTurnLabel(board.getPlayer2Name());

        RandomBot bot = new RandomBot("BOT");

        ArrayList<Piece> whitePeaces_arrayList = board.whitePeaces_arrayList;
        for(Piece p : whitePeaces_arrayList){
            System.out.println(p.toString());
            ArrayList<Cell> possibleMoves= p.getValidMoves(board.map,p.x,p.y,false);
            for(Cell c :possibleMoves ){
                System.out.println(c.toString());
            }
        }

    }
}

