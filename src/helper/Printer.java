package helper;

import logic.Move;

import java.util.ArrayList;

public class Printer {


    public static void allMoves(ArrayList<Move> movesList){
        System.out.println("Moves to do this Turn: "+ movesList.size());
        int i = 1;
        for(Move m : movesList){
            System.out.println(i+++". Zug: "+ m.piece.pieceId+"["+m.piece.x+"|"+m.piece.y+"] nach ["+m.target.x+"|"+m.target.y+"]" );
        }
    }
}
