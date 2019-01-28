package logic;

import Pieces.*;

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

class Board implements ActionListener, Serializable {
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

    Color blackCellColor;
    Color whiteCellColor;

    private HashMap<String, Integer> blackPiece;
    private HashMap<String, Integer> whitePiece;
    private Cell[][] map;
    private ArrayList<Cell> highlightedCells = new ArrayList();

    public King whiteKing;
    public King blackKing;
    private boolean checkFlag;
    private int turn;
    private Cell selectedCell;
    private ArrayList<Piece> whitePeaces_arrayList;
    private ArrayList<Piece> blackPeaces_arrayList;
    private boolean checkMateFlag;
    public GameEnds gameEnder;
    private Long saveTime;

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

        this.initialisePlayer();
        this.map = new Cell[9][9];
        this.initialiseBoard();
        this.initialiseTurnAndCheckLabels();

        this.turn = 0;
        this.checkFlag = false;
        this.checkMateFlag = false;
        this.boardFrame.add(this.player1Panel, "North");
        this.boardFrame.add(this.player2Panel, "South");
        this.boardFrame.add(this.boardPanel, "Center");
        this.boardFrame.add(this.timePanel, "East");
        this.setIcon();
        this.closeWindow();
    }

    public void initialiseBoard() {
        this.whiteCellColor = new Color(204, 153, 255);
        this.blackCellColor = new Color(102, 0, 102);

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
                this.map[i][k] = new Cell(i, k);
                if ((i % 2 == 0 || i * k % 2 != 1) && (i % 2 != 0 || k % 2 != 0)) {
                    if (i % 2 != 0 && i * k % 2 == 0 || i % 2 == 0 && k % 2 == 1) {
                        this.map[i][k].setBackground(this.blackCellColor);
                    }
                } else {
                    this.map[i][k].setBackground(this.whiteCellColor);
                }
                //each Cell is now in ActionListener
                this.map[i][k].addActionListener(this);
                this.boardPanel.add(this.map[i][k]);
            }
        }

        this.initialiseWhitePieces();
        this.initialiseBlackPieces();
        this.initialiseHashMAP();
        Check.setBoardObject(this);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.boardFrame.setLocation(dimension.width / 2 - this.boardFrame.getSize().width / 2, dimension.height / 2 - this.boardFrame.getSize().height / 2);
        this.boardFrame.getRootPane().setWindowDecorationStyle(0);
    }

    //fertig - init all peaces and put them in map and whitePeaces_arrayList
    public void initialiseWhitePieces() {
        this.whitePeaces_arrayList = new ArrayList();

        //create pawns
        for(int i = 1; i < 9; ++i) {
            Pawn pawn = new Pawn(7, i, 0);
            this.map[7][i].addPiece(pawn);
            this.whitePeaces_arrayList.add(pawn);
        }
        //create Pieces.Queen
        Queen queen = new Queen(8, 4, 0);
        this.map[8][4].addPiece(queen);
        this.whitePeaces_arrayList.add(queen);
        //create Pieces.King
        this.whiteKing = new King(8, 5, 0);
        this.whitePeaces_arrayList.add(this.whiteKing);
        this.map[8][5].addPiece(this.whiteKing);
        //create bishops
        Bishop bishop = new Bishop(8, 3, 0);
        this.map[8][3].addPiece(bishop);
        this.whitePeaces_arrayList.add(bishop);
        bishop = new Bishop(8, 6, 0);
        this.map[8][6].addPiece(bishop);
        this.whitePeaces_arrayList.add(bishop);
        //create Rooks
        Rook rook = new Rook(8, 1, 0);
        this.map[8][1].addPiece(rook);
        this.whitePeaces_arrayList.add(rook);
        rook = new Rook(8, 8, 0);
        this.map[8][8].addPiece(rook);
        this.whitePeaces_arrayList.add(rook);
        //create Knights
        Knight knight = new Knight(8, 2, 0);
        this.map[8][2].addPiece(knight);
        this.whitePeaces_arrayList.add(knight);
        knight = new Knight(8, 7, 0);
        this.map[8][7].addPiece(knight);
        this.whitePeaces_arrayList.add(knight);
    }

    //fertig
    public void initialiseBlackPieces() {
        this.blackPeaces_arrayList = new ArrayList();
        //create Pawns
        for(int i = 1; i < 9; ++i) {
            Pawn var2 = new Pawn(2, i, 1);
            this.map[2][i].addPiece(var2);
            this.blackPeaces_arrayList.add(var2);
        }
        //create Pieces.Queen
        Queen queen = new Queen(1, 4, 1);
        this.map[1][4].addPiece(queen);
        this.blackPeaces_arrayList.add(queen);
        //create Pieces.King
        this.blackKing = new King(1, 5, 1);
        this.map[1][5].addPiece(this.blackKing);
        this.blackPeaces_arrayList.add(this.blackKing);
        //create Bishops
        Bishop bishop = new Bishop(1, 3, 1);
        this.map[1][3].addPiece(bishop);
        this.blackPeaces_arrayList.add(bishop);
        bishop = new Bishop(1, 6, 1);
        this.map[1][6].addPiece(bishop);
        this.blackPeaces_arrayList.add(bishop);
        //create Kings
        Rook rook = new Rook(1, 1, 1);
        this.map[1][1].addPiece(rook);
        this.blackPeaces_arrayList.add(rook);
        rook = new Rook(1, 8, 1);
        this.map[1][8].addPiece(rook);
        this.blackPeaces_arrayList.add(rook);
        //create Knights
        Knight knight = new Knight(1, 2, 1);
        this.map[1][2].addPiece(knight);
        this.blackPeaces_arrayList.add(knight);
        knight = new Knight(1, 7, 1);
        this.map[1][7].addPiece(knight);
        this.blackPeaces_arrayList.add(knight);
    }

    public void initialiseHashMAP() {
        this.whitePiece = new HashMap();
        this.blackPiece = new HashMap();
        this.whitePiece.put("PAWN", 8);
        this.blackPiece.put("PAWN", 8);
        this.whitePiece.put("KING", 1);
        this.blackPiece.put("KING", 1);
        this.whitePiece.put("QUEEN", 1);
        this.blackPiece.put("QUEEN", 1);
        this.whitePiece.put("ROOK", 2);
        this.blackPiece.put("ROOK", 2);
        this.whitePiece.put("KNIGHT", 2);
        this.blackPiece.put("KNIGHT", 2);
        this.whitePiece.put("BISHOP", 2);
        this.blackPiece.put("BISHOP", 2);
    }

    //fertig
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

    //fertig
    public void initialisePlayer() {
        this.player1_JLabel = new JLabel();
        this.player1_JLabel.setFont(new Font("Serif", 0, 15));
        this.player2_JLabel = new JLabel();
        this.player2_JLabel.setFont(new Font("Serif", 0, 15));
        this.player1Panel.add(this.player1_JLabel);
        this.player2Panel.add(this.player2_JLabel);
    }

    public void initialiseTurnAndCheckLabels() {

        this.turnLabel = new JLabel(" Player 2's Turn ");
        this.turnLabel.setFont(new Font("Serif", 0, 15));
        this.checkLabel = new JLabel("         ");
        this.checkLabel.setFont(new Font("Serif", 0, 15));
        this.timePanel.add(this.turnLabel);
        this.timePanel.add(this.checkLabel);
    }

    //fertig
    public void boardReset() {
        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                this.map[i][j].removePiece();
            }
        }

        this.initialiseWhitePieces();
        this.initialiseBlackPieces();
        this.initialiseHashMAP();
        this.turn = 0;
    }
    //fertig
    void setIcon() {
        this.boardFrame.setResizable(false);

        try {
            this.boardFrame.setIconImage(ImageIO.read(new File("src/images/AntiChess_icon.png")));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

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


    public void setPlayerJLabels(String p1name, String p2name) {
        this.player1_JLabel.setText(p1name);
        this.player2_JLabel.setText(p2name);
        if (this.turn == 0) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }

        this.boardFrame.setVisible(true);

    }

    public int getTurn() {
        return this.turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
        if (turn == 0) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }

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

        //wenn figur ausgewählt ist oder die angeklickte Cell markiert ist
        if (piece != null || this.highlightedCells.contains(cell)) {
            //führe Zug aus
            if (this.highlightedCells.contains(cell)) {
                this.movePiece(cell);
                //markiere Felder
            } else if (piece != null && this.turn == cell.getColour()) {
                ArrayList validMovesList = piece.getValidMoves(this.map, cell.x, cell.y, false);
                this.highlightCells(validMovesList,piece.pieceId);
                this.selectedCell = cell;
            }

        }
    }
    //targetCell = the Cell the Pieces.Piece wants to move
    //fertig
    public void movePiece(Cell targetCell) {
        Piece piece = this.selectedCell.getPiece();
        piece.setXY(targetCell.x, targetCell.y);

        //If on the Target is an enemy Pieces.Piece
        if (!targetCell.isEmpty()) {
            int pieceId;
            //if Enemy is White
            //remove the hit Pieces.Piece from the whitePiece_arrayList and from hashtable
            if (targetCell.getColour() == 0) {
                pieceId = (Integer)this.whitePiece.get(targetCell.getPieceId());
                this.whitePiece.put(targetCell.getPieceId(), pieceId - 1);

                for(int i = 0; i < this.whitePeaces_arrayList.size(); ++i) {
                    if (targetCell.x == ((Piece)this.whitePeaces_arrayList.get(i)).getX() && targetCell.y == ((Piece)this.whitePeaces_arrayList.get(i)).getY()) {
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
        //remove hit Pieces.Piece and place new Pieces.Piece there
        targetCell.addPiece(piece);
        this.selectedCell.removePiece();

        //change turn
        this.turn = (this.turn + 1) % 2;
        if (this.turn == 0) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }

        //unhighlight all Cells
        if (!this.highlightedCells.isEmpty()) {
            LineBorder var6 = new LineBorder(Color.BLACK, 0);

            for(int i = 0; i < this.highlightedCells.size(); ++i) {
                Cell var5 = (Cell)this.highlightedCells.get(i);
                var5.setBorder(var6);
            }

            this.highlightedCells.clear();
        }
        this.selectedCell = null;

        //if a Pieces.Pawn reached other side of map (PROMOTE Scenario)
        if (!targetCell.isEmpty() && targetCell.getPieceId().equals("PAWN") && (targetCell.x == 1 || targetCell.x == 8)) {
            Pawn promotedPawn = (Pawn)targetCell.getPiece();
            promotedPawn.promotePawn(this.boardFrame, this.map);
            if (promotedPawn.getColour() == 0) {
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

            if (this.map[promotedPawn.getX()][promotedPawn.getY()].getColour() == 0) {
                this.whitePeaces_arrayList.add(this.map[promotedPawn.getX()][promotedPawn.getY()].getPiece());
            } else {
                this.blackPeaces_arrayList.add(this.map[promotedPawn.getX()][promotedPawn.getY()].getPiece());
            }
        }

        //check for check
        this.checkFlag = Check.check(this.map, this.turn);
        if (this.checkFlag) {
            this.checkLabel.setText("     Check!!    ");
        } else {
            this.checkLabel.setText("               ");
        }

        this.checkMateFlag = this.checkMate();
        if (this.checkFlag && this.checkMateFlag) {
            String winnerName;
            if (this.turn == 0) {
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
                tempCell = (Cell)this.highlightedCells.get(i);
                tempCell.setBorder(var3);
            }

            this.highlightedCells.clear();
        }

        for(int i = 0; i < validMovesList.size(); ++i) {
            tempCell = (Cell)validMovesList.get(i);
            tempCell.setBorder(var2);
            this.highlightedCells.add(tempCell);
        }

    }


    public boolean checkMate() {
        boolean checkmate = true;
        ArrayList arrayList;
        Piece piece;

        if (this.turn == 0) {
            //try to get for every piece all valid moves
            //if no move possible, it means there is no move to come out of the check = checkmate
            for(int i = 0; i < this.whitePeaces_arrayList.size(); ++i) {
                piece = (Piece)this.whitePeaces_arrayList.get(i);
                arrayList = piece.getValidMoves(this.map, piece.getX(), piece.getY(), false);
                if (!arrayList.isEmpty()) {
                    checkmate = false;
                    break;
                }
            }
        } else {
            for(int i = 0; i < this.blackPeaces_arrayList.size(); ++i) {
                piece = (Piece)this.blackPeaces_arrayList.get(i);
                arrayList = piece.getValidMoves(this.map, piece.getX(), piece.getY(), false);
                if (!arrayList.isEmpty()) {
                    checkmate = false;
                    break;
                }
            }
        }

        this.checkMateFlag = checkmate;
        return checkmate;
    }

    public void endTheGame(String var1) {
        this.gameEnder.endGame(var1);
    }

    //fertig
    public void closeWindow() {
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

    //This Method can be used to change the Color of the Cells ingame
    //removed
    void setCellColor(Color var1, Color var2) {
        this.whiteCellColor = var1;
        this.blackCellColor = var2;

        for(int i = 1; i < 9; ++i) {
            for(int j = 1; j < 9; ++j) {
                if (i % 2 != 0 && i * j % 2 == 1 || i % 2 == 0 && j % 2 == 0) {
                    this.map[i][j].setBackground(this.whiteCellColor);
                } else if (i % 2 != 0 && i * j % 2 == 0 || i % 2 == 0 && j % 2 == 1) {
                    this.map[i][j].setBackground(this.blackCellColor);
                }
            }
        }

    }
}