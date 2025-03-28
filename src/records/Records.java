package records;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Records {
    ArrayList<typingRecord> javascriptRecords = new ArrayList<>();
    ArrayList<typingRecord> pythonRecords = new ArrayList<>();
    ArrayList<typingRecord> javaRecords = new ArrayList<>();
    ArrayList<typingRecord> htmlRecords = new ArrayList<>();

    public void loadAndShowRecords(String filePath) {
        loadRecordsFromJson(filePath);
        showRecords();
    }

    private void loadRecordsFromJson(String filePath) {
        try {
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("파일이 존재하지 않습니다: " + filePath);
                return;
            }

            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

            if (jsonContent.isBlank()) {
                System.out.println("파일이 비어있습니다: " + filePath);
                return;
            }

            JsonParser parser = new JsonParser();
            List<Map<String, Object>> jsonData = parser.parseJsonArray(jsonContent);

            for (Map<String, Object> record : jsonData) {
                String type = (String) record.get("type");
                String name = (String) record.get("userName");
                double cpm = (double) record.get("cpm");
                double accuracy = (double) record.get("accuracy");

                typingRecord typingRecord = new typingRecord(name, (int) cpm, String.format("%.2f%%", accuracy));

                switch (type) {
                    case "JavaScript":
                        javascriptRecords.add(typingRecord);
                        break;
                    case "Python":
                        pythonRecords.add(typingRecord);
                        break;
                    case "Java":
                        javaRecords.add(typingRecord);
                        break;
                    case "HTML":
                        htmlRecords.add(typingRecord);
                        break;
                }
            }

            System.out.println("JSON 데이터를 성공적으로 로드했습니다.");
        } catch (IOException e) {
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void showRecords() {
        Comparator<typingRecord> comparator = (r1, r2) -> {
            double score1 = r1.getCpm() * 0.1 + Double.parseDouble(r1.getAccuracy().replace("%", ""));
            double score2 = r2.getCpm() * 0.1 + Double.parseDouble(r2.getAccuracy().replace("%", ""));
            return Double.compare(score2, score1); // 내림차순 정렬
        };

        javascriptRecords.sort(comparator);
        pythonRecords.sort(comparator);
        javaRecords.sort(comparator);
        htmlRecords.sort(comparator);

        printTopRecords("JavaScript", javascriptRecords);
        printTopRecords("Python", pythonRecords);
        printTopRecords("Java", javaRecords);
        printTopRecords("HTML", htmlRecords);
    }

    private void printTopRecords(String language, ArrayList<typingRecord> records) {
        System.out.println("[" + language + "]");
        System.out.println("=====================================");
        if (records.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (int i = 0; i < Math.min(records.size(), 5); i++) {
                typingRecord r = records.get(i);
                System.out.println("Name: " + r.getName() + " | cpm: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
            }
        }
        System.out.println("=====================================");
    }
}
