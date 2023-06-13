package view;

import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private JLabel playerLabel;
    private JLabel scoreLabel;
    private JLabel modeLabel;
    public static boolean isOver = false;

    public StatusPanel(int width, int height) {
        this.setSize(width, height);
        this.setLayout(null);
        this.setVisible(true);

        this.playerLabel = new JLabel();
        this.playerLabel.setLocation(0, 0);
        this.playerLabel.setSize((int) (width * 0.4),  (int) (height*0.5));
        this.playerLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        this.setPlayerText(ChessPiece.BLACK.name());
        add(playerLabel);

        this.scoreLabel = new JLabel();
        this.scoreLabel.setLocation((int) (width * 0.6), 0);
        this.scoreLabel.setSize((int) (width * 0.4),  (int) (height*0.5));
        this.scoreLabel.setFont(new Font("Calibri", Font.BOLD, 21));
        this.setScoreText(2,2);
        add(scoreLabel);

        this.modeLabel = new JLabel();
        this.modeLabel.setLocation((int)(width*0.35), (int) (height*0.5));
        this.modeLabel.setSize((int) (width * 0.4), (int) (height*0.5));
        this.modeLabel.setFont(new Font("Calibri", Font.ITALIC, 20));
        this.setModeLabel("Normal Mode");
        add(modeLabel);

    }

    public void setScoreText(int black, int white) {
        if (!isOver) {
            this.scoreLabel.setText(String.format("BLACK: %d\tWHITE: %d", black, white));
        }
    }

    public void setPlayerText(String playerText) {
        this.playerLabel.setText(playerText + "'s turn");
    }

    public void setModeLabel(String ModeText){
        this.modeLabel.setText(ModeText);
    }
    
}
