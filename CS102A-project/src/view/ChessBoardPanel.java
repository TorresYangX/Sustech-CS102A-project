package view;

import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;


import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel extends JPanel {
    private final int CHESS_COUNT = 8;

    public ChessGridComponent[][] getChessGrids() {
        return chessGrids;
    }/*$Luo */

    private ChessGridComponent[][] chessGrids;
    private int BlackNumber;
    private int WhiteNumber;


    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);

        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess

        repaint();
    }

    /**
     * set an empty chessboard
     */


    public void initialChessGrids() {

        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];
        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                chessGrids[i][j].symbol = (i + j) % 2;/*Luo*/
                this.add(chessGrids[i][j]);
            }
        }
    }


    /**
     * initial origin four chess
     */
    public void initialGame() {
        clearPieces();
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
        chessGrids[5][3].setChessPiece(ChessPiece.CYAN);
        chessGrids[4][2].setChessPiece(ChessPiece.CYAN);
        chessGrids[3][5].setChessPiece(ChessPiece.CYAN);
        chessGrids[2][4].setChessPiece(ChessPiece.CYAN);
        JLabel jl1 = new JLabel(String.valueOf(1));
        jl1.setLocation(20, 5);
        jl1.setSize(50,50);
        jl1.setFont(new Font("Calibri", Font.ITALIC, 50));
        JLabel jl2 = new JLabel(String.valueOf(1));
        jl2.setLocation(20, 5);
        jl2.setSize(50,50);
        jl2.setFont(new Font("Calibri", Font.ITALIC, 50));
        JLabel jl3 = new JLabel(String.valueOf(1));
        jl3.setLocation(20, 5);
        jl3.setSize(50,50);
        jl3.setFont(new Font("Calibri", Font.ITALIC, 50));
        JLabel jl4 = new JLabel(String.valueOf(1));
        jl4.setLocation(20, 5);
        jl4.setSize(50,50);
        jl4.setFont(new Font("Calibri", Font.ITALIC, 50));
        chessGrids[5][3].add(jl1);
        chessGrids[4][2].add(jl2);
        chessGrids[3][5].add(jl3);
        chessGrids[2][4].add(jl4);
        GameController.reArr();
    }

    public void clearPieces() {
        for (int i = 0; i < chessGrids.length; i++) {
            for (int j = 0; j < chessGrids[i].length; j++) {
                chessGrids[i][j].removeAll();
                chessGrids[i][j].setChessPiece(null);
                GameController.arr[i][j] = 0;
            }
        }
        GameController.reArr();
    }


    public void loadGameFromData(int[][] datas) {
        clearPieces();
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                switch (datas[i][j]) {
                    case 1:
                        chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                        break;
                    case -1:
                        chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                        break;
                }
            }
        }
        repaint();
