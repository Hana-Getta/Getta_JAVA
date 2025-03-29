package game;

import javax.swing.*;
import java.awt.event.*;

public class GameActionListener implements ActionListener {
    private GamePanel panel;
    private JTextField jt;
    private Game game;
    public GameActionListener(GamePanel panel, JTextField jt, Game game) {
        this.panel = panel;
        this.jt = jt;
        this.game = game;
    }

    public void actionPerformed(ActionEvent e) {
        String inputText = jt.getText().trim();

        JLabel matchedLabel = null;
        int maxY = Integer.MIN_VALUE;

        for (JLabel label : panel.getLabels()) {
            if (inputText.equals(label.getText()) && label.getLocation().y > maxY) {
                matchedLabel = label;
                maxY = label.getLocation().y;
            }
        }

        if (matchedLabel != null) {
            // 단어 맞추면 바로 새로운 단어 생성되게 하는 기능
            matchedLabel.setText(panel.getRandomWord()); // 새로운 단어로 변경
            matchedLabel.setLocation((int) (Math.random() * panel.getWidth() / 2), 0); // 새로운 랜덤 위치로 설정

            game.updateScoreAndTime(game.getScore() + 10, ((int)(panel.getTimeLimit()-panel.getElapsedTime())/1000));

            /*
            // 일치하는 단어를 화면에서 제거
            panel.removeLabel(matchedLabel)

            // 화면을 다시 그려서 변경사항 반영
            panel.revalidate();
            panel.repaint();
             */

        }

        jt.setText("");
    }
}