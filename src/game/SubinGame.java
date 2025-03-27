
package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class SubinGame extends JFrame {
    private MyPanel panel;
    private JPanel jp;
    private JTextField jt;
    private JLabel timeLabel; // 남은 시간을 표시할 JLabel 추가
    private final String[] words = {
            "${title}", "$(el)", "<%=name%>", "@log", "@Test", "x->x", "a=>b",
            "std::cin", "KClass::name", "%%time", "$PATH", "*ptr", "&var", "a&&b",
            "x||y", "n:=10", "x!=y", "a!==b", "x<=y", "x>=y", "val??0", "obj?.key",
            "\"\\n\"", "\"\\t\"", "r\"\\w+\"", "/abc/", "x^y", "r\"end$\"", "$(VAR)", "<%=id%>"
    };
    private final Vector<String> v = new Vector<>(Arrays.asList(words));

    public SubinGame() {
        setTitle("산성비 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // 창을 화면 크기에 맞게 최대화
        setLocationRelativeTo(null); // 화면 중앙에 위치하도록 설정

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.setBackground(Color.decode("#222222"));

        jt = new JTextField();
        jt.setPreferredSize(new Dimension(300, 50));
        jt.setFont(new Font("Serif", Font.BOLD, 24));
        jt.addActionListener(new MyActionListener());
        jp = new JPanel();
        Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.decode("#cccccc"));
        jp.setBorder(BorderFactory.createCompoundBorder(topBorder, BorderFactory.createEmptyBorder(10, 0, 20, 0)));
        jp.setBackground(Color.decode("#222222"));
        jp.add(jt);
        c.add(jp, BorderLayout.SOUTH);

        // 남은 시간을 표시할 JLabel 추가
        timeLabel = new JLabel("남은 시간: 60초", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        timeLabel.setForeground(Color.white);
        timeLabel.setPreferredSize(new Dimension(getWidth(), 50));
        c.add(timeLabel, BorderLayout.NORTH);

        panel = new MyPanel(timeLabel); // MyPanel 생성 시 timeLabel 전달
        c.add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    class MyPanel extends JPanel implements Runnable {
        private JLabel timeLabel;
        private long startTime; // 게임 시작 시간
        private int timeLimit = 60000; // 60초 제한 (60000ms)
        private long lastTime = System.currentTimeMillis(); // 마지막으로 단어를 변경한 시간
        private int wordInterval = 5000; // 5초 간격 (5000ms)
        private List<JLabel> labels = new ArrayList<>(); // 떨어지는 단어들을 관리할 리스트

        public MyPanel(JLabel timeLabel) {
            this.timeLabel = timeLabel;
            setLayout(null);
            setBackground(Color.decode("#222222"));

            startTime = System.currentTimeMillis(); // 게임 시작 시간 설정

            Thread th = new Thread(this);
            th.start();
        }

        public void run() {
            while (true) {
                try {
                    long currentTime = System.currentTimeMillis();
                    long elapsedTime = currentTime - startTime; // 경과 시간 계산

                    // 게임 시간이 60초를 넘었으면 종료
                    if (elapsedTime >= timeLimit) {
                        JOptionPane.showMessageDialog(SubinGame.this, "게임 시간이 종료되었습니다.");
                        System.exit(0); // 게임 종료
                    }

                    // 남은 시간 계산
                    long remainingTime = timeLimit - elapsedTime;
                    long remainingSeconds = remainingTime / 1000; // 남은 초

                    // 남은 시간 텍스트 갱신
                    if (timeLabel != null) {
                        timeLabel.setText("남은 시간: " + remainingSeconds + "초");
                    }

                    // 5초마다 새로운 단어 추가
                    if (currentTime - lastTime >= wordInterval) {
                        addNewWord(); // 새로운 단어 추가
                        lastTime = currentTime; // 시간 갱신
                    }

                    // Iterator 사용하여 리스트 안전하게 순회 및 제거
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
                    Thread.sleep(200); // 200ms 대기
                } catch (InterruptedException e) {
                    return;
                }
            }
        }

        // 새로운 단어를 추가하는 메서드
        private void addNewWord() {
            JLabel newLabel = new JLabel();
            newLabel.setText(v.get((int) (Math.random() * v.size()))); // 랜덤 단어 선택
            newLabel.setFont(new Font("Serif", Font.BOLD, 35));
            newLabel.setSize(400, 50);
            newLabel.setForeground(Color.magenta);
            newLabel.setLocation((int) (Math.random() * getWidth() / 2), 0); // 랜덤 위치
            labels.add(newLabel); // 리스트에 추가
            add(newLabel); // 패널에 추가
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (JLabel label : panel.labels) {
                if (jt.getText().equals(label.getText())) {
                    label.setText(v.get((int) (Math.random() * v.size()))); // 새로운 단어로 변경
                    label.setLocation((int) (Math.random() * panel.getWidth() / 2), 0); // 랜덤 위치
                }
            }

            if (jt.getText().equals("그만")) {
                JOptionPane.showMessageDialog(SubinGame.this, "게임을 종료합니다.");
                System.exit(0); // "그만"을 입력하면 즉시 종료
            }

            jt.setText(""); // 입력 필드 초기화
        }
    }

    public static void main(String[] args) {
        new SubinGame(); // SubinGame 생성
    }
}