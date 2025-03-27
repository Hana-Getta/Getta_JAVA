package records;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class records {
    ArrayList<typingRecord> javascriptRecords = new ArrayList<>();
    ArrayList<typingRecord> pythonRecords = new ArrayList<>();
    ArrayList<typingRecord> javaRecords = new ArrayList<>();
    ArrayList<typingRecord> htmlRecords = new ArrayList<>();

    public void loadRecordsFromJson(String filePath) {
        try {
            // 파일 존재 여부 확인
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("파일이 존재하지 않습니다: " + filePath);
                return; // 메서드 종료
            }

            // JSON 파일 읽기
            String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

            // 파일이 비어있는 경우 처리
            if (jsonContent.isBlank()) {
                System.out.println("파일이 비어있습니다: " + filePath);
                return; // 메서드 종료
            }

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
            System.out.println("파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> parseJsonArray(String json) {
        List<Map<String, Object>> result = new ArrayList<>();

        // JSON 배열을 대략적으로 파싱 (쉼표와 중괄호를 기준으로 처리)
        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1); // 대괄호 제거
            String[] records = json.split("},\\s*\\{"); // 각 객체를 분리

            for (String record : records) {
                record = record.replace("{", "").replace("}", ""); // 중괄호 제거
                String[] fields = record.split(",\\s*"); // 필드 분리

                Map<String, Object> map = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(":");
                    String key = keyValue[0].trim().replace("\"", ""); // 키
                    String value = keyValue[1].trim().replace("\"", ""); // 값

                    // 값의 타입에 따라 처리
                    if (key.equals("cpm") || key.equals("accuracy")) {
                        map.put(key, Double.parseDouble(value));
                    } else {
                        map.put(key, value);
                    }
                }
                result.add(map);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        records rec = new records();

        // JSON 파일에서 데이터 로드
        rec.loadRecordsFromJson("records.txt");

        // 데이터 출력
        rec.showRecords();
    }

    public void showRecords() {
        // JavaScript 순위
        System.out.println("[JavaScript]");
        System.out.println("=====================================");
        if (javascriptRecords.size() == 0) {
            System.out.println("아직 기록이 없습니다.");
        } else {
            for (typingRecord r : javascriptRecords) {
                System.out.println("Name: " + r.getName() + " | cpm: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
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
                System.out.println("Name: " + r.getName() + " | cpm: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
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
                System.out.println("Name: " + r.getName() + " | cpm: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
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
                System.out.println("Name: " + r.getName() + " | cpm: " + r.getCpm() + " | Accuracy: " + r.getAccuracy());
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