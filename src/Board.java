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
    private int chance;
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
        this.initialiseTimePanel();

        this.chance = 0;
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
                //each Cell ist now in ActionListener
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
        //create Queen
        Queen queen = new Queen(8, 4, 0);
        this.map[8][4].addPiece(queen);
        this.whitePeaces_arrayList.add(queen);
        //create King
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
        //create Queen
        Queen queen = new Queen(1, 4, 1);
        this.map[1][4].addPiece(queen);
        this.blackPeaces_arrayList.add(queen);
        //create King
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

    public void initialiseTimePanel() {

        this.turnLabel = new JLabel(" Player 2's Turn ");
        this.turnLabel.setFont(new Font("Serif", 0, 15));
        this.checkLabel = new JLabel("         ");
        this.checkLabel.setFont(new Font("Serif", 0, 15));
        this.timePanel.add(this.turnLabel);

        this.timePanel.add(this.checkLabel);
    }

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
        this.chance = 0;
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
        if (this.chance == 0) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }

        this.boardFrame.setVisible(true);

    }

    public int getChance() {
        return this.chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
        if (chance == 0) {
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


    public Cell getSquare(int var1, int var2) {
        return this.map[var1][var2];
    }


    public void actionPerformed(ActionEvent actionEvent) {
        Cell cell = (Cell)actionEvent.getSource();
        Piece piece = cell.getPiece();
        if (piece != null || this.highlightedCells.contains(cell)) {
            if (this.highlightedCells.contains(cell)) {
                this.movePiece(cell);
            } else if (piece != null && this.chance == cell.getColour()) {
                ArrayList var4 = piece.getValidMoves(this.map, cell.x, cell.y, false);
                this.highlightCells(var4);
                this.selectedCell = cell;
            }

        }
    }

    public void movePiece(Cell var1) {
        Piece var2 = this.selectedCell.getPiece();
        var2.setXY(var1.x, var1.y);
        int var4;
        if (!var1.isEmpty()) {
            int var3;
            if (var1.getColour() == 0) {
                var3 = (Integer)this.whitePiece.get(var1.getPieceId());
                this.whitePiece.put(var1.getPieceId(), var3 - 1);

                for(var4 = 0; var4 < this.whitePeaces_arrayList.size(); ++var4) {
                    if (var1.x == ((Piece)this.whitePeaces_arrayList.get(var4)).getX() && var1.y == ((Piece)this.whitePeaces_arrayList.get(var4)).getY()) {
                        this.whitePeaces_arrayList.remove(var4);
                        break;
                    }
                }
            } else {
                var3 = (Integer)this.blackPiece.get(var1.getPieceId());
                this.blackPiece.put(var1.getPieceId(), var3 - 1);

                for(var4 = 0; var4 < this.blackPeaces_arrayList.size(); ++var4) {
                    if (var1.x == ((Piece)this.blackPeaces_arrayList.get(var4)).getX() && var1.y == ((Piece)this.blackPeaces_arrayList.get(var4)).getY()) {
                        this.blackPeaces_arrayList.remove(var4);
                        break;
                    }
                }
            }
        }

        var1.addPiece(var2);


        this.chance = (this.chance + 1) % 2;
        if (this.chance == 0) {
            this.setTurnLabel(this.player2_JLabel.getText());
        } else {
            this.setTurnLabel(this.player1_JLabel.getText());
        }

        this.selectedCell.removePiece();
        if (!this.highlightedCells.isEmpty()) {
            LineBorder var6 = new LineBorder(Color.BLACK, 0);

            for(var4 = 0; var4 < this.highlightedCells.size(); ++var4) {
                Cell var5 = (Cell)this.highlightedCells.get(var4);
                var5.setBorder(var6);
            }

            this.highlightedCells.clear();
        }

        this.selectedCell = null;
        if (!var1.isEmpty() && var1.getPieceId().equals("PAWN") && (var1.x == 1 || var1.x == 8)) {
            Pawn var7 = (Pawn)var1.getPiece();
            var7.promotePawn(this.boardFrame, this.map);
            if (var7.getColour() == 0) {
                for(var4 = 0; var4 < this.whitePeaces_arrayList.size(); ++var4) {
                    if (var7.x == ((Piece)this.whitePeaces_arrayList.get(var4)).getX() && var7.y == ((Piece)this.whitePeaces_arrayList.get(var4)).getY()) {
                        this.whitePeaces_arrayList.remove(var4);
                        break;
                    }
                }
            } else {
                for(var4 = 0; var4 < this.blackPeaces_arrayList.size(); ++var4) {
                    if (var7.x == ((Piece)this.blackPeaces_arrayList.get(var4)).getX() && var7.y == ((Piece)this.blackPeaces_arrayList.get(var4)).getY()) {
                        this.blackPeaces_arrayList.remove(var4);
                        break;
                    }
                }
            }

            if (this.map[var7.getX()][var7.getY()].getColour() == 0) {
                this.whitePeaces_arrayList.add(this.map[var7.getX()][var7.getY()].getPiece());
            } else {
                this.blackPeaces_arrayList.add(this.map[var7.getX()][var7.getY()].getPiece());
            }
        }


        this.checkFlag = Check.check(this.map, this.chance);
        if (this.checkFlag) {
            this.checkLabel.setText("     Check!!    ");
        } else {
            this.checkLabel.setText("               ");
        }

        this.checkMateFlag = this.checkMate();
        if (this.checkFlag && this.checkMateFlag) {
            String var8;
            if (this.chance == 0) {
                var8 = "Black Wins";
            } else {
                var8 = "White Wins";
            }

            this.gameEnder = new GameEnds(this);
            this.gameEnder.endGame(var8);
        } else if (this.checkMateFlag) {
            this.gameEnder = new GameEnds(this);
            this.gameEnder.endGame("DRAW");
        }

    }

    private void highlightCells(ArrayList<Cell> var1) {
        LineBorder var2 = new LineBorder(Color.BLACK, 5);
        LineBorder var3 = new LineBorder(Color.BLACK, 0);
        int i;
        Cell var5;
        if (!this.highlightedCells.isEmpty()) {
            for(i = 0; i < this.highlightedCells.size(); ++i) {
                var5 = (Cell)this.highlightedCells.get(i);
                var5.setBorder(var3);
            }

            this.highlightedCells.clear();
        }

        for(i = 0; i < var1.size(); ++i) {
            var5 = (Cell)var1.get(i);
            var5.setBorder(var2);
            this.highlightedCells.add(var5);
        }

    }


    public boolean checkMate() {
        boolean var1 = true;
        int var2;
        ArrayList var3;
        Piece var4;
        if (this.chance == 0) {
            for(var2 = 0; var2 < this.whitePeaces_arrayList.size(); ++var2) {
                var4 = (Piece)this.whitePeaces_arrayList.get(var2);
                var3 = var4.getValidMoves(this.map, var4.getX(), var4.getY(), false);
                if (!var3.isEmpty()) {
                    var1 = false;
                    break;
                }
            }
        } else {
            for(var2 = 0; var2 < this.blackPeaces_arrayList.size(); ++var2) {
                var4 = (Piece)this.blackPeaces_arrayList.get(var2);
                var3 = var4.getValidMoves(this.map, var4.getX(), var4.getY(), false);
                if (!var3.isEmpty()) {
                    var1 = false;
                    break;
                }
            }
        }

        this.checkMateFlag = var1;
        return var1;
    }

    public void endTheGame(String var1) {
        this.gameEnder.endGame(var1);
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