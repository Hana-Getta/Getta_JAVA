package typing;

import java.awt.font.TextHitInfo;
import java.util.List;

public class TypingPractice {
    private final TextLoader textLoader = new TextLoader();
    private final InputHandler inputHandler = new InputHandler();
    private final TypingEvaluator evaluator = new TypingEvaluator();
    private final ResultPrinter printer = new ResultPrinter();
    private final ResultRecorder recoder = new ResultRecorder();
    private final String userName;



    public TypingPractice(String userName) {
        this.userName = userName;
    }


    public void run(int n) {
        List<String> lines = textLoader.loadText(n);
        TypingResult result = inputHandler.startTyping(lines);
        evaluator.evaluate(result);
        printer.print(result);
        recoder.record(result,n,userName);
    }
}