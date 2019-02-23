package Gui;

import Gui.PvPGui;

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
    private PvPGui pvPGui;

     MenuListener(PvPGui pvPGui) {
        this.pvPGui = pvPGui;
    }

    public void actionPerformed(ActionEvent var1) {
        JMenuItem var2 = (JMenuItem)var1.getSource();
        if (var2 == this.pvPGui.newGame) {
            this.beginNewGame();
        } else if (var2 == this.pvPGui.nextGame) {
            this.beginNextGame();
        } else if (var2 == this.pvPGui.saveGame) {
            this.saveGames();
        } else if (var2 == this.pvPGui.loadGame) {
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


        this.pvPGui.boardReset();
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

        this.pvPGui.boardReset();
        this.pvPGui.setPlayerJLabels(this.pvPGui.getPlayer2Name(), this.pvPGui.getPlayer1Name());
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
                var6.writeObject(this.pvPGui);
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
                PvPGui pvPGui = (PvPGui)var6.readObject();
                this.loadBoard(pvPGui);
                var6.close();
                var5.close();
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        }

    }

    private void loadBoard(PvPGui pvPGui) {
        //remove all peaces
        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.pvPGui.getSquare(i, j).removePiece();
            }
        }

        this.pvPGui.setPlayerJLabels(pvPGui.getPlayer1Name(), pvPGui.getPlayer2Name());

        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.pvPGui.getSquare(i, j).addPiece(pvPGui.getSquare(i, j).getPiece());
            }
        }
        //TODO
        //this.pvPGui.setTurn(pvPGui.getTurn());
        this.pvPGui.setHashMaps(pvPGui.getWhiteHashMap(), pvPGui.getBlackHashMap());

    }

}