import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Rook extends Piece {

    //fertig
    public Rook(int x, int y, int colour) {
        super(x, y, colour);
        ImageIcon icon;
        if (colour == 0) {
            icon = new ImageIcon("src/images/White_Rook.png");
        } else {
            icon = new ImageIcon("src/images/Black_Rook.png");
        }

        this.pieceIcon = icon;
        this.pieceId = "ROOK";
    }

    public ArrayList<Cell> getValidMoves(Cell[][] var1, int var2, int var3, boolean var4) {
        this.validMoves.clear();
        this.pseudoValidMoves.clear();
        int[] var5 = new int[]{1, -1, 0, 0};
        int[] var6 = new int[]{0, 0, 1, -1};
        int var7 = var2;
        int var8 = var3;

        for(int var9 = 0; var9 < 4; ++var9) {
            var7 += var5[var9];

            for(var8 += var6[var9]; this.isValid(var7, var8) && var1[var7][var8].isEmpty(); var8 += var6[var9]) {
                this.pseudoValidMoves.add(var1[var7][var8]);
                var7 += var5[var9];
            }

            if (this.isValid(var7, var8) && var1[var7][var8].getColour() != this.colour) {
                this.pseudoValidMoves.add(var1[var7][var8]);
            }

            var7 = var2;
            var8 = var3;
        }

        if (var4) {
            return this.pseudoValidMoves;
        } else {
            this.validMoves = this.checkFreeMoves(var1, var2, var3);
            return this.validMoves;
        }
    }

    public void move(int var1, int var2) {
        this.x = this.x;
        this.y = this.y;
    }
}