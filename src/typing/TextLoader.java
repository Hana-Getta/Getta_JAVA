package typing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextLoader {
    public List<String> loadText(int n) {
        File note = switch (n) {
            case 1 -> new File("JavaScript.txt");
            case 2 -> new File("Python.txt");
            case 3 -> new File("Java.txt");
            case 4 -> new File("HTML.txt");
            default -> throw new IllegalArgumentException("잘못된 번호입니다: " + n);
        };

        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(note))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류 발생: " + e.getMessage());
        }
        return lines;
    }
}
