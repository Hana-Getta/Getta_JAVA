package typing;

public class TypingEvaluator {
    public void evaluate(TypingResult result) {
        double durationInMinutes = (result.getEndTime() - result.getStartTime()) / 1000.0 / 60.0;
        double cpm = result.getTotalChars() / durationInMinutes;
        double accuracy = ((result.getTotalChars() - result.getErrorCount()) / result.getTotalChars()) * 100;

        result.setCpm(cpm);
        result.setAccuracy(accuracy);
    }
}
