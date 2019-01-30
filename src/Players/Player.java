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




    public Player(String name){
        this.name = name;
        movesList= new ArrayList<>();
    }

    public  void createMovesList(){

    }

    public void updateAllMovesList(Cell[][]map , ArrayList<Piece> botPieces){
        movesList.clear();
        System.out.println("FÜR DIESEN ZU MÖGLICH!!");
        for(Piece p : botPieces){
            ArrayList<Cell> possibleTargetCells= p.getValidMoves(map,p.x,p.y,false);
            for(Cell c :possibleTargetCells ){
                movesList.add(new Move(c,p,0));
            }
        }
        int i = 1;
        System.out.println("dd"+ movesList.size());
        for(Move m : movesList){
            System.out.println(i+++". Zug: "+ m.piece.pieceId+"["+m.piece.x+"|"+m.piece.y+"] nach ["+m.target.x+"|"+m.target.y+"]" );
        }
    }



}
