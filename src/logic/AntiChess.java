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



    }
}

