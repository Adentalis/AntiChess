package logic;

import Pieces.Piece;

public class Move {

    public Cell target;
    public Piece piece;
    //will be used later for heuristik
    public int value;

    public Move(Cell targetCell , Piece pieceToMove, int value){
        this.target=targetCell;
        this.piece = pieceToMove;
        this.value = value;
    }

}
