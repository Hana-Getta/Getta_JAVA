package records;

class typingRecord {
    private String name;
    private int cpm;
    private String accuracy;

    public typingRecord(String name, int cpm, String accuracy) {
        this.name = name;
        this.cpm = cpm;
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public int getCpm() {
        return cpm;
    }

    public String getAccuracy() {
        return accuracy;
    }
}
