package Players;

import Pieces.Piece;
import helper.Colour;
import logic.Cell;
import logic.Move;

import java.util.ArrayList;

public abstract class Player {
    public String name;
    public Colour colour;
    public ArrayList<Move> movesList;
    public ArrayList<Piece> piecesList;
    public Cell[][] map;


    Player(String name){
        this.name = name;
    }

    public  void createMovesList(){

    }



}
