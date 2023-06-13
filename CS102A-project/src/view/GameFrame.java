package view;


import components.ChessGridComponent;
import controller.GameController;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    public static GameController controller;
    private ChessBoardPanel chessBoardPanel;
    private StatusPanel statusPanel;
    public static boolean isCheatMode = false;
    public static ChessPiece cheatModeChoose ;
    final ImageIcon image;
    Image image2;
    JButton NormalModeBtn;
    JButton RegretBtn;


    public GameFrame(int frameSize) {

        this.setTitle("2021F CS102A Project Reversi");

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        Insets inset = this.getInsets();
        this.setSize(frameSize + inset.left + inset.right, frameSize + inset.top + inset.bottom + 300);

        this.setLocationRelativeTo(null);

        image = new ImageIcon("background.jpg");
        image2 = image.getImage();
        image.setImage(image2.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_AREA_AVERAGING));
        JLabel jLabel = new JLabel(image);
        jLabel.setBounds(0,50,image.getIconWidth(),image.getIconHeight());
        JPanel jPanel = (JPanel)this.getContentPane();
        jPanel.setOpaque(false);
        this.getLayeredPane().setLayout(null);

        this.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE));

        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);

        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);

        this.getLayeredPane().add(chessBoardPanel);
        this.getLayeredPane().add(statusPanel);


        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() + chessBoardPanel.getHeight()) / 2);
        this.getLayeredPane().add(restartBtn);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");

            controller.initialIsOver();
            chessBoardPanel.initialGame();
            controller.initialCurrentPlayer();
            controller.initialScore();
            repaint();
        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        this.getLayeredPane().add(loadGameBtn);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.readFileData(filePath);
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        this.getLayeredPane().add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            String filePath = JOptionPane.showInputDialog(this, "input the path here");
            controller.writeDataToFile(filePath);
        });

        JButton CheatModeBtn = new JButton("CheatMode");
        CheatModeBtn.setSize(120,50);
        CheatModeBtn.setLocation(restartBtn.getX(), restartBtn.getY()+ restartBtn.getHeight()+30);
        this.getLayeredPane().add(CheatModeBtn);
        CheatModeBtn.addActionListener(e -> {
            Object[] objects = {"Black","White"};
            int m = JOptionPane.showOptionDialog(null,"Which side you want to be?","Cheat Mode Open",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,objects,objects[0]);
            System.out.println(m);
            if (m == 0){
                controller.setCurrentPlayer(ChessPiece.BLACK);
                statusPanel.setPlayerText(controller.getCurrentPlayer().name());
                cheatModeChoose = ChessPiece.BLACK;
            }
            if (m == 1){
                controller.setCurrentPlayer(ChessPiece.WHITE);
                statusPanel.setPlayerText(controller.getCurrentPlayer().name());
                cheatModeChoose = ChessPiece.WHITE;
            }
            isCheatMode = true;
            statusPanel.setModeLabel("CheatMode");
            NormalModeBtn.setVisible(true);
            CheatModeBtn.setVisible(false);
            RegretBtn.setVisible(false);
            repaint();
        });

        NormalModeBtn = new JButton("NormalMode");
        NormalModeBtn.setSize(120,50);
        NormalModeBtn.setLocation(restartBtn.getX(),restartBtn.getY()+ restartBtn.getHeight()+30);
        this.getLayeredPane().add(NormalModeBtn);
        NormalModeBtn.setVisible(false);
        NormalModeBtn.addActionListener(e -> {
            isCheatMode = false;
            statusPanel.setModeLabel("Normal Mode");
            NormalModeBtn.setVisible(false);
            CheatModeBtn.setVisible(true);
        });

        RegretBtn = new JButton("Regret");
        RegretBtn.setSize(120,50);
        RegretBtn.setLocation(saveGameBtn.getX(),restartBtn.getY()+ restartBtn.getHeight()+30);
        this.getLayeredPane().add(RegretBtn);
        RegretBtn.addActionListener(e -> {
            controller.regret(GameController.LastArr);
            controller.swapPlayer();
            GameController.setCopyArr(GameController.getArr());
            GameController.setArr(GameController.LastArr);
//            repaint();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (GameController.arr[i][j] == -1) {
                        GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.BLACK);
                    }
                    if (GameController.arr[i][j] == 1) {
                        GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(ChessPiece.WHITE);
                    }
                    if (GameController.arr[i][j] == 0) {
                        GameFrame.controller.getGamePanel().getChessGrids()[i][j].setChessPiece(null);
                    }
                    GameFrame.controller.getGamePanel().getChessGrids()[i][j].repaint();
                }
            }
        });
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton GiveUpBtn = new JButton("Give up");
        GiveUpBtn.setSize(120,50);
        GiveUpBtn.setLocation(loadGameBtn.getX(),restartBtn.getY()+ restartBtn.getHeight()+30);
        this.getLayeredPane().add(GiveUpBtn);
        GiveUpBtn.addActionListener(e -> {
            Object[] objects = {"Yes","No"};
            int m = JOptionPane.showOptionDialog(null,"Are you sure?","Give up",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,objects,objects[0]);
            if (m == 0){
                JOptionPane.showMessageDialog(null, controller.getAnotherPlayer()+" "+"win!");
            }
        });

    }

}
