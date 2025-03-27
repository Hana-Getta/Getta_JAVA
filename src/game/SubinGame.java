package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SubinGame extends JFrame {
    private MyPanel panel = new MyPanel();
    private JPanel jp;
    private JTextField jt;
    private JLabel jl1, jl2;
    private String[] words = {
            "${title}", "$(el)", "<%=name%>", "@log", "@Test", "x->x", "a=>b",
            "std::cin", "KClass::name", "%%time", "$PATH", "*ptr", "&var", "a&&b",
            "x||y", "n:=10", "x!=y", "a!==b", "x<=y", "x>=y", "val??0", "obj?.key",
            "\"\\n\"", "\"\\t\"", "r\"\\w+\"", "/abc/", "x^y", "r\"end$\"", "$(VAR)", "<%=id%>"
    };
    private Vector<String> v = new Vector<String>(Arrays.asList(words));

    public SubinGame() {
        setTitle("산성비 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setExtendedState(JFrame.MAXIMIZED_BOTH); // 창을 화면 크기에 맞게 최대화
        setLocationRelativeTo(null); // 화면 중앙에 위치하도록 설정

        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        jt = new JTextField(10);
        jt.addActionListener(new MyActionListener());
        jp = new JPanel();
        jp.setBackground(Color.LIGHT_GRAY);
        jp.add(jt);
        c.add(jp, BorderLayout.SOUTH);
        c.add(panel, BorderLayout.CENTER);

        // 랜덤 단어 설정
        jl2.setText(v.get((int) (Math.random() * v.size()))); // 벡터의 크기 사용

        setSize(300, 400);
        setVisible(true);
    }

    class MyPanel extends JPanel implements Runnable {
        public MyPanel() {
            setLayout(null);
            jl1 = new JLabel();
            jl1.setFont(new Font("Serif", Font.BOLD, 18));
            jl1.setSize(120, 20);
            jl1.setLocation(10, 10);
            add(jl1);

            jl2 = new JLabel();
            jl2.setFont(new Font("Serif", Font.BOLD, 35));
            jl2.setSize(400, 50);
            jl2.setForeground(Color.magenta);
            jl2.setLocation((int) (Math.random() * 400 / 2), 0);
            add(jl2);

            Thread th = new Thread(this);
            th.start();
        }

        public void run() {
            while (true) {
                try {
                    Point p = jl2.getLocation();
                    jl2.setLocation(p.x, p.y + 10); // 단어 이동

                    // 15px 이상 내려갔을 때
                    if (p.y > getHeight() - 15) {
                        jl1.setText("시간초과 실패");
                        jl2.setText(v.get((int) (Math.random() * v.size()))); // 새로운 단어 생성
                        jl2.setLocation((int) (Math.random() * getWidth() / 2), 0); // 랜덤 위치
                    }

                    Thread.sleep(200); // 200ms 대기
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (jt.getText().equals(jl2.getText())) {
                jl1.setText("성공");
                jl2.setText(v.get((int) (Math.random() * v.size()))); // 새로운 단어
                jl2.setLocation((int) (Math.random() * panel.getWidth() / 2), 0); // 랜덤 위치
            }
            if (jt.getText().equals("그만")) {
                System.exit(0); // 게임 종료
            }
            jt.setText(""); // 입력 필드 초기화
        }
    }

    public static void main(String[] args) {
        new SubinGame(); // SubinGame 생성
    }
}