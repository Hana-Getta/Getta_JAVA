package game;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static game.GameRecord.saveGameRecord;

public class GamePanel extends JPanel implements Runnable {
    private long lastTime = System.currentTimeMillis();
    private final long timeLimit = 30000;
    private long startTime;
    private int wordInterval = 5000;
    private List<JLabel> labels = new ArrayList<>();
    private final String[] words = {
            "${title}", "$(el)", "<%=name%>", "@log", "@Test", "x->x", "a=>b",
            "std::cin", "KClass::name", "%%time", "$PATH", "*ptr", "&var", "a&&b",
            "x||y", "n:=10", "x!=y", "a!==b", "x<=y", "x>=y", "val??0", "obj?.key",
            "\"\\n\"", "\"\\t\"", "r\"\\w+\"", "/abc/", "x^y", "r\"end$\"", "$(VAR)", "<%=id%>"
    };
    private final Vector<String> v = new Vector<>(Arrays.asList(words));
    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setLayout(null);
        setBackground(Color.decode("#222222"));

        Thread th = new Thread(this);
        th.start();
    }


    public void run() {
        startTime = System.currentTimeMillis();

        while (true) {
            try {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = currentTime - startTime;

                if (elapsedTime >= timeLimit) {
                    saveGameRecord(game.getUserName(), game.getScore());
                    JOptionPane.showMessageDialog(game, "게임 시간이 종료되었습니다.");
                    synchronized (game) {
                        game.notify();
                    }
                    game.dispose();
                    return;
                }

                // 남은 시간 계산
                int remainingTime = (int) ((timeLimit - elapsedTime) / 1000);
                game.updateScoreAndTime(game.getScore(), remainingTime);

                if (currentTime - lastTime >= wordInterval) {
                    addNewWord();
                    lastTime = currentTime;
                }

                Iterator<JLabel> iterator = labels.iterator();
                while (iterator.hasNext()) {
                    JLabel label = iterator.next();
                    Point p = label.getLocation();
                    label.setLocation(p.x, p.y + 10); // 단어 이동

                    // 단어가 화면 밖으로 나가면 리스트에서 제거
                    if (p.y > getHeight() - 15) {
                        iterator.remove();
                        remove(label);
                    }
                }

                repaint();
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public List<JLabel> getLabels() {
        return labels;
    }

    public void removeLabel(JLabel label) {
        labels.remove(label);
        remove(label);
    }

    public String getRandomWord() {
        return v.get(new Random().nextInt(v.size()));
    }

    public void addNewWord() {
        JLabel newLabel = new JLabel();
        newLabel.setText(v.get(new Random().nextInt(v.size())));
        newLabel.setFont(new Font("Serif", Font.BOLD, 35));
        newLabel.setSize(400, 50);
        newLabel.setForeground(Color.decode("#cccccc"));
        newLabel.setLocation(new Random().nextInt(getWidth() / 2), 0);
        labels.add(newLabel);
        add(newLabel);
    }
}