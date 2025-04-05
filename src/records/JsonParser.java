package records;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParser {
    public List<Map<String, Object>> parseJsonArray(String json) {
        List<Map<String, Object>> result = new ArrayList<>();

        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1);
            String[] records = json.split("},\\s*\\{");

            for (String record : records) {
                record = record.replace("{", "").replace("}", "");
                String[] fields = record.split(",\\s*");

                Map<String, Object> map = new HashMap<>();
                for (String field : fields) {
                    String[] keyValue = field.split(":");
                    String key = keyValue[0].trim().replace("\"", "");
                    String value = keyValue[1].trim().replace("\"", "");

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
}