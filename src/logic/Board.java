package logic;

import Pieces.*;
import helper.Check;
import helper.Colour;
import helper.Initalizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class Board implements ActionListener, Serializable {
    private JFrame boardFrame = new JFrame("AntiCHESS");
    private JPanel boardPanel;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel timePanel;
    private JLabel player1_JLabel;
    private JLabel player2_JLabel;
    private JLabel turnLabel;
    private JLabel checkLabel;

    private JMenuBar jMenuBar;
    private JMenu jMenu;
    JMenuItem newGame;
    JMenuItem nextGame;
    JMenuItem saveGame;
    JMenuItem loadGame;

    public Cell[][] map;
    private HashMap<String, Integer> blackPiece;
    private HashMap<String, Integer> whitePiece;
    public ArrayList<Piece> whitePeaces_arrayList;
    public ArrayList<Piece> blackPeaces_arrayList;

    private ArrayList<Cell> highlightedCells = new ArrayList();

    public King whiteKing;
    public King blackKing;
    private Enum colorAtTurn;
    private Cell selectedCell;

    private boolean checkFlag;
    private boolean checkMateFlag;
    public GameEnds gameEnder;

    Board() {
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
        this.blackPiece=Initalizer.blackPiece;
        this.whitePiece=Initalizer.whitePiece;
        this.whitePeaces_arrayList=Initalizer.whitePieces_arrayList;
        this.blackPeaces_arrayList=Initalizer.blackPieces_arrayList;
        this.blackKing= (King)blackPeaces_arrayList.get(0);
        this.whiteKing= (King)whitePeaces_arrayList.get(0);

        this.initialisePlayerLabel();
        this.initialiseBoard();
        this.initialiseTurnAndCheckLabels();

        this.colorAtTurn = Colour.WHITE;
        this.checkFlag = false;
        this.checkMateFlag = false;
        this.boardFrame.add(this.player1Panel, "North");
        this.boardFrame.add(this.player2Panel, "South");
        this.boardFrame.add(this.boardPanel, "Center");
        this.boardFrame.add(this.timePanel, "East");
        this.setIcon();
        this.closeWindow();
    }

    public void initialiseMenuBar() {
        this.jMenu = new JMenu("File");
        this.newGame = new JMenuItem("New Game");
        this.nextGame = new JMenuItem("Next Game");
        this.saveGame = new JMenuItem("Save Game");
        this.loadGame = new JMenuItem("Load Game");
        MenuListener menuListener = new MenuListener(this);
        this.newGame.addActionListener(menuListener);
        this.nextGame.addActionListener(menuListener);
        this.saveGame.addActionListener(menuListener);
        this.loadGame.addActionListener(menuListener);
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

        Check.setBoardObject(this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.boardFrame.setLocation(dimension.width / 2 - this.boardFrame.getSize().width / 2, dimension.height / 2 - this.boardFrame.getSize().height / 2);
        this.boardFrame.getRootPane().setWindowDecorationStyle(0);
    }

    public void initialiseTurnAndCheckLabels() {

        this.turnLabel = new JLabel(" Player 2's Turn ");
        this.turnLabel.setFont(new Font("Serif", 0, 15));
        this.checkLabel = new JLabel("         ");
        this.checkLabel.setFont(new Font("Serif", 0, 15));
        this.timePanel.add(this.turnLabel);
        this.timePanel.add(this.checkLabel);
    }

    //targetCell = the Cell the Pieces.Piece wants to move
    //fertig
    public void movePiece(Move move) {
        /*
        -ändere von der Figut die zieht die x,y Werte
        -wenn Figur geschlagen wird entferne diese aus dem Spiel (Hashmap und ArrayList)
        -update die Zelle mit der neuen Figur die auf dieses Feld gezogen ist
        -unhighlight all Cells
        -check if PawnPromoted
        -check for check/checkMate
        -nächster Spieler am Zug
         */

        Cell targetCell = move.target;
        Piece piece = move.piece;

        piece.setXY(targetCell.x, targetCell.y);

        checkForEnemyPieceOnTargetCellAndRemoveFromGame(targetCell);

        targetCell.addPiece(piece);
        this.selectedCell.removePiece();

        unHighlightAllCells();

        checkForPomotedPawn(targetCell);

        //check for check
        //White did a move so check if black is check
        //Just check for checkmate if there is a check
        if(colorAtTurn == Colour.WHITE) {
            this.checkFlag = Check.check(this.map, Colour.BLACK);
            if(checkFlag)
                this.checkMateFlag = Check.checkMate(Colour.BLACK, whitePeaces_arrayList,blackPeaces_arrayList,map );
        }else {
            this.checkFlag = Check.check(this.map, Colour.WHITE);
            if(checkFlag)
                this.checkMateFlag = Check.checkMate(Colour.WHITE, whitePeaces_arrayList,blackPeaces_arrayList,map );
        }

        if (this.checkFlag) {
            this.checkLabel.setText("     Check!!    ");
        } else {
            this.checkLabel.setText("               ");
        }

        if (this.checkFlag && this.checkMateFlag) {
            String winnerName;
            if (this.colorAtTurn == Colour.BLACK) {
                winnerName = "Black Wins";
            } else {
                winnerName = "White Wins";
            }

            this.gameEnder = new GameEnds(this);
            this.gameEnder.endGame(winnerName);
        } else if (this.checkMateFlag) {
            this.gameEnder = new GameEnds(this);
            this.gameEnder.endGame("DRAW");
        }
        nextPlayersTurn();
    }

    private void checkForPomotedPawn(Cell targetCell) {
        //in this case there is rightnow a pawn on x =1/8 so it has to be promoted and be changed by a new Piece

        //if a Pieces.Pawn reached other side of map (PROMOTE Scenario)
        if (!targetCell.isEmpty() && targetCell.getPieceId().equals("PAWN") && (targetCell.x == 1 || targetCell.x == 8)) {
            Pawn promotedPawn = (Pawn)targetCell.getPiece();
            promotedPawn.promotePawn(this.boardFrame, this.map);

            if (promotedPawn.getColour() == Colour.WHITE) {
                //find the pawn in arrayList and removes it
                for(int i = 0; i < this.whitePeaces_arrayList.size(); ++i) {
                    if (promotedPawn.x == ((Piece)this.whitePeaces_arrayList.get(i)).getX() && promotedPawn.y == ((Piece)this.whitePeaces_arrayList.get(i)).getY()) {
                        this.whitePeaces_arrayList.remove(i);
                        break;
                    }
                }
            } else {
                for(int i = 0; i < this.blackPeaces_arrayList.size(); ++i) {
                    if (promotedPawn.x == ((Piece)this.blackPeaces_arrayList.get(i)).getX() && promotedPawn.y == ((Piece)this.blackPeaces_arrayList.get(i)).getY()) {
                        this.blackPeaces_arrayList.remove(i);
                        break;
                    }
                }
            }

            if (this.map[promotedPawn.getX()][promotedPawn.getY()].getColour() == Colour.WHITE) {
                this.whitePeaces_arrayList.add(this.map[promotedPawn.getX()][promotedPawn.getY()].getPiece());
            } else {
                this.blackPeaces_arrayList.add(this.map[promotedPawn.getX()][promotedPawn.getY()].getPiece());
            }
        }
    }

    private void checkForEnemyPieceOnTargetCellAndRemoveFromGame(Cell targetCell) {
        //If on the Target is an enemy Pieces.Piece
        if (!targetCell.isEmpty()) {
            int pieceId;
            //if Enemy is White remove the hit Pieces.Piece from the whitePiece_arrayList and from hashtable
            if (targetCell.getColour() == Colour.WHITE) {
                pieceId = (Integer)this.whitePiece.get(targetCell.getPieceId());
                //remove from Hashmap
                this.whitePiece.put(targetCell.getPieceId(), pieceId - 1);

                for(int i = 0; i < this.whitePeaces_arrayList.size(); ++i) {
                    if (targetCell.x == ((Piece)this.whitePeaces_arrayList.get(i)).getX() && targetCell.y == ((Piece)this.whitePeaces_arrayList.get(i)).getY()) {
                        //remove from ArrayList
                        this.whitePeaces_arrayList.remove(i);
                        break;
                    }
                }
                //Else Enemy is Black
            } else {
                pieceId = (Integer)this.blackPiece.get(targetCell.getPieceId());
                this.blackPiece.put(targetCell.getPieceId(), pieceId - 1);

                for(int i = 0; i < this.blackPeaces_arrayList.size(); ++i) {
                    if (targetCell.x == ((Piece)this.blackPeaces_arrayList.get(i)).getX() && targetCell.y == ((Piece)this.blackPeaces_arrayList.get(i)).getY()) {
                        this.blackPeaces_arrayList.remove(i);
                        break;
                    }
                }
            }
        }
    }

    private void nextPlayersTurn() {
        if(colorAtTurn == Colour.WHITE){
            colorAtTurn = Colour.BLACK;
            this.setTurnLabel(this.player1_JLabel.getText());
        }else{
            colorAtTurn = Colour.WHITE;
            this.setTurnLabel(this.player2_JLabel.getText());
        }
    }

    private void unHighlightAllCells() {
        if (!this.highlightedCells.isEmpty()) {
            LineBorder var6 = new LineBorder(Color.BLACK, 0);

            for(int i = 0; i < this.highlightedCells.size(); ++i) {
                Cell var5 = (Cell)this.highlightedCells.get(i);
                var5.setBorder(var6);
            }

            this.highlightedCells.clear();
        }
        this.selectedCell = null;
    }

    //fertig
    private void highlightCells(ArrayList<Cell> validMovesList, String pieceId) {
        //TEST start
        System.out.println(pieceId);
        for(Cell c : validMovesList){
            System.out.println(c.toString());
        }
        //TEST end

        LineBorder var2 = new LineBorder(Color.GREEN, 3);
        LineBorder var3 = new LineBorder(Color.BLACK, 0);

        Cell tempCell;
        if (!this.highlightedCells.isEmpty()) {
            for(int i = 0; i < this.highlightedCells.size(); ++i) {
                tempCell = this.highlightedCells.get(i);
                tempCell.setBorder(var3);
            }
            this.highlightedCells.clear();
        }

        for(int i = 0; i < validMovesList.size(); ++i) {
            tempCell = validMovesList.get(i);
            tempCell.setBorder(var2);
            this.highlightedCells.add(tempCell);
        }
    }

    //TODO
    public void boardReset() {
        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.map[i][j].removePiece();
            }
        }

        this.map = Initalizer.map;
        this.whitePeaces_arrayList=Initalizer.whitePieces_arrayList;
        this.blackPeaces_arrayList=Initalizer.blackPieces_arrayList;
        this.blackKing= (King)blackPeaces_arrayList.get(0);
        this.whiteKing= (King)whitePeaces_arrayList.get(0);

        //this.initialiseHashMAP();
        this.colorAtTurn = Colour.WHITE;
    }

    public void setVisibleFalse() {
        this.boardFrame.setVisible(false);
    }

    public void setVisibleTrue() {
        this.boardFrame.setVisible(true);
    }

    public String getPlayer1Name() {
        return this.player1_JLabel.getText();
    }

    public String getPlayer2Name() {
        return this.player2_JLabel.getText();
    }

    public void setTurnLabel(String name) {
        this.turnLabel.setText("    " + name + "'s turn   ");
    }
    //is used by load game in menulistener Todo
    public Enum getColorAtTurn() {
        return this.colorAtTurn;
    }

    public HashMap<String, Integer> getWhiteHashMap() {
        return this.whitePiece;
    }

    public HashMap<String, Integer> getBlackHashMap() {
        return this.blackPiece;
    }

    public void setHashMaps(HashMap<String, Integer> var1, HashMap<String, Integer> var2) {
        this.whitePiece = var1;
        this.blackPiece = var2;
    }

    public Cell getSquare(int x, int y) {
        return this.map[x][y];
    }

    public void actionPerformed(ActionEvent actionEvent) {
        Cell cell = (Cell)actionEvent.getSource();
        Piece piece = cell.getPiece();
        Colour test ;

        //wenn figur ausgewählt ist oder die angeklickte Cell markiert ist
        if (piece != null || this.highlightedCells.contains(cell)) {
            //führe Zug aus
            if (this.highlightedCells.contains(cell)) {
                this.movePiece(new Move(cell,  this.selectedCell.getPiece(),0));
                //markiere Felder
            } else if (piece != null && colorAtTurn == cell.getColour()) {
                ArrayList validMovesList = piece.getValidMoves(this.map, cell.x, cell.y, false);
                this.highlightCells(validMovesList,piece.pieceId);
                this.selectedCell = cell;
            }
        }
    }

    //this is just done once in Main. Can be removed and names send via Constructor
    public void setPlayerJLabels(String p1name, String p2name) {
        this.player1_JLabel.setText(p1name);
        this.player2_JLabel.setText(p2name);
        if (this.colorAtTurn == Colour.WHITE) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }
        this.boardFrame.setVisible(true);
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

}

