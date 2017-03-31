package pojo.convert;

public abstract class Conversions {
    private String conversionType;
    private String conversionTypeTwo;

    String getConversionTypeTwo() {
        return conversionTypeTwo;
    }

    public void setConversionTypeTwo(String conversionTypeTwo) {
        this.conversionTypeTwo = conversionTypeTwo;
    }

    String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

     String appendType(String inputStr) {
        StringBuilder sb = new StringBuilder(inputStr);
         sb.append(" ");
         sb.append(conversionTypeTwo);
        return (sb.toString());
    }

    public abstract String convert(double valueIn);
}
