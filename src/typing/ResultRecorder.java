package typing;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class ResultRecorder {
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

        String json = String.format("{\"userName\":\"%s\",\"type\":\"%s\",\"cpm\":%.2f,\"accuracy\":%.2f}",
            userName, type, cpm, accuracy);

        try (PrintWriter out = new PrintWriter(new FileWriter("records.txt", true))) {
            out.println(json);
        } catch (IOException e) {
            System.out.println("결과 저장 중 오류 발생: " + e.getMessage());
        }
    }
}