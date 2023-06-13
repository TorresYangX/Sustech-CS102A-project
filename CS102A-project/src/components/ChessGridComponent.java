package components;

import controller.GameController;
import model.*;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;

import static controller.GameController.getLastArr;

public class ChessGridComponent extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor01 = new Color(255, 150, 50);
    public static Color gridColor02 = new Color(10, 200, 150);/*Luo %^%*/
    public static Color gridColor = gridColor01;
    public static int colorCount = 1;
    public static int colorCountPlus = 1;
    public int symbol;


    private ChessPiece chessPiece;
    private int row;
    private int col;


    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;
    }

    @Override
    public void onMouseClicked() {
        //todo: complete mouse click method
        if (!(GameFrame.isCheatMode)) {
            if (!GameFrame.controller.canClick(row, col) || this.chessPiece == ChessPiece.BLACK || this.chessPiece == ChessPiece.WHITE) {
                System.out.println("Invalid Move");
                JOptionPane.showMessageDialog(null,"Invalid Move");
            }
            if (GameFrame.controller.canClick(row, col)) {
                if (this.chessPiece == null || this.chessPiece == ChessPiece.CYAN) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].removeAll();
                        }
                    }

                    System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
                    String e = GameFrame.controller.getCurrentPlayer() + " " + "clicked" + " " + "(" + row + "," + " " + col + ")";
                    GameController.EveryStep.add(e);
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();


                    int wry;
                    if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
                        wry = -1;
                    } else {
                        wry = 1;
                    }

                    boolean all = false;
                    int[] mov = new int[8];
                    for (int j = 0; j < 8; j++) {
                        mov[j] = 1;
                    }

                    int[][] postArr = new int[8][8];

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            postArr[i][j] = GameController.getArr()[i][j];
                        }
                    }

                    /*left*/
                    while ((this.row > mov[0]) && (GameController.getArr()[this.row - mov[0]][this.col] == -1 * wry)) {
                        mov[0]++;
                    }
                    if ((this.row - mov[0] >= 0) && (GameController.getArr()[this.row - mov[0]][this.col] == wry) && (GameController.getArr()[this.row - 1][this.col] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[0]; j++) {
                            postArr[this.row - j][this.col] = wry;
                        }
                    }


                    /*right*/
                    while ((this.row + mov[1] < 7) && (GameController.getArr()[this.row + mov[1]][this.col] == -1 * wry)) {
                        mov[1]++;
                    }
                    if ((this.row + mov[1] <= 7) && GameController.getArr()[this.row + mov[1]][this.col] == wry && (GameController.getArr()[this.row + 1][this.col] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[1]; j++) {
                            postArr[this.row + j][this.col] = wry;
                        }
                    }


                    /*up*/
                    while ((this.col > mov[2]) && (GameController.getArr()[this.row][this.col - mov[2]] == -1 * wry)) {
                        mov[2]++;
                    }
                    if ((this.col - mov[2] >= 0) && GameController.getArr()[this.row][this.col - mov[2]] == wry && (GameController.getArr()[this.row][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[2]; j++) {
                            postArr[this.row][this.col - j] = wry;
                        }
                    }

                    /*down*/
                    while ((this.col + mov[3] < 7) && (GameController.getArr()[this.row][this.col + mov[3]] == -1 * wry)) {
                        mov[3]++;
                    }
                    if ((this.col + mov[3] <= 7) && GameController.getArr()[this.row][this.col + mov[3]] == wry && (GameController.getArr()[this.row][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[3]; j++) {
                            postArr[this.row][this.col + j] = wry;
                        }
                    }

                    /*leftup*/
                    while ((this.row > mov[4]) && (this.col > mov[4]) && (GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == -1 * wry)) {
                        mov[4]++;
                    }
                    if ((this.row - mov[4] >= 0) && (this.col - mov[4] >= 0) && GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == wry && (GameController.getArr()[this.row - 1][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[4]; j++) {
                            postArr[this.row - j][this.col - j] = wry;
                        }
                    }

                    /*leftdown*/
                    while ((this.row > mov[5]) && (this.col + mov[5] < 7) && (GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == -1 * wry)) {
                        mov[5]++;
                    }
                    if ((this.row - mov[5] >= 0) && (this.col + mov[5] <= 7) && GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == wry && (GameController.getArr()[this.row - 1][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[5]; j++) {
                            postArr[this.row - j][this.col + j] = wry;
                        }
                    }

                    /*rightup*/
                    while ((this.row + mov[6] < 7) && (this.col > mov[6]) && (GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == -1 * wry)) {
                        mov[6]++;
                    }
                    if ((this.row + mov[6] <= 7) && (this.col - mov[6] >= 0) && GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == wry && (GameController.getArr()[this.row + 1][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[6]; j++) {
                            postArr[this.row + j][this.col - j] = wry;
                        }
                    }

                    /*rightdown*/
                    while ((this.row + mov[7] < 7) && (this.col + mov[7] < 7) && (GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == -1 * wry)) {
                        mov[7]++;
                    }
                    if ((this.row + mov[7] <= 7) && (this.col + mov[7] <= 7) && GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == wry && (GameController.getArr()[this.row + 1][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[7]; j++) {
                            postArr[this.row + j][this.col + j] = wry;
                        }
                    }


                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (postArr[i][j] == -1) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.BLACK);
                            }
                            if (postArr[i][j] == 1) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.WHITE);
                            }
                            if (postArr[i][j] == 0) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(null);
                            }
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].repaint();
                        }
                    }
                    repaint();
                    GameFrame.controller.swapPlayer();
                    GameController.reflection(GameFrame.controller.getGamePanel().getChessGrids());

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            getLastArr()[i][j] = GameController.getCopyArr()[i][j];
                        }
                    }

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            GameController.getCopyArr()[i][j] = postArr[i][j];
                        }
                    }


                    for (int w = 0; w < 2; w++) {

                        if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
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
                                        int count = 0;
                                        while (l < 8 && GameController.getArr()[j][l] != 0) {
                                            if (GameController.getArr()[j][l] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            count++;
                                        }
                                        if (tell && l < 8) {
                                            GameController.getJudgeArr()[j][l] += count;
                                        }
                                    }


                                    if (k > 1 && GameController.getArr()[j][k - 1] == 0 - wry) {/*left*/
                                        int l = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && GameController.getArr()[j][l] != 0) {
                                            if (GameController.getArr()[j][l] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            count++;
                                        }
                                        if (tell && l >= 0) {
                                            GameController.getJudgeArr()[j][l] += count;
                                        }
                                    }


                                    if (j > 1 && GameController.getArr()[j - 1][k] == 0 - wry) {/*up*/
                                        int l = j - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && GameController.getArr()[l][k] != 0) {
                                            if (GameController.getArr()[l][k] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            count++;
                                        }
                                        if (tell && l >= 0) {
                                            GameController.getJudgeArr()[l][k] += count;
                                        }
                                    }


                                    if (j < 6 && GameController.getArr()[j + 1][k] == 0 - wry) {/*down*/
                                        int l = j + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && GameController.getArr()[l][k] != 0) {
                                            if (GameController.getArr()[l][k] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            count++;
                                        }
                                        if (tell && l < 8) {
                                            GameController.getJudgeArr()[l][k] += count;
                                        }
                                    }


                                    if (j < 6 && k < 6 && GameController.getArr()[j + 1][k + 1] == 0 - wry) {/*downRight*/
                                        int l = j + 1;
                                        int m = k + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && m < 8 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            m++;
                                            count++;
                                        }
                                        if (tell && l < 8 && m < 8) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j < 6 && k > 1 && GameController.getArr()[j + 1][k - 1] == 0 - wry) {/*downLeft*/
                                        int l = j + 1;
                                        int m = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            m--;
                                            count++;
                                        }
                                        if (tell && l < 8 && m >= 0) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j > 1 && k < 6 && GameController.getArr()[j - 1][k + 1] == 0 - wry) {/*upRight*/
                                        int l = j - 1;
                                        int m = k + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && m < 8 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            m++;
                                            count++;
                                        }
                                        if (tell && l >= 0 && m < 8) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j > 1 && k > 1 && GameController.getArr()[j - 1][k - 1] == 0 - wry) {/*upLeft*/
                                        int l = j - 1;
                                        int m = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            m--;
                                            count++;
                                        }
                                        if (tell && l >= 0 && m >= 0) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                }
                            }
                        }
                        for (int i = 0; i < 8; i++) {
                            for (int l = 0; l < 8; l++) {
                                if (GameController.getJudgeArr()[i][l] > 0) {
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].setChessPiece(ChessPiece.CYAN);
                                    JLabel jl = new JLabel(String.valueOf(GameController.getJudgeArr()[i][l]));
                                    jl.setLocation(20, 5);
                                    jl.setSize(50, 50);
                                    jl.setFont(new Font("Calibri", Font.ITALIC, 50));
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].add(jl);
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].repaint();
                                }
                            }
                        }
                        int sum = 0;
                        for (int j = 0; j < 8; j++) {
                            for (int k = 0; k < 8; k++) {
                                sum += GameController.getJudgeArr()[j][k];
                            }
                        }

                        if (sum != 0) {
                            break;
                        } else if (sum == 0 && w == 0) {
                            GameFrame.controller.swapPlayer();
                            GameController.getStatusPanel().setPlayerText(GameFrame.controller.getCurrentPlayer().name());
                        } else if (sum == 0 && w != 0) {
                            GameController.getStatusPanel().isOver = true;
                            break;
                        }

                    }

                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() > GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("BLACK win!");
                        JOptionPane.showMessageDialog(null, "BLACK win!");
                    }
                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() < GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("WHITE win!");
                        JOptionPane.showMessageDialog(null, "WHITE win!");
                    }
                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() == GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("Draw!");
                        JOptionPane.showMessageDialog(null, "Draw!");
                    }

                }

            }

        }
        if (GameFrame.isCheatMode && GameFrame.controller.getCurrentPlayer() != GameFrame.cheatModeChoose) {
            if (!GameFrame.controller.canClick(row, col) || this.chessPiece == ChessPiece.BLACK || this.chessPiece == ChessPiece.WHITE) {
                System.out.println("Invalid Move");
                JOptionPane.showMessageDialog(null,"Invalid Move");
            }
            if (GameFrame.controller.canClick(row, col)) {
                if (this.chessPiece == null || this.chessPiece == ChessPiece.CYAN) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].removeAll();
                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].removeAll();
                        }
                    }

                    System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
                    String e = GameFrame.controller.getCurrentPlayer() + " " + "clicked" + " " + "(" + row + "," + " " + col + ")";
                    GameController.EveryStep.add(e);


                    int wry;
                    if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
                        wry = -1;
                    } else {
                        wry = 1;
                    }

                    boolean all = false;
                    int[] mov = new int[8];
                    for (int j = 0; j < 8; j++) {
                        mov[j] = 1;
                    }

                    int[][] postArr = new int[8][8];
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            postArr[i][j] = GameController.getArr()[i][j];
                        }
                    }

                    /*left*/
                    while ((this.row > mov[0]) && (GameController.getArr()[this.row - mov[0]][this.col] == -1 * wry)) {
                        mov[0]++;
                    }
                    if ((this.row - mov[0] >= 0) && (GameController.getArr()[this.row - mov[0]][this.col] == wry) && (GameController.getArr()[this.row - 1][this.col] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[0]; j++) {
                            postArr[this.row - j][this.col] = wry;
                        }
                    }


                    /*right*/
                    while ((this.row + mov[1] < 7) && (GameController.getArr()[this.row + mov[1]][this.col] == -1 * wry)) {
                        mov[1]++;
                    }
                    if ((this.row + mov[1] <= 7) && GameController.getArr()[this.row + mov[1]][this.col] == wry && (GameController.getArr()[this.row + 1][this.col] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[1]; j++) {
                            postArr[this.row + j][this.col] = wry;
                        }
                    }


                    /*up*/
                    while ((this.col > mov[2]) && (GameController.getArr()[this.row][this.col - mov[2]] == -1 * wry)) {
                        mov[2]++;
                    }
                    if ((this.col - mov[2] >= 0) && GameController.getArr()[this.row][this.col - mov[2]] == wry && (GameController.getArr()[this.row][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[2]; j++) {
                            postArr[this.row][this.col - j] = wry;
                        }
                    }

                    /*down*/
                    while ((this.col + mov[3] < 7) && (GameController.getArr()[this.row][this.col + mov[3]] == -1 * wry)) {
                        mov[3]++;
                    }
                    if ((this.col + mov[3] <= 7) && GameController.getArr()[this.row][this.col + mov[3]] == wry && (GameController.getArr()[this.row][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[3]; j++) {
                            postArr[this.row][this.col + j] = wry;
                        }
                    }

                    /*leftup*/
                    while ((this.row > mov[4]) && (this.col > mov[4]) && (GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == -1 * wry)) {
                        mov[4]++;
                    }
                    if ((this.row - mov[4] >= 0) && (this.col - mov[4] >= 0) && GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == wry && (GameController.getArr()[this.row - 1][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[4]; j++) {
                            postArr[this.row - j][this.col - j] = wry;
                        }
                    }

                    /*leftdown*/
                    while ((this.row > mov[5]) && (this.col + mov[5] < 7) && (GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == -1 * wry)) {
                        mov[5]++;
                    }
                    if ((this.row - mov[5] >= 0) && (this.col + mov[5] <= 7) && GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == wry && (GameController.getArr()[this.row - 1][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[5]; j++) {
                            postArr[this.row - j][this.col + j] = wry;
                        }
                    }

                    /*rightup*/
                    while ((this.row + mov[6] < 7) && (this.col > mov[6]) && (GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == -1 * wry)) {
                        mov[6]++;
                    }
                    if ((this.row + mov[6] <= 7) && (this.col - mov[6] >= 0) && GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == wry && (GameController.getArr()[this.row + 1][this.col - 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[6]; j++) {
                            postArr[this.row + j][this.col - j] = wry;
                        }
                    }

                    /*rightdown*/
                    while ((this.row + mov[7] < 7) && (this.col + mov[7] < 7) && (GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == -1 * wry)) {
                        mov[7]++;
                    }
                    if ((this.row + mov[7] <= 7) && (this.col + mov[7] <= 7) && GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == wry && (GameController.getArr()[this.row + 1][this.col + 1] == -1 * wry)) {
                        all = true;
                        for (int j = 0; j <= mov[7]; j++) {
                            postArr[this.row + j][this.col + j] = wry;
                        }
                    }


                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            if (postArr[i][j] == -1) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.BLACK);
                            }
                            if (postArr[i][j] == 1) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.WHITE);
                            }
                            if (postArr[i][j] == 0) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(null);
                            }
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].repaint();
                        }
                    }
                    repaint();
                    GameFrame.controller.swapPlayer();

                    GameController.reflection(GameFrame.controller.getGamePanel().getChessGrids());


                    for (int w = 0; w < 2; w++) {

                        if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
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
                                        int count = 0;
                                        while (l < 8 && GameController.getArr()[j][l] != 0) {
                                            if (GameController.getArr()[j][l] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            count++;
                                        }
                                        if (tell && l < 8) {
                                            GameController.getJudgeArr()[j][l] += count;
                                        }
                                    }


                                    if (k > 1 && GameController.getArr()[j][k - 1] == 0 - wry) {/*left*/
                                        int l = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && GameController.getArr()[j][l] != 0) {
                                            if (GameController.getArr()[j][l] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            count++;
                                        }
                                        if (tell && l >= 0) {
                                            GameController.getJudgeArr()[j][l] += count;
                                        }
                                    }


                                    if (j > 1 && GameController.getArr()[j - 1][k] == 0 - wry) {/*up*/
                                        int l = j - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && GameController.getArr()[l][k] != 0) {
                                            if (GameController.getArr()[l][k] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            count++;
                                        }
                                        if (tell && l >= 0) {
                                            GameController.getJudgeArr()[l][k] += count;
                                        }
                                    }


                                    if (j < 6 && GameController.getArr()[j + 1][k] == 0 - wry) {/*down*/
                                        int l = j + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && GameController.getArr()[l][k] != 0) {
                                            if (GameController.getArr()[l][k] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            count++;
                                        }
                                        if (tell && l < 8) {
                                            GameController.getJudgeArr()[l][k] += count;
                                        }
                                    }


                                    if (j < 6 && k < 6 && GameController.getArr()[j + 1][k + 1] == 0 - wry) {/*downRight*/
                                        int l = j + 1;
                                        int m = k + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && m < 8 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            m++;
                                            count++;
                                        }
                                        if (tell && l < 8 && m < 8) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j < 6 && k > 1 && GameController.getArr()[j + 1][k - 1] == 0 - wry) {/*downLeft*/
                                        int l = j + 1;
                                        int m = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l < 8 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l++;
                                            m--;
                                            count++;
                                        }
                                        if (tell && l < 8 && m >= 0) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j > 1 && k < 6 && GameController.getArr()[j - 1][k + 1] == 0 - wry) {/*upRight*/
                                        int l = j - 1;
                                        int m = k + 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && m < 8 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            m++;
                                            count++;
                                        }
                                        if (tell && l >= 0 && m < 8) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                    if (j > 1 && k > 1 && GameController.getArr()[j - 1][k - 1] == 0 - wry) {/*upLeft*/
                                        int l = j - 1;
                                        int m = k - 1;
                                        boolean tell = true;
                                        int count = 0;
                                        while (l >= 0 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                            if (GameController.getArr()[l][m] == wry) {
                                                tell = false;
                                            }
                                            l--;
                                            m--;
                                            count++;
                                        }
                                        if (tell && l >= 0 && m >= 0) {
                                            GameController.getJudgeArr()[l][m] += count;
                                        }
                                    }


                                }
                            }
                        }
                        for (int i = 0; i < 8; i++) {
                            for (int l = 0; l < 8; l++) {
                                if (GameController.getJudgeArr()[i][l] > 0) {
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].setChessPiece(ChessPiece.CYAN);
                                    JLabel jl = new JLabel(String.valueOf(GameController.getJudgeArr()[i][l]));
                                    jl.setLocation(20, 5);
                                    jl.setSize(50, 50);
                                    jl.setFont(new Font("Calibri", Font.ITALIC, 50));
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].add(jl);
                                    GameFrame.controller.getGamePanel().getChessGrids()[i][l].repaint();
                                }
                            }
                        }
                        int sum = 0;
                        for (int j = 0; j < 8; j++) {
                            for (int k = 0; k < 8; k++) {
                                sum += GameController.getJudgeArr()[j][k];
                            }
                        }

                        if (sum != 0) {
                            break;
                        } else if (sum == 0 && w == 0) {
                            GameFrame.controller.swapPlayer();
                            GameController.getStatusPanel().setPlayerText(GameFrame.controller.getCurrentPlayer().name());
                        } else if (sum == 0 && w != 0) {
                            GameController.getStatusPanel().isOver = true;
                            break;
                        }

                    }

                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() > GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("BLACK win!");
                        JOptionPane.showMessageDialog(null, "BLACK win!");
                    }
                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() < GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("WHITE win!");
                        JOptionPane.showMessageDialog(null, "WHITE win!");
                    }
                    if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() == GameFrame.controller.getGamePanel().getWhiteNumber()) {
                        System.out.println("Draw!");
                        JOptionPane.showMessageDialog(null, "Draw!");
                    }
                }
            }
        }


        if (GameFrame.isCheatMode && GameFrame.controller.getCurrentPlayer() == GameFrame.cheatModeChoose) {
            if (this.chessPiece == null || this.chessPiece == ChessPiece.CYAN) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        GameFrame.controller.getGamePanel().getChessGrids()[i][j].removeAll();
                    }
                }
                System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
                String e = GameFrame.controller.getCurrentPlayer() + " " + "clicked" + " " + "(" + row + "," + " " + col + ")";
                GameController.EveryStep.add(e);
                this.chessPiece = GameFrame.controller.getCurrentPlayer();

                int wry;
                if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
                    wry = -1;
                } else {
                    wry = 1;
                }

                GameController.getArr()[row][col] = wry;

                boolean all = false;
                int[] mov = new int[8];
                for (int j = 0; j < 8; j++) {
                    mov[j] = 1;
                }

                int[][] postArr = new int[8][8];
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        postArr[i][j] = GameController.getArr()[i][j];
                    }
                }

                /*left*/
                while ((this.row > mov[0]) && (GameController.getArr()[this.row - mov[0]][this.col] == -1 * wry)) {
                    mov[0]++;
                }
                if ((this.row - mov[0] >= 0) && (GameController.getArr()[this.row - mov[0]][this.col] == wry) && (GameController.getArr()[this.row - 1][this.col] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[0]; j++) {
                        postArr[this.row - j][this.col] = wry;
                    }
                }


                /*right*/
                while ((this.row + mov[1] < 7) && (GameController.getArr()[this.row + mov[1]][this.col] == -1 * wry)) {
                    mov[1]++;
                }
                if ((this.row + mov[1] <= 7) && GameController.getArr()[this.row + mov[1]][this.col] == wry && (GameController.getArr()[this.row + 1][this.col] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[1]; j++) {
                        postArr[this.row + j][this.col] = wry;
                    }
                }


                /*up*/
                while ((this.col > mov[2]) && (GameController.getArr()[this.row][this.col - mov[2]] == -1 * wry)) {
                    mov[2]++;
                }
                if ((this.col - mov[2] >= 0) && GameController.getArr()[this.row][this.col - mov[2]] == wry && (GameController.getArr()[this.row][this.col - 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[2]; j++) {
                        postArr[this.row][this.col - j] = wry;
                    }
                }

                /*down*/
                while ((this.col + mov[3] < 7) && (GameController.getArr()[this.row][this.col + mov[3]] == -1 * wry)) {
                    mov[3]++;
                }
                if ((this.col + mov[3] <= 7) && GameController.getArr()[this.row][this.col + mov[3]] == wry && (GameController.getArr()[this.row][this.col + 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[3]; j++) {
                        postArr[this.row][this.col + j] = wry;
                    }
                }

                /*leftup*/
                while ((this.row > mov[4]) && (this.col > mov[4]) && (GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == -1 * wry)) {
                    mov[4]++;
                }
                if ((this.row - mov[4] >= 0) && (this.col - mov[4] >= 0) && GameController.getArr()[this.row - mov[4]][this.col - mov[4]] == wry && (GameController.getArr()[this.row - 1][this.col - 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[4]; j++) {
                        postArr[this.row - j][this.col - j] = wry;
                    }
                }

                /*leftdown*/
                while ((this.row > mov[5]) && (this.col + mov[5] < 7) && (GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == -1 * wry)) {
                    mov[5]++;
                }
                if ((this.row - mov[5] >= 0) && (this.col + mov[5] <= 7) && GameController.getArr()[this.row - mov[5]][this.col + mov[5]] == wry && (GameController.getArr()[this.row - 1][this.col + 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[5]; j++) {
                        postArr[this.row - j][this.col + j] = wry;
                    }
                }

                /*rightup*/
                while ((this.row + mov[6] < 7) && (this.col > mov[6]) && (GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == -1 * wry)) {
                    mov[6]++;
                }
                if ((this.row + mov[6] <= 7) && (this.col - mov[6] >= 0) && GameController.getArr()[this.row + mov[6]][this.col - mov[6]] == wry && (GameController.getArr()[this.row + 1][this.col - 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[6]; j++) {
                        postArr[this.row + j][this.col - j] = wry;
                    }
                }

                /*rightdown*/
                while ((this.row + mov[7] < 7) && (this.col + mov[7] < 7) && (GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == -1 * wry)) {
                    mov[7]++;
                }
                if ((this.row + mov[7] <= 7) && (this.col + mov[7] <= 7) && GameController.getArr()[this.row + mov[7]][this.col + mov[7]] == wry && (GameController.getArr()[this.row + 1][this.col + 1] == -1 * wry)) {
                    all = true;
                    for (int j = 0; j <= mov[7]; j++) {
                        postArr[this.row + j][this.col + j] = wry;
                    }
                }


                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (postArr[i][j] == -1) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.BLACK);
                        }
                        if (postArr[i][j] == 1) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.WHITE);
                        }
                        if (postArr[i][j] == 0) {
                            GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(null);
                        }
                        GameFrame.controller.getGamePanel().getChessGrids()[i][j].repaint();
                    }
                }
                repaint();
                GameFrame.controller.swapPlayer();

                GameController.reflection(GameFrame.controller.getGamePanel().getChessGrids());
                for (int w = 0; w < 2; w++) {

                    if (GameFrame.controller.getCurrentPlayer() == ChessPiece.BLACK) {
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
                                    int count = 0;
                                    while (l < 8 && GameController.getArr()[j][l] != 0) {
                                        if (GameController.getArr()[j][l] == wry) {
                                            tell = false;
                                        }
                                        l++;
                                        count++;
                                    }
                                    if (tell && l < 8) {
                                        GameController.getJudgeArr()[j][l] += count;
                                    }
                                }


                                if (k > 1 && GameController.getArr()[j][k - 1] == 0 - wry) {/*left*/
                                    int l = k - 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l >= 0 && GameController.getArr()[j][l] != 0) {
                                        if (GameController.getArr()[j][l] == wry) {
                                            tell = false;
                                        }
                                        l--;
                                        count++;
                                    }
                                    if (tell && l >= 0) {
                                        GameController.getJudgeArr()[j][l] += count;
                                    }
                                }


                                if (j > 1 && GameController.getArr()[j - 1][k] == 0 - wry) {/*up*/
                                    int l = j - 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l >= 0 && GameController.getArr()[l][k] != 0) {
                                        if (GameController.getArr()[l][k] == wry) {
                                            tell = false;
                                        }
                                        l--;
                                        count++;
                                    }
                                    if (tell && l >= 0) {
                                        GameController.getJudgeArr()[l][k] += count;
                                    }
                                }


                                if (j < 6 && GameController.getArr()[j + 1][k] == 0 - wry) {/*down*/
                                    int l = j + 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l < 8 && GameController.getArr()[l][k] != 0) {
                                        if (GameController.getArr()[l][k] == wry) {
                                            tell = false;
                                        }
                                        l++;
                                        count++;
                                    }
                                    if (tell && l < 8) {
                                        GameController.getJudgeArr()[l][k] += count;
                                    }
                                }


                                if (j < 6 && k < 6 && GameController.getArr()[j + 1][k + 1] == 0 - wry) {/*downRight*/
                                    int l = j + 1;
                                    int m = k + 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l < 8 && m < 8 && GameController.getArr()[l][m] != 0) {
                                        if (GameController.getArr()[l][m] == wry) {
                                            tell = false;
                                        }
                                        l++;
                                        m++;
                                        count++;
                                    }
                                    if (tell && l < 8 && m < 8) {
                                        GameController.getJudgeArr()[l][m] += count;
                                    }
                                }


                                if (j < 6 && k > 1 && GameController.getArr()[j + 1][k - 1] == 0 - wry) {/*downLeft*/
                                    int l = j + 1;
                                    int m = k - 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l < 8 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                        if (GameController.getArr()[l][m] == wry) {
                                            tell = false;
                                        }
                                        l++;
                                        m--;
                                        count++;
                                    }
                                    if (tell && l < 8 && m >= 0) {
                                        GameController.getJudgeArr()[l][m] += count;
                                    }
                                }


                                if (j > 1 && k < 6 && GameController.getArr()[j - 1][k + 1] == 0 - wry) {/*upRight*/
                                    int l = j - 1;
                                    int m = k + 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l >= 0 && m < 8 && GameController.getArr()[l][m] != 0) {
                                        if (GameController.getArr()[l][m] == wry) {
                                            tell = false;
                                        }
                                        l--;
                                        m++;
                                        count++;
                                    }
                                    if (tell && l >= 0 && m < 8) {
                                        GameController.getJudgeArr()[l][m] += count;
                                    }
                                }


                                if (j > 1 && k > 1 && GameController.getArr()[j - 1][k - 1] == 0 - wry) {/*upLeft*/
                                    int l = j - 1;
                                    int m = k - 1;
                                    boolean tell = true;
                                    int count = 0;
                                    while (l >= 0 && m >= 0 && GameController.getArr()[l][m] != 0) {
                                        if (GameController.getArr()[l][m] == wry) {
                                            tell = false;
                                        }
                                        l--;
                                        m--;
                                        count++;
                                    }
                                    if (tell && l >= 0 && m >= 0) {
                                        GameController.getJudgeArr()[l][m] += count;
                                    }
                                }


                            }
                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        for (int l = 0; l < 8; l++) {
                            if (GameController.getJudgeArr()[i][l] > 0) {
                                GameFrame.controller.getGamePanel().getChessGrids()[i][l].setChessPiece(ChessPiece.CYAN);
                                JLabel jl = new JLabel(String.valueOf(GameController.getJudgeArr()[i][l]));
                                jl.setLocation(20, 5);
                                jl.setSize(50, 50);
                                jl.setFont(new Font("Calibri", Font.ITALIC, 50));
                                GameFrame.controller.getGamePanel().getChessGrids()[i][l].add(jl);
                                GameFrame.controller.getGamePanel().getChessGrids()[i][l].repaint();
                            }
                        }
                    }
                    int sum = 0;
                    for (int j = 0; j < 8; j++) {
                        for (int k = 0; k < 8; k++) {
                            sum += GameController.getJudgeArr()[j][k];
                        }
                    }

                    if (sum != 0) {
                        break;
                    } else if (sum == 0 && w == 0) {
                        GameFrame.controller.swapPlayer();
                        GameController.getStatusPanel().setPlayerText(GameFrame.controller.getCurrentPlayer().name());
                    } else if (sum == 0 && w != 0) {
                        GameController.getStatusPanel().isOver = true;
                        break;
                    }

                }

                if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() > GameFrame.controller.getGamePanel().getWhiteNumber()) {
                    System.out.println("BLACK win!");
                    JOptionPane.showMessageDialog(null, "BLACK win!");
                }
                if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() < GameFrame.controller.getGamePanel().getWhiteNumber()) {
                    System.out.println("WHITE win!");
                    JOptionPane.showMessageDialog(null, "WHITE win!");
                }
                if (GameController.getStatusPanel().isOver && GameFrame.controller.getGamePanel().getBlackNumber() == GameFrame.controller.getGamePanel().getWhiteNumber()) {
                    System.out.println("Draw!");
                    JOptionPane.showMessageDialog(null, "Draw!");
                }
            }
        }
    }


    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPiece(Graphics g) {
        if (symbol == 1) {
            gridColor = gridColor01;
        } else {
            gridColor = gridColor02;
        }
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
    }

    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
    }
}
