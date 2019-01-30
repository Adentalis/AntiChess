package helper;

import Pieces.King;
import Pieces.Piece;
import logic.Board;
import logic.Cell;

import java.util.ArrayList;

public class Check {
    private static Board board;

    Check() {
    }

    public static boolean checkMate(Colour c, ArrayList<Piece> whiteList,ArrayList<Piece> blackList, Cell[][]map ) {
        boolean checkmate ;

        if (c == Colour.WHITE) {
            //try to get for every piece all valid moves
            //if no move possible, it means there is no move to come out of the check = checkmate
            checkmate = isCheckmate(whiteList, map, true);
        } else {
            checkmate = isCheckmate(blackList, map, true);
        }

        return checkmate;
    }

    private static boolean isCheckmate(ArrayList<Piece> whiteList, Cell[][] map, boolean checkmate) {
        Piece piece;
        ArrayList arrayList;
        for (Piece aWhiteList : whiteList) {
            piece = (Piece) aWhiteList;
            arrayList = piece.getValidMoves(map, piece.getX(), piece.getY(), false);
            if (!arrayList.isEmpty()) {
                checkmate = false;
                break;
            }
        }
        return checkmate;
    }

    public static boolean check(Cell[][] var0, Colour colour) {
        King king;
        if (colour == Colour.WHITE) {
            king = board.whiteKing;
        } else {
            king = board.blackKing;
        }

        int[] var3 = new int[]{1, -1, 0, 0, 1, -1, 1, -1};
        int[] var4 = new int[]{0, 0, 1, -1, 1, 1, -1, -1};

        boolean var7 = false;

        int var10;
        label101:
        for(int var8 = 0; var8 < 8; ++var8) {
            int var9 = king.x + var3[var8];

            for(var10 = king.y + var4[var8]; king.isValid(var9, var10) && !var7; var10 += var4[var8]) {
                if (!var0[var9][var10].isEmpty() && var0[var9][var10].getColour() != king.getColour()) {
                    Piece var11 = var0[var9][var10].getPiece();
                    String var12 = var11.getPieceId();
                    byte var13 = -1;
                    switch(var12.hashCode()) {
                        case 2448520:
                            if (var12.equals("PAWN")) {
                                var13 = 0;
                            }
                            break;
                        case 2521305:
                            if (var12.equals("ROOK")) {
                                var13 = 3;
                            }
                            break;
                        case 77405962:
                            if (var12.equals("QUEEN")) {
                                var13 = 1;
                            }
                            break;
                        case 1959485373:
                            if (var12.equals("BISHOP")) {
                                var13 = 2;
                            }
                    }

                    switch(var13) {
                        case 0:
                            if (var8 >= 4 && var8 < 8 && var9 == king.x + var3[var8] && var10 == king.y + var4[var8]) {
                                var7 = true;
                            }
                            continue label101;
                        case 1:
                            var7 = true;
                            continue label101;
                        case 2:
                            if (var8 >= 4 && var8 < 8) {
                                var7 = true;
                            }
                            continue label101;
                        case 3:
                            if (var8 >= 0 && var8 < 4) {
                                var7 = true;
                            }
                        default:
                            continue label101;
                    }
                }

                if (!var0[var9][var10].isEmpty() && var0[var9][var10].getColour() == king.getColour()) {
                    break;
                }

                var9 += var3[var8];
            }
        }

        if (!var7) {
            int[] var14 = new int[]{2, 2, -2, -2, -1, 1, 1, -1};
            int[] var15 = new int[]{-1, 1, 1, -1, 2, 2, -2, -2};

            for(int var17 = 0; var17 < 8; ++var17) {
                var10 = king.x + var14[var17];
                int var16 = king.y + var15[var17];
                if (king.isValid(var10, var16) && !var0[var10][var16].isEmpty() && var0[var10][var16].getColour() != king.getColour() && var0[var10][var16].getPiece().getPieceId().equals("KNIGHT")) {
                    var7 = true;
                    break;
                }
            }
        }

        return var7;
    }

    public static void setBoardObject(Board var0) {
        board = var0;
    }
}

