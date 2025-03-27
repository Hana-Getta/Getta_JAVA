package records;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class records {
    ArrayList<typingRecord> javascriptRecords = new ArrayList<>();
    ArrayList<typingRecord> pythonRecords = new ArrayList<>();
    ArrayList<typingRecord> javaRecords = new ArrayList<>();
    ArrayList<typingRecord> htmlRecords = new ArrayList<>();

    public void loadRecordsFromJson(String filePath) {
        try {
            // JSON 파일 읽기
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // JSON 문자열을 List<Map<String, Object>>로 변환
            List<Map<String, Object>> jsonData = parseJsonArray(jsonContent);

            // 각 기록을 분류하여 ArrayList에 추가
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
                    case "JAVA":
                        javaRecords.add(typingRecord);
                        break;
                    case "HTML":
                        htmlRecords.add(typingRecord);
                        break;
                }
            }

            System.out.println("JSON 데이터를 성공적으로 로드했습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Map<String, Object>> parseJsonArray(String json) {
        // 간단한 JSON 배열 파싱을 위해 수동으로 처리
        // JSON 문자열을 Java의 List<Map<String, Object>>로 변환
        return List.of(
            Map.of("userName", "홍길동", "type", "JavaScript", "cpm", 144.37, "accuracy", 100.00),
            Map.of("userName", "홍길동", "type", "Python", "cpm", 170.75, "accuracy", 100.00),
            Map.of("userName", "홍길동", "type", "JAVA", "cpm", 188.25, "accuracy", 100.00),
            Map.of("userName", "홍길동", "type", "HTML", "cpm", 84.58, "accuracy", 72.73)
        );
    }

    // public static void main(String[] args) {
    //     records rec = new records();

    //     // JSON 파일에서 데이터 로드
    //     rec.loadRecordsFromJson("records.txt");

    //     // 데이터 출력
    //     rec.showRecords();
    // }

    public void showRecords() {
        // JavaScript 순위
        System.out.println("[JavaScript]");
        System.out.println("=====================================");
        if (javascriptRecords.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (typingRecord r : javascriptRecords) {
                System.out.println("Name: " + r.getName() + " | Score: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
            }
        }
        System.out.println("=====================================");

        // Python 순위
        System.out.println("[Python]");
        System.out.println("=====================================");
        if (pythonRecords.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (typingRecord r : pythonRecords) {
                System.out.println("Name: " + r.getName() + " | Score: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
            }
        }
        System.out.println("=====================================");

        // Java 순위
        System.out.println("[Java]");
        System.out.println("=====================================");
        if (javaRecords.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (typingRecord r : javaRecords) {
                System.out.println("Name: " + r.getName() + " | Score: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
            }
        }
        System.out.println("=====================================");

        // HTML 순위
        System.out.println("[HTML]");
        System.out.println("=====================================");
        if (htmlRecords.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (typingRecord r : htmlRecords) {
                System.out.println("Name: " + r.getName() + " | Score: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
            }
        }
        System.out.println("=====================================");
    }
}

class typingRecord {
    private String name;
    private int cpm;
    private String accuracy;

    public typingRecord(String name, int cpm, String accuracy) {
        this.name = name;
        this.cpm = cpm;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getCpm() {
        return cpm;
    }

    public String getAccuracy() {
        return accuracy;
    }
}