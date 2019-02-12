package logic;

import Pieces.Piece;
import Players.Player;
import Players.RandomBot;

import java.util.ArrayList;

public class Game {
    Player whitePlayer;
    Player blackPlayer;

    public Cell[][] map;
    public ArrayList<Piece> whitePieces_arrayList;
    public ArrayList<Piece> blackPieces_arrayList;

    boolean checkMate;

    public Game(Player rndBot, Player rndBot2) {
        this.whitePlayer = rndBot;
        this.blackPlayer = rndBot2;
    }

    public void startGame(){

    }


    public void setPlayers(Player w, Player b){
        this.whitePlayer=w;
        this.blackPlayer=b;

    }

}
