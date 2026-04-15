package dev.mr3.sb.model;

public enum Weekday {
    SUNDAY,
    TUESDAY,
    THURSDAY;

    @Override
    public String toString() {
        String lower = name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}

