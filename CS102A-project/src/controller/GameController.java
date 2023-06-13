package controller;

import components.ChessGridComponent;
import model.ChessPiece;
import view.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class GameController {


    private static ChessBoardPanel gamePanel;

    public static StatusPanel getStatusPanel() {
        return statusPanel;
    }

    private static StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    private static int blackScore;
    private static int whiteScore;

    public static ArrayList<String> EveryStep = new ArrayList<>();

    public static int[][] arr = new int[8][8];
    public static int[][] LastArr = new int[8][8];
    public static int[][] CopyArr = new int[8][8];

    public static int[][] getLastArr() {
        return LastArr;
    }

    public static int[][] getCopyArr() {
        return CopyArr;
    }

    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
    }

    public void initialCurrentPlayer() {
        currentPlayer = ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
    }

    public void initialScore() {
        blackScore = 2;
        whiteScore = 2;
        statusPanel.setScoreText(2, 2);
    }

    public void initialIsOver(){
        StatusPanel.isOver = false;
    }

    public void swapPlayer() {
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
    }

    public static void countScore() {
        //todo: modify the countScore method
        blackScore = gamePanel.countBlackScore();
        whiteScore = gamePanel.countWhiteScore();
    }


    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessPiece getAnotherPlayer(){
        if (getCurrentPlayer()==ChessPiece.BLACK){
            return ChessPiece.WHITE;
        }
        if (getCurrentPlayer()==ChessPiece.WHITE){
            return ChessPiece.BLACK;
        }
        return null;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setCurrentPlayer(ChessPiece currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void readFileData(String fileName) {
        //todo: read date from file
        List<String> fileData = new ArrayList<>();
        String[] l;
        String[] file = fileName.split("\\.");
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            fileData.forEach(System.out::println);

            for (int i = 0; i < 8; i++) {
                l = fileData.get(i).split(" ");
                BoardErrorCheck1(l);
            }
            TurnErrorCheck(fileData);//103
            if (!fileData.get(8).equals("White's Turn") && !fileData.get(8).equals("Black's Turn")) {
                JOptionPane.showMessageDialog(null, "101:ChessBoard Wrong");
                throw new ChessBoardException("101:ChessBoard Wrong");
            }

            int[][] datas = new int[8][8];
            for (int i = 0; i < 8; i++) {
                l = fileData.get(i).split(" ");
                for (int j = 0; j < l.length; j++) {
                    datas[i][j] = Integer.parseInt(l[j]);
                }
            }
            ChessPieceCheck(datas);//102
            formatErrorCheck(file[1]);//104

            List<String> steps = new ArrayList<>();
            for (int i = 9; i < fileData.size(); i++) {
                steps.add(fileData.get(i));
            }
            illegalNormalGame(steps,datas);
            elseError(steps);
            if (fileData.get(8).equals("Black's Turn")) {
                setCurrentPlayer(ChessPiece.BLACK);
                statusPanel.setPlayerText("Black");
            }
            if (fileData.get(8).equals("White's Turn")) {
                setCurrentPlayer(ChessPiece.WHITE);
                statusPanel.setPlayerText("White");
            }
            gamePanel.loadGameFromData(datas);
        } catch (IOException | ChessBoardException e) {
            e.printStackTrace();
        }
        GameController.reflection(GameFrame.controller.getGamePanel().getChessGrids());
        countScore();
        statusPanel.setScoreText(blackScore, whiteScore);
    }


    public static void BoardErrorCheck1(String[] l) throws ChessBoardException {
        if (l.length != 8) {
            JOptionPane.showMessageDialog(null, "101:ChessBoard Wrong");
            throw new ChessBoardException("101:ChessBoard Wrong");
        }
    }

    public static void ChessPieceCheck(int[][] datas) throws ChessBoardException {
        boolean isContain = false;
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j] != 0 && datas[i][j] != 1 && datas[i][j] != -1) {
                    isContain = true;
                    break;
                }
            }
        }
        if (isContain) {
            JOptionPane.showMessageDialog(null, "102:ChessPiece Wrong");
            throw new ChessBoardException("102:ChessPiece Wrong");
        }
        boolean WithoutBlack =true;
        boolean WithoutWhite =true;
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j] == -1){
                    WithoutBlack = false;
                    break;
                }
            }
        }
        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[i].length; j++) {
                if (datas[i][j] == 1){
                    WithoutWhite = false;
                    break;
                }
            }
        }
        if (WithoutBlack){
            JOptionPane.showMessageDialog(null, "102:ChessPiece Wrong");
            throw new ChessBoardException("102:ChessPiece Wrong");
        }
        if (WithoutWhite){
            JOptionPane.showMessageDialog(null, "102:ChessPiece Wrong");
            throw new ChessBoardException("102:ChessPiece Wrong");
        }
    }

    public static void TurnErrorCheck(List<String> fileData) throws ChessBoardException {
        boolean isContainNextTurn = false;
        for (int i = 0; i < fileData.size(); i++) {
            if (fileData.get(i).equals("Black's Turn") || fileData.get(i).equals("White's Turn")) {
                isContainNextTurn = true;
                break;
            }
        }
        if (!isContainNextTurn) {
            JOptionPane.showMessageDialog(null, "103:Turn Wrong");
            throw new ChessBoardException("103:Turn Wrong");
        }
    }

    public static void formatErrorCheck(String file) throws ChessBoardException {
        if (!file.equals("txt")) {
            JOptionPane.showMessageDialog(null, "104:Format Wrong");
            throw new ChessBoardException("104:Format Wrong");
        }
    }

    public ArrayList<String> getEveryStep() {
        return EveryStep;
    }

    public static void illegalNormalGame(List<String> steps,int[][] datas) throws ChessBoardException {
        int[][] board = new int[98][98];
        board[48][48] = -1;//black
        board[48][49] = 1;//white
        board[49][48] = 1;
        board[49][49] = -1;
        int[][] checkBoard = new int[8][8];

        int w = 0;
        int row, column;
        int colorChoose = 0;
        for (int i = 0; i < steps.size(); i++) {
            int valid = 0;
            String[] eachStep = steps.get(i).split(" ");
            if (eachStep[0].equals("BLACK")) {
                colorChoose = -1;
            }
            if (eachStep[0].equals("WHITE")) {
                colorChoose = 1;
            }
            if (!eachStep[0].equals("WHITE")&&!eachStep[0].equals("BLACK")){
                JOptionPane.showMessageDialog(null,"106:other wrong");
                throw new ChessBoardException("106:other wrong");
            }
            int a = getRow(steps.get(i));
            int b = getCol(steps.get(i));
            int x = a + 45;
            int y = b + 45;
            int zuo = 0, you = 0, shang = 0, xia = 0, zuoShang = 0, youShang = 0, zuoXia = 0, youXia = 0;
            int step = 0;
            if (board[x][y] != 0) {
                w += 1;
                JOptionPane.showMessageDialog(null,"105:illegal move");
                throw new ChessBoardException("105");
            }
            if (board[x][y] == 0) {
                if (board[x][y - 1] != -colorChoose && board[x][y + 1] != -colorChoose && board[x - 1][y] != -colorChoose && board[x + 1][y] != -colorChoose && board[x - 1][y - 1] != -colorChoose && board[x + 1][y - 1] != -colorChoose && board[x - 1][y + 1] != -colorChoose && board[x + 1][y + 1] != -colorChoose) {
                    /*for (row = 45; row < 53; row++) {
                        for (column = 45; column < 53; column++) {
                            checkBoard[row-45][column-45] = board[row][column];
                        }
                    }*/
                    board[x][y] = colorChoose;
                    //w+=1;
                    //break;
                } else {
                    board[x][y] = colorChoose;
                    if (board[x][y - 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x][y - j] == 0) {
                                break;
                            }
                            if (board[x][y - j] == colorChoose) {
                                valid += 1;
                                zuo = 1;
                                break;
                            }
                        }
                    }
                    if (board[x][y + 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x][y + j] == 0) {
                                break;
                            }
                            if (board[x][y + j] == colorChoose) {
                                valid += 1;
                                you = 1;
                                break;
                            }
                        }
                    }
                    if (board[x - 1][y] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x - j][y] == 0) {
                                break;
                            }
                            if (board[x - j][y] == colorChoose) {
                                valid += 1;
                                shang = 1;
                                break;
                            }
                        }
                    }
                    if (board[x + 1][y] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x + j][y] == 0) {
                                break;
                            }
                            if (board[x + j][y] == colorChoose) {
                                valid += 1;
                                xia = 1;
                                break;
                            }
                        }
                    }
                    if (board[x - 1][y - 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x - j][y - j] == 0) {
                                break;
                            }
                            if (board[x - j][y - j] == colorChoose) {
                                valid += 1;
                                zuoShang = 1;
                                break;
                            }
                        }
                    }
                    if (board[x + 1][y - 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x + j][y - j] == 0) {
                                break;
                            }
                            if (board[x + j][y - j] == colorChoose) {
                                valid += 1;
                                zuoXia = 1;
                                break;
                            }
                        }
                    }
                    if (board[x - 1][y + 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x - j][y + j] == 0) {
                                break;
                            }
                            if (board[x - j][y + j] == colorChoose) {
                                valid += 1;
                                youShang = 1;
                                break;
                            }
                        }
                    }
                    if (board[x + 1][y + 1] == -colorChoose) {
                        for (int j = 2; j <= 8; j++) {
                            if (board[x + j][y + j] == 0) {
                                break;
                            }
                            if (board[x + j][y + j] == colorChoose) {
                                valid += 1;
                                youXia = 1;
                                break;
                            }
                        }
                    }
                    if (valid == 0) {
                        for (row = 45; row < 53; row++) {
                            for (column = 45; column < 53; column++) {
                                checkBoard[row-45][column-45] = board[row][column];
                            }
                        }
                        w+=1;
                        break;
                    } else {
                        if (zuo == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x][y - j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x][y - j] = colorChoose;
                            }
                        }
                        if (you == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x][y + j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x][y + j] = colorChoose;
                            }
                        }
                        if (shang == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x - j][y] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x - j][y] = colorChoose;
                            }
                        }
                        if (xia == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x + j][y] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x + j][y] = colorChoose;
                            }
                        }
                        if (zuoShang == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x - j][y - j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x - j][y - j] = colorChoose;
                            }
                        }
                        if (zuoXia == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x + j][y - j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x + j][y - j] = colorChoose;
                            }
                        }
                        if (youShang == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x - j][y + j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x - j][y + j] = colorChoose;
                            }
                        }
                        if (youXia == 1) {
                            for (int j = 2; j <= 8; j++) {
                                if (board[x + j][y + j] == colorChoose) {
                                    step = j;
                                    break;
                                }
                            }
                            for (int j = 1; j < step; j++) {
                                board[x + j][y + j] = colorChoose;
                            }
                        }
                    }
                }
            }
        }
        if (w==0){
            for (row = 45; row < 53; row++) {
                for (column = 45; column < 53; column++) {
                    checkBoard[row-45][column-45] = board[row][column];
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (checkBoard[i][j]!=datas[i][j]){
                    JOptionPane.showMessageDialog(null,"105:illegal move");
                    throw new ChessBoardException("105");
                }
            }
        }
    }

    public static void elseError(List<String> steps) throws ChessBoardException{
        boolean isWrong = false;
        for (int i = 0; i < steps.size(); i++) {
            String[] b = steps.get(i).split(" ");
            if (!b[0].equals("BLACK")&&!b[0].equals("WHITE")&&!b[0].equals("Invalid")){
                isWrong = true;
                break;
            }
        }
        if (isWrong){
            throw new ChessBoardException("106");
        }
    }

    public void writeDataToFile (String fileName){
        //todo: write data into file\
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String[] strings = new String[9];
            int[][] board = new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (GameFrame.controller.getGamePanel().getChessGrids()[i][j].getChessPiece() == ChessPiece.BLACK) {
                        board[i][j] = -1;
                    }
                    if (GameFrame.controller.getGamePanel().getChessGrids()[i][j].getChessPiece() == ChessPiece.WHITE) {
                        board[i][j] = 1;
                    }
                    if (GameFrame.controller.getGamePanel().getChessGrids()[i][j].getChessPiece() == null) {
                        board[i][j] = 0;
                    }
                }
            }
            if (getCurrentPlayer() == ChessPiece.BLACK) {
                strings[8] = "Black's Turn";
            }
            if (getCurrentPlayer() == ChessPiece.WHITE) {
                strings[8] = "White's Turn";
            }
            for (int i = 0; i < 8; i++) {
                strings[i] = board[i][0] + " " + board[i][1] + " " + board[i][2] + " " + board[i][3] + " " + board[i][4] + " " +
                        board[i][5] + " " + board[i][6] + " " + board[i][7];
            }
            for (int i = 0; i < 9; i++) {
                bufferedWriter.write(strings[i]);
                bufferedWriter.newLine();
            }
            for (int i = 0; i < EveryStep.size(); i++) {
                bufferedWriter.write(EveryStep.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getRow (String step){
        char a = step.charAt(15);
        int row = Character.getNumericValue(a);
        return row;
    }

    public static int getCol (String step){
        char a = step.charAt(18);
        return Character.getNumericValue(a);
    }


    public static void reArr() {
        arr[3][3] = -1;
        arr[3][4] = 1;
        arr[4][3] = 1;
        arr[4][4] = -1;
    }

    public static void initialLastArr(){
        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                LastArr[i][j] = 0;
            }
        }
        LastArr[3][3] = -1;
        LastArr[3][4] = 1;
        LastArr[4][3] = 1;
        LastArr[4][4] = -1;
    }

    public static void initialCopyArr(){
        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                CopyArr[i][j] = 0;
            }
        }
        CopyArr[3][3] = -1;
        CopyArr[3][4] = 1;
        CopyArr[4][3] = 1;
        CopyArr[4][4] = -1;
    }

    public static void setCopyArr(int[][] arr){
//        GameController.CopyArr = arr;  Luo%^%
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                CopyArr[i][j] = arr[i][j];
            }
        }
    }

    public void regret(int[][] arr){
        gamePanel.clearPieces();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (arr[i][j] == 0) gamePanel.getChessGrids()[i][j].setChessPiece(null);
                if (arr[i][j] == 1) gamePanel.getChessGrids()[i][j].setChessPiece(ChessPiece.WHITE);
                if (arr[i][j] == -1) gamePanel.getChessGrids()[i][j].setChessPiece(ChessPiece.BLACK);
                GameFrame.controller.getGamePanel().getChessGrids()[i][j].repaint();
            }
        }
    }

    public static void setArr(int[][] brr) {
//        GameController.arr = arr; Luo%^%
        for (int i = 0; i <8; i++) {
            for (int j = 0; j <8; j++) {
                arr[i][j] = brr[i][j];
            }
        }
    }

    public static void reflection(ChessGridComponent[][] chesses) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chesses[i][j].getChessPiece() == ChessPiece.BLACK) {
                    GameController.getArr()[i][j] = -1;
                }
                if (chesses[i][j].getChessPiece() == ChessPiece.WHITE) {
                    GameController.getArr()[i][j] = 1;
                }
                if (chesses[i][j].getChessPiece() == null||chesses[i][j].getChessPiece()==ChessPiece.CYAN) {
                    GameController.getArr()[i][j] = 0;
                }
            }
        }
    }


    public static int[][] getArr() {
        return arr;
    }

    public static int[][] judgeArr = new int[8][8];

    public static int[][] getJudgeArr() {
        return judgeArr;
    }

    public static void setJudgeArr(int[][] judgeArr) {
        GameController.judgeArr = judgeArr;
    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }

    public boolean CheatModeClick(int row, int col){
        return true;
    }


}
