package view;

import javax.swing.*;
import java.awt.*;
import controller.GameController;

public class EnterFrame extends JFrame {
    JButton enterGameBtn = new JButton("enter game");
    JButton quitGameBtn = new JButton("Quit");
    JLabel gameName = new JLabel();
    GameFrame gameFrame;
    final ImageIcon image;
    Image image2;
    public static GameController controller;

    public EnterFrame(GameFrame gameFrame){
        super("Othello Shiyouyo");
        this.gameFrame = gameFrame;

        this.setSize(500,800);

        image = new ImageIcon("enterBackground.jpg");
        image2 = image.getImage();
        image.setImage(image2.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_AREA_AVERAGING));
        JLabel picture = new JLabel(image);
        picture.setBounds(0,0,image.getIconWidth(),image.getIconHeight());
        JPanel jPanel = (JPanel)this.getContentPane();
        jPanel.setOpaque(false);
        this.getLayeredPane().setLayout(null);
        this.getLayeredPane().add(picture,new Integer(Integer.MIN_VALUE));


        gameName.setText("Othello Shiyouyo");
        gameName.setSize(500,200);
        gameName.setFont(new Font("Times New Roman",Font.ITALIC,60));
        gameName.setLocation(32,0);

        enterGameBtn.setLocation(150,300);
        enterGameBtn.setSize(200,50);
        enterGameBtn.addActionListener(e -> {
            String filePath = JOptionPane.showInputDialog(this, "input the name here");
            JOptionPane.showMessageDialog(null, "Hello"+" "+filePath+"!");
            gameFrame.setVisible(true);
            this.setVisible(false);
        });

        quitGameBtn.setLocation(150,600);
        quitGameBtn.setSize(200,50);
        quitGameBtn.addActionListener(e -> {
            System.exit(0);
        });

        this.getLayeredPane().add(gameName);
        this.getLayeredPane().add(quitGameBtn);
        this.getLayeredPane().add(enterGameBtn);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
