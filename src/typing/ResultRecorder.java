package typing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ResultRecorder {
    private static final String FILE_NAME = "records.txt";

    public void record(TypingResult result, int n, String userName) {
        String type = switch (n) {
            case 1 -> "JavaScript";
            case 2 -> "Python";
            case 3 -> "JAVA";
            case 4 -> "HTML";
            default -> "Unknown";
        };

        double cpm = result.getCpm();
        double accuracy = result.getAccuracy();

        String newRecord = String.format("{\"type\":\"%s\",\"userName\":\"%s\",\"cpm\":%.2f,\"accuracy\":%.2f}",
            type, userName, cpm, accuracy);

        List<String> records = new ArrayList<>();

        // 기존 records 읽기
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        records.add(line);
                    }
                }
            } catch (IOException e) {
                System.out.println("기존 기록 불러오기 실패: " + e.getMessage());
            }
        }

        records.add(newRecord);

        // JSON 배열로 저장
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("[");
            for (int i = 0; i < records.size(); i++) {
                writer.print("  " + records.get(i));
                if (i < records.size() - 1) writer.println(",");
                else writer.println();
            }
            writer.println("]");
        } catch (IOException e) {
            System.out.println("결과 저장 중 오류 발생: " + e.getMessage());
        }
    }
}