
import view.EnterFrame;
import view.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        new Music();
        SwingUtilities.invokeLater(() -> {
            GameFrame mainFrame = new GameFrame(600);
            EnterFrame enterFrame = new EnterFrame(mainFrame);
            enterFrame.setVisible(true);
            new Music();
        });
    }
}

