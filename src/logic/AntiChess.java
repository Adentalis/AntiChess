package logic;


import Gui.BasicGui;
import Gui.PvPGui;
import Players.RandomBot;
import helper.Colour;


public class AntiChess {

    public static void main(String[] var0) {

        if(true) {
            PvPGui board = new PvPGui();
            board.setPlayerJLabels("Daniel", "Bot12");
            board.setTurnLabel(board.getPlayer2Name());
        }else{
            RandomBot rndBot = new RandomBot("Bot1", Colour.WHITE);
            RandomBot rndBot2 = new RandomBot("Bot2", Colour.BLACK);
            Game game = new Game(rndBot,rndBot2);
            BasicGui g = new BasicGui();
        }






    }
}

