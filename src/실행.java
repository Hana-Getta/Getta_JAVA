import java.util.Scanner;
import typing.*;

public class 실행 {



    public static final String USER_NAME = "홍길동";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TypingPractice practice = new TypingPractice(USER_NAME);
        int n = scanner.nextInt();
        practice.run(n);
    }
}
