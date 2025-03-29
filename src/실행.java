import java.io.FileNotFoundException;
import java.util.Scanner;

import game.Game;
import records.Records;
import typing.*;

public class 실행 {
    public static String USER_NAME;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("👉 이름을 입력하세요: ");
        USER_NAME = scanner.next();

        while (true) {
            printMenu();
            System.out.print("👉 원하는 메뉴를 선택하세요: ");
            int choice = getUserChoice(scanner);
            switch (choice) {
                case 1 -> {
                    TypingPractice practice = new TypingPractice(USER_NAME);
                    System.out.println("📝 Typing 모드로 이동합니다...");
                    practice.run(USER_NAME);
                }
                case 2 -> {
                    System.out.println("🎮 Game 모드로 이동합니다...");
                    Game game = new Game(USER_NAME);

                    // 게임이 끝날 때까지 대기
                    synchronized (game) {
                        try {
                            game.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                case 3 -> {
                    System.out.println("📜 Record 화면으로 이동합니다...");
                    Records records = new Records();
                    records.loadAndShowRecords("records.txt");
                }
                case 4 -> {
                    System.out.println("👋 게임을 종료합니다. 감사합니다!");
                    scanner.close();
                    return;
                }
                default -> {
                    System.out.println("❌ 잘못된 입력입니다. 다시 선택해주세요. ❌");
                }
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("===========================================");
        System.out.println("               🎮 MENU 🎮");
        System.out.println("===========================================");
        System.out.println("              1️⃣  Typing");
        System.out.println("              2️⃣  Game");
        System.out.println("              3️⃣  Record");
        System.out.println("              4️⃣  Exit");
        System.out.println("===========================================");
    }

    // 사용자 입력을 받아 유효한 값인지 확인하는 함수
    private static int getUserChoice(Scanner scanner) {
        int choice = -1; // 기본적으로 잘못된 선택으로 초기화

        // 숫자 입력이 될 때까지 반복
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= 4) {
                    break; // 유효한 입력이면 반복 종료
                } else {
                    System.out.println("❌ 선택한 번호는 유효하지 않습니다.");
                }
            } else {
                System.out.println("❌ 잘못된 입력입니다. 숫자만 입력 가능합니다.");
                scanner.next(); // 잘못된 입력 제거
            }
        }
        return choice;
    }
}
