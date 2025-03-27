package typing;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner = new Scanner(System.in);

    public TypingResult startTyping(List<String> lines) {
        long startTime = System.currentTimeMillis();
        double totalChars = 0;
        double errorCount = 0;

        for (String line : lines) {
            System.out.println(line);
            String userInput = scanner.nextLine();
            double lineLength = line.length();
            totalChars += lineLength;

            for (int i = 0; i < Math.min(line.length(), userInput.length()); i++) {
                if (line.charAt(i) != userInput.charAt(i)) {
                    errorCount++;
                }
            }
            if (userInput.length() < line.length()) {
                errorCount += (line.length() - userInput.length());
            }
        }

        long endTime = System.currentTimeMillis();

        TypingResult result = new TypingResult();
        result.setTotalChars(totalChars);
        result.setErrorCount(errorCount);
        result.setStartTime(startTime);
        result.setEndTime(endTime);

        return result;
    }
}