//        GameController.reflection(GameFrame.controller.getGamePanel().getChessGrids());
    }

    public int countBlackScore() {
        BlackNumber = 0;
        for (int i = 0; i < chessGrids.length; i++) {
            for (int j = 0; j < chessGrids[i].length; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.BLACK) {
                    BlackNumber += 1;
                }
            }
        }
        return BlackNumber;
    }

    public int countWhiteScore() {
        WhiteNumber = 0;
        for (int i = 0; i < chessGrids.length; i++) {
            for (int j = 0; j < chessGrids[i].length; j++) {
                if (chessGrids[i][j].getChessPiece() == ChessPiece.WHITE) {
                    WhiteNumber += 1;
                }
            }
        }
        return WhiteNumber;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public int getBlackNumber() {
        return BlackNumber;
    }

    public int getWhiteNumber() {
        return WhiteNumber;
    }

    public boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
//        todo: complete this method
        int wry;

        if (currentPlayer == ChessPiece.BLACK) {
            wry = -1;
        } else {
            wry = 1;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                GameController.getJudgeArr()[i][j] = 0;
            }
        }

        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {

                if (GameController.getArr()[j][k] == wry) {

                    if (k < 6 && GameController.getArr()[j][k + 1] == 0 - wry) {/*right*/
                        int l = k + 1;
                        boolean tell = true;
                        while (l < 8 && GameController.getArr()[j][l] != 0) {
                            if (GameController.getArr()[j][l] == wry) {
                                tell = false;
                            }
                            l++;
                        }
                        if (tell && l < 8) {
                            GameController.getJudgeArr()[j][l] = 1;
                        }
                    }


                    if (k > 1 && GameController.getArr()[j][k - 1] == 0 - wry) {/*left*/
                        int l = k - 1;
                        boolean tell = true;
                        while (l >= 0 && GameController.getArr()[j][l] != 0) {
                            if (GameController.getArr()[j][l] == wry) {
                                tell = false;
                            }
                            l--;
                        }
                        if (tell && l >= 0) {
                            GameController.getJudgeArr()[j][l] = 1;
                        }
                    }


                    if (j > 1 && GameController.getArr()[j - 1][k] == 0 - wry) {/*up*/
                        int l = j - 1;
                        boolean tell = true;
                        while (l >= 0 && GameController.getArr()[l][k] != 0) {
                            if (GameController.getArr()[l][k] == wry) {
                                tell = false;
                            }
                            l--;
                        }
                        if (tell && l >= 0) {
                            GameController.getJudgeArr()[l][k] = 1;
                        }
                    }


                    if (j < 6 && GameController.getArr()[j + 1][k] == 0 - wry) {/*down*/
                        int l = j + 1;
                        boolean tell = true;
                        while (l < 8 && GameController.getArr()[l][k] != 0) {
                            if (GameController.getArr()[l][k] == wry) {
                                tell = false;
                            }
                            l++;
                        }
                        if (tell && l < 8) {
                            GameController.getJudgeArr()[l][k] = 1;
                        }
                    }


                    if (j < 6 && k < 6 && GameController.getArr()[j + 1][k + 1] == 0 - wry) {/*downRight*/
                        int l = j + 1;
                        int m = k + 1;
                        boolean tell = true;
                        while (l < 8 && m < 8 && GameController.getArr()[l][m] != 0) {
                            if (GameController.getArr()[l][m] == wry) {
                                tell = false;
                            }
                            l++;
                            m++;
                        }
                        if (tell && l < 8 && m < 8) {
                            GameController.getJudgeArr()[l][m] = 1;
                        }
                    }


                    if (j < 6 && k > 1 && GameController.getArr()[j + 1][k - 1] == 0 - wry) {/*downLeft*/
                        int l = j + 1;
                        int m = k - 1;
                        boolean tell = true;
                        while (l < 8 && m >= 0 && GameController.getArr()[l][m] != 0) {
                            if (GameController.getArr()[l][m] == wry) {
                                tell = false;
                            }
                            l++;
                            m--;
                        }
                        if (tell && l < 8 && m >= 0) {
                            GameController.getJudgeArr()[l][m] = 1;
                        }
                    }


                    if (j > 1 && k < 6 && GameController.getArr()[j - 1][k + 1] == 0 - wry) {/*upRight*/
                        int l = j - 1;
                        int m = k + 1;
                        boolean tell = true;
                        while (l >= 0 && m < 8 && GameController.getArr()[l][m] != 0) {
                            if (GameController.getArr()[l][m] == wry) {
                                tell = false;
                            }
                            l--;
                            m++;
                        }
                        if (tell && l >= 0 && m < 8) {
                            GameController.getJudgeArr()[l][m] = 1;
                        }
                    }


                    if (j > 1 && k > 1 && GameController.getArr()[j - 1][k - 1] == 0 - wry) {/*upLeft*/
                        int l = j - 1;
                        int m = k - 1;
                        boolean tell = true;
                        while (l >= 0 && m >= 0 && GameController.getArr()[l][m] != 0) {
                            if (GameController.getArr()[l][m] == wry) {
                                tell = false;
                            }
                            l--;
                            m--;
                        }
                        if (tell && l >= 0 && m >= 0) {
                            GameController.getJudgeArr()[l][m] = 1;
                        }
                    }


                }
            }
        }
        int sum = 0;
        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {
                sum += GameController.getJudgeArr()[j][k];
            }
        }

        if (GameController.getJudgeArr()[row][col] == 1) {
            return true;
        } else {
            return false;
        }

    }


}
