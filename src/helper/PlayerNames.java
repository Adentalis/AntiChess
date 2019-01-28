package helper;

import logic.Board;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PlayerNames extends JFrame implements ActionListener, Serializable, ChangeListener {
    private JPanel textFieldInputPanel = new JPanel();
    private JButton submitButton = new JButton("Submit");

    private int timeLimit = 5;
    private Board board;

    public PlayerNames(Board board) {
        this.board = board;
        this.submitButton.addActionListener(this);
        this.textFieldInputPanel.setLayout(new BoxLayout(this.textFieldInputPanel, 1));
        BorderLayout var2 = new BorderLayout();
        this.setLayout(var2);
        this.setSize(300, 200);
        this.add(this.textFieldInputPanel, "North");
        this.add(this.submitButton, "South");
        Dimension var3 = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(var3.width / 2 - this.getSize().width / 2, var3.height / 2 - this.getSize().height / 2);
        this.getRootPane().setWindowDecorationStyle(0);
        this.setVisible(true);
    }

    public void stateChanged(ChangeEvent var1) {
        JSlider var2 = (JSlider)var1.getSource();
        if (!var2.getValueIsAdjusting()) {
            this.timeLimit = var2.getValue();
        }

    }

    public void actionPerformed(ActionEvent var1) {
        if (var1.getSource() == this.submitButton) {
            String p1name ="Daniel";
            String p2name = "P2";

            this.board.setPlayerJLabels(p1name, p2name);
        } else {
            JSlider var4 = (JSlider)var1.getSource();
            if (!var4.getValueIsAdjusting()) {
                this.timeLimit = var4.getValue();
            }
        }

    }


}