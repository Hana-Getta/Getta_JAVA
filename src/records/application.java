package records;

public class application {
    public static void main(String[] args) {
        records rec = new records();

        // JSON 파일에서 데이터 로드
        rec.loadRecordsFromJson("records.txt");

        // 데이터 출력
        rec.showRecords();
    }
}
