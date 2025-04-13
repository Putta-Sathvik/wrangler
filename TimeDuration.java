package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long milliseconds;

    public TimeDuration(String value) {
        super(value);
        this.milliseconds = parseMilliseconds(value);
    }

    private long parseMilliseconds(String value) {
        String unit = value.replaceAll("[0-9.]", "").toLowerCase();
        double number = Double.parseDouble(value.replaceAll("[^0-9.]", ""));
        switch (unit) {
            case "s":
            case "sec":
            case "seconds":
                return (long)(number * 1000);
            default:
                return (long)number;
        }
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}
