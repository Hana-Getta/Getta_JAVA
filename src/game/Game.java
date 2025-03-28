package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Game extends JFrame {
    private final String user_name;
    private GamePanel panel;
    private JTextField jt;
    private JLabel timeAndScoreLabel;
    private int score = 0;

    public Game(String name) {
        this.user_name = name;

        setTitle("산성비 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.decode("#222222"));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#222222"));

        timeAndScoreLabel = new JLabel("남은 시간: 30초  |  점수: 0", JLabel.CENTER);
        timeAndScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
        timeAndScoreLabel.setForeground(Color.white);
        topPanel.add(timeAndScoreLabel, BorderLayout.CENTER);

        c.add(topPanel, BorderLayout.NORTH);

        panel = new GamePanel(this);

        jt = new JTextField();
        jt.setPreferredSize(new Dimension(300, 50));
        jt.setFont(new Font("SansSerif", Font.BOLD, 20));
        jt.addActionListener(new GameActionListener(panel, jt,this));

        JPanel jp = new JPanel();
        Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.decode("#cccccc"));
        jp.setBorder(BorderFactory.createCompoundBorder(topBorder, BorderFactory.createEmptyBorder(10, 0, 20, 0)));
        jp.setBackground(Color.decode("#222222"));

        jp.add(jt);
        c.add(jp, BorderLayout.SOUTH);
        c.add(panel, BorderLayout.CENTER);

        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            panel.addNewWord();
            revalidate();
            repaint();
        });
    }

    public void updateScoreAndTime(int score, int remainingTime) {
        this.score = score;
        if (timeAndScoreLabel != null) {
            timeAndScoreLabel.setText("남은 시간: " + remainingTime + "초  |  점수: " + score);
        }
    }

    public int getScore() {
        return score;
    }

    public String getUserName(){
        return user_name;
    }
}