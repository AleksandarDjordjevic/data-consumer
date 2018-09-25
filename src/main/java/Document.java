public class Document {
    private String X101_heartRate_1;
    private String corr1819;
    private String corr1920;
    private String corr1820;
    private String X101_handTemp_3;
    private ClassAttributeValue expectedClassAttribute;
    private ClassAttributeValue actualClassAttribute;


    public Document(String X101_heartRate_1, String corr1819, String corr1820, String corr1920, String X101_handTemp_3, String classAttribute) {
        this.X101_heartRate_1 = X101_heartRate_1;
        this.corr1819 = corr1819;
        this.corr1820 = corr1820;
        this.corr1920 = corr1920;
        this.X101_handTemp_3 = X101_handTemp_3;

        this.expectedClassAttribute = ClassAttributeValue.getClassAttributeValue(classAttribute);
    }

    public String getX101_heartRate_1() {
        return X101_heartRate_1;
    }

    public void setX101_heartRate_1(String x101_heartRate_1) {
        X101_heartRate_1 = x101_heartRate_1;
    }

    public String getCorr1819() {
        return corr1819;
    }

    public void setCorr1819(String corr1819) {
        this.corr1819 = corr1819;
    }

    public String getCorr1920() {
        return corr1920;
    }

    public void setCorr1920(String corr1920) {
        this.corr1920 = corr1920;
    }

    public String getCorr1820() {
        return corr1820;
    }

    public void setCorr1820(String corr1820) {
        this.corr1820 = corr1820;
    }

    public String getX101_handTemp_3() {
        return X101_handTemp_3;
    }

    public void setX101_handTemp_3(String x101_handTemp_3) {
        X101_handTemp_3 = x101_handTemp_3;
    }

    public ClassAttributeValue getExpectedClassAttribute() {
        return expectedClassAttribute;
    }

    public void setExpectedClassAttribute(String expectedClassAttribute) {
        this.expectedClassAttribute = ClassAttributeValue.valueOf(expectedClassAttribute);
    }

    public ClassAttributeValue getActualClassAttribute() {
        return this.actualClassAttribute;
    }

    public void setActualClassAttribute(String actualClassAttribute) {
        this.actualClassAttribute = ClassAttributeValue.getClassAttributeValue(actualClassAttribute);
    }
}
