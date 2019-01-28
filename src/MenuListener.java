import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

class MenuListener implements ActionListener, Serializable {
    private Board board;

     MenuListener(Board board) {
        this.board = board;
    }

    public void actionPerformed(ActionEvent var1) {
        JMenuItem var2 = (JMenuItem)var1.getSource();
        if (var2 == this.board.newGame) {
            this.beginNewGame();
        } else if (var2 == this.board.nextGame) {
            this.beginNextGame();
        } else if (var2 == this.board.saveGame) {
            this.saveGames();
        } else if (var2 == this.board.loadGame) {
            this.loadGames();
        }

    }

    private void beginNewGame() {
        int var1 = JOptionPane.showConfirmDialog((Component)null, "Do want to save current game?");
        if (var1 == 0) {
            if (!this.saveGames()) {
                return;
            }
        } else if (var1 == 2) {
            return;
        }


        this.board.boardReset();
    }

    private void beginNextGame() {
        int var1 = JOptionPane.showConfirmDialog((Component)null, "Do want to save current game?");
        if (var1 == 0) {
            if (!this.saveGames()) {
                return;
            }
        } else if (var1 == 2) {
            return;
        }

        this.board.boardReset();
        this.board.setPlayerJLabels(this.board.getPlayer2Name(), this.board.getPlayer1Name());
    }

    private boolean saveGames() {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Save As");
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Saved Games", "sav");
        jFileChooser.setFileFilter(fileNameExtensionFilter);
        int var3 = jFileChooser.showSaveDialog(null);
        if (var3 == 0) {
            File var4 = jFileChooser.getSelectedFile();

            try {
                FileOutputStream var5 = new FileOutputStream(var4.getAbsolutePath() + ".sav");
                ObjectOutputStream var6 = new ObjectOutputStream(var5);
                var6.writeObject(this.board);
                var6.close();
                var5.close();
            } catch (IOException var7) {
                var7.printStackTrace();
            }

            return true;
        } else {
            return false;
        }
    }

    private void loadGames() {
        JFileChooser var1 = new JFileChooser();
        var1.setDialogTitle("Open Saved Game");
        FileNameExtensionFilter var2 = new FileNameExtensionFilter("Saved Games", "sav");
        var1.setFileFilter(var2);
        int var3 = var1.showOpenDialog(null);
        if (var3 == 0) {
            File var4 = var1.getSelectedFile();

            try {
                FileInputStream var5 = new FileInputStream(var4.getAbsolutePath());
                ObjectInputStream var6 = new ObjectInputStream(var5);
                Board board = (Board)var6.readObject();
                this.loadBoard(board);
                var6.close();
                var5.close();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

    }

    private void loadBoard(Board board) {
        //remove all peaces
        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.board.getSquare(i, j).removePiece();
            }
        }

        this.board.setPlayerJLabels(board.getPlayer1Name(), board.getPlayer2Name());

        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.board.getSquare(i, j).addPiece(board.getSquare(i, j).getPiece());
            }
        }

        this.board.setTurn(board.getTurn());
        this.board.setHashMaps(board.getWhiteHashMap(), board.getBlackHashMap());

    }

}