package typing;

import java.awt.font.TextHitInfo;
import java.util.List;
import java.util.Scanner;

public class TypingPractice {
    Scanner scan = new Scanner(System.in);
    private final TextLoader textLoader = new TextLoader();
    private final InputHandler inputHandler = new InputHandler();
    private final TypingEvaluator evaluator = new TypingEvaluator();
    private final ResultPrinter printer = new ResultPrinter();
    private final ResultRecorder recoder = new ResultRecorder();
    private final String userName;



    public TypingPractice(String userName) {
        this.userName = userName;
    }


    public void run() {
        System.out.println("===================================");
        System.out.println("        🎮 연습할 언어 선택 🎮");
        System.out.println("===================================");
        System.out.println("  1️⃣  JavaScript");
        System.out.println("  2️⃣  Python");
        System.out.println("  3️⃣  Java");
        System.out.println("  4️⃣  HTML");
        System.out.println("===================================");
        int choiceLanguage = scan.nextInt();
        List<String> lines = textLoader.loadText(choiceLanguage);
        TypingResult result = inputHandler.startTyping(lines);
        evaluator.evaluate(result);
        printer.print(result);
        recoder.record(result,choiceLanguage,userName);
    }
}
