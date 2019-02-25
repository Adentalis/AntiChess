package helper;

import Pieces.Piece;
import logic.Cell;
import logic.Move;

import java.util.ArrayList;

public  class MoveHelper {


    public static ArrayList<Piece> ownPieces = new ArrayList<>();

    //Not working
    public static ArrayList<Move> getMovesList(Cell[][] map, Colour c){
        ownPieces.clear();
        System.out.println(map.length);
        for(int i = 1 ; i <= 8 ; i++){
            for (int k = 1 ; k <=8 ; k++){
                if(map[i][k].piece != null) {
                    if (map[i][k].piece.colour == c) {
                        ownPieces.add(map[i][k].piece);
                    }
                }
            }
        }
        System.out.println(ownPieces.size());



        return null;
    }
}
