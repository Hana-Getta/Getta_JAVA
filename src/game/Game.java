package game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class SubinGame extends JFrame {
    private final String user_name;

    private MyPanel panel = new MyPanel();
    private JPanel jp;
    private JTextField jt;
    private final String[] words = {
            "${title}", "$(el)", "<%=name%>", "@log", "@Test", "x->x", "a=>b",
            "std::cin", "KClass::name", "%%time", "$PATH", "*ptr", "&var", "a&&b",
            "x||y", "n:=10", "x!=y", "a!==b", "x<=y", "x>=y", "val??0", "obj?.key",
            "\"\\n\"", "\"\\t\"", "r\"\\w+\"", "/abc/", "x^y", "r\"end$\"", "$(VAR)", "<%=id%>"
    };
    private final Vector<String> v = new Vector<>(Arrays.asList(words));

    private int score = 0; // 초기 점수 설정
    private JLabel timeAndScoreLabel; // 시간과 점수를 보여주는 레이블

    public SubinGame(String name) {
        this.user_name = name;

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

        // 상단에 점수와 시간을 표시하는 레이블 추가
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#222222"));

        timeAndScoreLabel = new JLabel("남은 시간: 60초  |  점수: 0", JLabel.CENTER);
        timeAndScoreLabel.setFont(new Font("Serif", Font.BOLD, 24));
        timeAndScoreLabel.setForeground(Color.white);
        topPanel.add(timeAndScoreLabel, BorderLayout.CENTER);

        c.add(topPanel, BorderLayout.NORTH); // 상단 패널을 북쪽에 추가

        jp = new JPanel();
        Border topBorder = BorderFactory.createMatteBorder(2, 0, 0, 0, Color.decode("#cccccc"));
        jp.setBorder(BorderFactory.createCompoundBorder(topBorder, BorderFactory.createEmptyBorder(10, 0, 20, 0)));
        jp.setBackground(Color.decode("#222222"));
        jp.add(jt);

        c.add(jp, BorderLayout.SOUTH);
        c.add(panel, BorderLayout.CENTER);

        setVisible(true);

        //처음 시작하고 바로 단어 떨어질 수 있게 수정
        SwingUtilities.invokeLater(() -> {
            panel.addNewWord();
            revalidate();
            repaint();
        });
    }

    class MyPanel extends JPanel implements Runnable {
        private long lastTime = System.currentTimeMillis(); // 마지막으로 단어를 변경한 시간
        private final long timeLimit = 30000; // 게임 시간 30초
        private long startTime;
        private int wordInterval = 5000; // 5초 간격 (5000ms)
        private List<JLabel> labels = new ArrayList<>(); // 떨어지는 단어들을 관리할 리스트

        public MyPanel() {
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
                    long elapsedTime = currentTime - startTime; // 경과 시간 계산

                    // 게임 시간이 30초를 넘었으면 종료
                    if (elapsedTime >= timeLimit) {
                        saveGameRecord();
                        JOptionPane.showMessageDialog(SubinGame.this, "게임 시간이 종료되었습니다.");
                        synchronized (SubinGame.this) {
                            SubinGame.this.notify();
                        }

                        dispose();
                        return;
                    }

                    // 남은 시간 계산
                    long remainingTime = timeLimit - elapsedTime;
                    long remainingSeconds = remainingTime / 1000; // 남은 초

                    // 남은 시간과 점수 텍스트 갱신
                    if (timeAndScoreLabel != null) {
                        timeAndScoreLabel.setText("남은 시간: " + remainingSeconds + "초 | 점수: " + score);
                    }

                    // 5초마다 새로운 단어 추가
                    if (currentTime - lastTime >= wordInterval) {
                        addNewWord(); // 새로운 단어 추가
                        lastTime = currentTime; // 시간 갱신
                    }

                    Iterator<JLabel> iterator = labels.iterator();
                    while (iterator.hasNext()) {
                        JLabel label = iterator.next();
                        Point p = label.getLocation();
                        label.setLocation(p.x, p.y + 10); // 단어 이동

                        // 단어가 화면 밖으로 나가면 리스트에서 제거
                        if (p.y > getHeight() - 15) {
                            iterator.remove(); // 안전하게 제거
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
            newLabel.setForeground(Color.decode("#cccccc"));
            newLabel.setLocation((int) (Math.random() * getWidth() / 2), 0); // 랜덤 위치
            labels.add(newLabel); // 리스트에 추가
            add(newLabel); // 패널에 추가
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputText = jt.getText().trim(); // 사용자 입력 텍스트에서 공백 제거

            // 입력된 텍스트와 레이블의 텍스트가 일치하는지 비교
            JLabel matchedLabel = null;
            int maxY = Integer.MIN_VALUE; // 가장 밑에 있는 단어의 y 좌표를 찾기 위한 변수

            for (JLabel label : panel.labels) {
                if (inputText.equals(label.getText()) && label.getLocation().y > maxY) {
                    // y 좌표가 가장 큰 레이블을 찾음
                    matchedLabel = label;
                    maxY = label.getLocation().y;
                }
            }

            // 일치하는 단어가 있으면
            if (matchedLabel != null) {
                // 단어 맞추면 바로 새로운 단어 생성되게 하는 기능
                matchedLabel.setText(v.get((int) (Math.random() * v.size()))); // 새로운 단어로 변경
                matchedLabel.setLocation((int) (Math.random() * panel.getWidth() / 2), 0); // 새로운 랜덤 위치로 설정

                // 맞춘 단어가 있으면 점수 10점 추가
                score += 10;

                /*
                // 일치하는 단어를 화면에서 제거
                panel.labels.remove(matchedLabel); // 리스트에서 제거
                panel.remove(matchedLabel); // 화면에서 제거

                // 화면을 다시 그려서 변경사항 반영
                panel.revalidate();
                panel.repaint();
                 */
            }


            jt.setText(""); // 입력 필드 초기화
        }
    }

    private void saveGameRecord() {
        String fileName = "game_records.txt";
        List<String> records = new ArrayList<>();

        // 기존 파일이 있으면 읽어오기
        File file = new File(fileName);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    records.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 현재 플레이어 기록 추가
        records.add(user_name + ":" + score);

        // 정렬 (점수 내림차순, 점수가 같다면 이름 오름차순)
        records.sort((a, b) -> {
            String[] entryA = a.split(":");
            String[] entryB = b.split(":");

            int scoreA = Integer.parseInt(entryA[1]);
            int scoreB = Integer.parseInt(entryB[1]);

            if (scoreA != scoreB) {
                return Integer.compare(scoreB, scoreA); // 점수 내림차순
            }
            return entryA[0].compareTo(entryB[0]); // 이름 오름차순
        });

        // 최대 5개까지만 유지
        if (records.size() > 5) {
            records = records.subList(0, 5);
        }

        // 정렬된 기록을 다시 파일에 저장
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String record : records) {
                bw.write(record);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 콘솔에 현재 사용자 기록과 Top 5 출력
        System.out.printf("%s 님의 기록: %d\n", user_name, score);
        System.out.println("\uD83D\uDC51 Top 5 기록");

        // Top 5 기록 출력
        for (String record : records) {
            System.out.println(record);
        }
    }
}