package dev.mr3.sb.model;

public enum BodyPart {
    THIGH,
    HAMSTRING,
    CALF,
    ANKLE,
    KNEE,
    FOOT,
    SHIN,
    ARM,
    LEG,
    WRIST,
    SHOULDER,
    ELBOW,
    ACHILLES;

    @Override
    public String toString() {
        String lower = name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}