package typing;

import java.util.List;

public class TypingPractice {
    private final TextLoader textLoader = new TextLoader();
    private final InputHandler inputHandler = new InputHandler();
    private final TypingEvaluator evaluator = new TypingEvaluator();
    private final ResultPrinter printer = new ResultPrinter();

    public void run(int n) {
        List<String> lines = textLoader.loadText(n);
        TypingResult result = inputHandler.startTyping(lines);
        evaluator.evaluate(result);
        printer.print(result);
    }
}