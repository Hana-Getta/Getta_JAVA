package typing;

public class ResultPrinter {
    public void print(TypingResult result) {
        System.out.println("CPM: " + (int) result.getCpm());
        System.out.printf("정확도: %.2f%%\n", result.getAccuracy());

    }
}
