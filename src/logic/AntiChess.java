package logic;


import Pieces.Piece;
import Players.RandomBot;
import helper.Colour;
import helper.Initalizer;

import java.util.ArrayList;
import java.util.Random;


public class AntiChess {

    public static void main(String[] var0) {


       // Board board = new Board();
        //board.setPlayerJLabels("Daniel", "Bot12");
        //board.setTurnLabel(board.getPlayer2Name());
        RandomBot rndBot = new RandomBot("Bot1", Colour.WHITE);
        RandomBot rndBot2 = new RandomBot("Bot2", Colour.BLACK);
        Game game = new Game(rndBot,rndBot2);
        Gui g = new Gui();





    }
}

