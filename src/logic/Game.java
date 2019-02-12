package logic;

import Pieces.Piece;
import Players.Player;

import java.util.ArrayList;

public class Game {
    Player whitePlayer;
    Player blackPlayer;

    public Cell[][] map;
    public ArrayList<Piece> whitePieces_arrayList;
    public ArrayList<Piece> blackPieces_arrayList;

    boolean checkMate;

    public void startGame(){

    }


    public void setPlayers(Player w, Player b){
        this.whitePlayer=w;
        this.blackPlayer=b;

    }

}
