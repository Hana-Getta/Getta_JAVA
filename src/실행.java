import java.util.Scanner;
import typing.*;

public class 실행 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TypingPractice practice = new TypingPractice();
        int n = scanner.nextInt();
        practice.run(n);
    }
}
