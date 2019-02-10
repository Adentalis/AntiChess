package logic;


import Pieces.*;

import helper.Colour;
import helper.Initalizer;
import helper.MoveHelper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Gui implements ActionListener, Serializable {
    private JFrame boardFrame = new JFrame("AntiCHESS");
    private JPanel boardPanel;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel timePanel;
    private JLabel player1_JLabel;
    private JLabel player2_JLabel;
    private JLabel turnLabel;
    private JLabel checkLabel;
    private Button allMovesButton;

    private JMenuBar jMenuBar;
    private JMenu jMenu;
    JMenuItem newGame;
    JMenuItem nextGame;
    JMenuItem saveGame;
    JMenuItem loadGame;

    public Cell[][] map;

    public ArrayList<Piece> whitePeaces_arrayList;
    public ArrayList<Piece> blackPeaces_arrayList;


    Gui() {
        BorderLayout borderLayout = new BorderLayout();
        this.boardFrame.setLayout(borderLayout);
        this.boardFrame.setSize(800, 720);
        this.boardPanel = new JPanel();
        GridLayout gridLayout1 = new GridLayout(0, 9);
        this.boardPanel.setLayout(gridLayout1);
        this.player1Panel = new JPanel();
        FlowLayout flowLayout1 = new FlowLayout(1, 20, 5);
        this.player1Panel.setLayout(flowLayout1);
        this.player2Panel = new JPanel();
        FlowLayout flowLayout2 = new FlowLayout(1, 20, 5);
        this.player2Panel.setLayout(flowLayout2);
        this.timePanel = new JPanel();
        GridLayout gridLayout2 = new GridLayout(3, 0, 10, 0);
        this.timePanel.setLayout(gridLayout2);

        this.initialiseMenuBar();
        this.boardFrame.setJMenuBar(this.jMenuBar);

        Initalizer.initPiecesAndMap();
        this.map = Initalizer.map;
        this.whitePeaces_arrayList=Initalizer.whitePieces_arrayList;
        this.blackPeaces_arrayList=Initalizer.blackPieces_arrayList;

        this.initialisePlayerLabel();
        this.initialiseBoard();
        this.initialiseTurnAndCheckLabels();

        this.allMovesButton = new Button();
        this.allMovesButton.setLabel("alle Züge");
        this.allMovesButton.addActionListener(this);



        this.boardFrame.add(this.player1Panel, "North");
        this.boardFrame.add(this.player2Panel, "South");
        this.boardFrame.add(this.boardPanel, "Center");
        this.boardFrame.add(this.timePanel, "East");
        this.timePanel.add(this.allMovesButton, "Middle");
        this.setIcon();
        this.closeWindow();
    }

    public void initialiseMenuBar() {
        this.jMenu = new JMenu("File");
        this.newGame = new JMenuItem("New Game");
        this.nextGame = new JMenuItem("Next Game");
        this.saveGame = new JMenuItem("Save Game");
        this.loadGame = new JMenuItem("Load Game");
        /*
        MenuListener menuListener = new MenuListener(this);
        this.newGame.addActionListener(menuListener);
        this.nextGame.addActionListener(menuListener);
        this.saveGame.addActionListener(menuListener);
        this.loadGame.addActionListener(menuListener);
        */
        this.jMenu.add(this.newGame);
        this.jMenu.add(this.nextGame);
        this.jMenu.addSeparator();
        this.jMenu.add(this.saveGame);
        this.jMenu.addSeparator();
        this.jMenu.add(this.loadGame);

        this.jMenuBar = new JMenuBar();
        this.jMenuBar.add(this.jMenu);
    }

    public void initialisePlayerLabel() {
        this.player1_JLabel = new JLabel();
        this.player1_JLabel.setFont(new Font("Serif", 0, 15));
        this.player2_JLabel = new JLabel();
        this.player2_JLabel.setFont(new Font("Serif", 0, 15));
        this.player1Panel.add(this.player1_JLabel);
        this.player2Panel.add(this.player2_JLabel);
    }

    public void initialiseBoard() {

        //Description for Cells A-H & 1-8
        JLabel[] character = new JLabel[9];
        JLabel[] numbers = new JLabel[8];

        //create characters - first one is empty
        character[0] = new JLabel("  ", 0);
        this.boardPanel.add(character[0]);
        for(int i = 1; i < 9; ++i) {
            character[i] = new JLabel("" + (char)(97 + i - 1), 0);
            this.boardPanel.add(character[i]);
        }

        //creates numbers and fill Cells with Color
        for( int i = 1; i < 9; ++i) {
            int j = 0;
            numbers[j] = new JLabel("" + (9 - i), 0);
            this.boardPanel.add(numbers[j++]);

            for(int k = 1; k < 9; ++k) {
                //each Cell is now in ActionListener and rendered on screen
                this.map[i][k].addActionListener(this);
                this.boardPanel.add(this.map[i][k]);
            }
        }

        //Check.setBoardObject(this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.boardFrame.setLocation(dimension.width / 2 - this.boardFrame.getSize().width / 2, dimension.height / 2 - this.boardFrame.getSize().height / 2);
        this.boardFrame.getRootPane().setWindowDecorationStyle(0);
        //this lets the gui be visible
        this.boardFrame.setVisible(true);
    }

    public void initialiseTurnAndCheckLabels() {

        this.turnLabel = new JLabel(" Player 2's Turn ");
        this.turnLabel.setFont(new Font("Serif", 0, 15));
        this.checkLabel = new JLabel("         ");
        this.checkLabel.setFont(new Font("Serif", 0, 15));
        this.timePanel.add(this.turnLabel);
        this.timePanel.add(this.checkLabel);
    }



    //TODO
    public void boardReset() {
    }

    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(allMovesButton)){
            //MoveHelper.getMovesList(map, Colour.WHITE);

        }else {
            Cell cell = (Cell) actionEvent.getSource();
            Piece piece = cell.getPiece();
        }


    }

    //fertig
    private void setIcon() {
        this.boardFrame.setResizable(false);
        try {
            this.boardFrame.setIconImage(ImageIO.read(new File("src/images/AntiChess_icon.png")));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    //fertig
    private void closeWindow() {
        this.boardFrame.setDefaultCloseOperation(0);
        this.boardFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent var1) {
                int var2 = JOptionPane.showConfirmDialog((Component)null, "Are you sure you want to exit?");
                if (var2 == 0) {
                    System.exit(0);
                }

            }
        });
    }

    public void updateGui(Move m){
        Cell target = m.target;
        Piece pieceToMove = m.piece;

        int pieceX = pieceToMove.x;
        int pieceY = pieceToMove.y;

        Colour pieceColour = pieceToMove.colour;

        target.addPiece(pieceToMove);
        map[5][5] = target;

        if(pieceColour==Colour.WHITE){

        }
    }

}
