package typing;

public class TypingResult {
    private double totalChars;
    private double errorCount;
    private long startTime;
    private long endTime;
    private double cpm;
    private double accuracy;

    public double getTotalChars() { return totalChars; }
    public void setTotalChars(double totalChars) { this.totalChars = totalChars; }

    public double getErrorCount() { return errorCount; }
    public void setErrorCount(double errorCount) { this.errorCount = errorCount; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public double getCpm() { return cpm; }
    public void setCpm(double cpm) { this.cpm = cpm; }

    public double getAccuracy() { return accuracy; }
    public void setAccuracy(double accuracy) { this.accuracy = accuracy; }
}
