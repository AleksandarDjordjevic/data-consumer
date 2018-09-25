public enum ClassAttributeValue {
    HIGH_INTENSITY,
    MEDIUM_INTENSITY,
    LOW_INTENSITY;

    public static ClassAttributeValue getClassAttributeValue(String value) {
        if (value.equals("HIGH_INTENSITY"))
            return HIGH_INTENSITY;
        if (value.equals("MEDIUM_INTENSITY"))
            return MEDIUM_INTENSITY;
        return LOW_INTENSITY;
    }
    }

