package logic;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameEnds extends JFrame implements ActionListener, Serializable {
    private JPanel dialogPanel;
    private JPanel buttonsPanel;
    private JButton newGame;
    private JButton nextGame;
    private Board board;
    private String dialogString = "What do you want to do now?";
    private JLabel resultText;
    private JLabel dialogText;

    public GameEnds(Board board) {
        this.board = board;
        this.setSize(300, 200);
        this.dialogPanel = new JPanel();
        this.buttonsPanel = new JPanel();
        this.resultText = new JLabel();
        this.dialogText = new JLabel(this.dialogString);
        this.dialogPanel.add(this.resultText);
        this.dialogPanel.add(this.dialogText);
        this.dialogPanel.setLayout(new BoxLayout(this.dialogPanel, 1));
        this.buttonsPanel.setLayout(new FlowLayout(1));
        this.newGame = new JButton("New Game");
        this.nextGame = new JButton("Next Game");
        this.newGame.addActionListener(this);
        this.nextGame.addActionListener(this);
        this.buttonsPanel.add(this.newGame);
        this.buttonsPanel.add(this.nextGame);
        this.setLayout(new BorderLayout());
        this.add(this.dialogPanel, "Center");
        this.add(this.buttonsPanel, "South");
        this.setIcon();
        Dimension var2 = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(var2.width / 2 - this.getSize().width / 2, var2.height / 2 - this.getSize().height / 2);
        this.setVisible(true);
    }

    public void endGame(String var1) {
        this.resultText.setText(var1 + "!");
        this.board.setVisibleFalse();
    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.newGame) {
            new PlayerNames(this.board);
            this.board.boardReset();
        } else {
            this.board.boardReset();
            //geändert
            this.board.setPlayerJLabels(this.board.getPlayer2Name(), this.board.getPlayer1Name());
        }

        this.setVisible(false);
        this.board.setVisibleTrue();
    }

    void setIcon() {
        this.setResizable(false);

        try {
            this.setIconImage(ImageIO.read(new File("src/images/AntiChess_icon.png")));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }
}

