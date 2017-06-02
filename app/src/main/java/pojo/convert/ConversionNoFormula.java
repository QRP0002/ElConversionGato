package pojo.convert;

public class ConversionNoFormula extends Conversions {
    private double dataBaseValue;

    public ConversionNoFormula(double dataBaseValue) {
        this.dataBaseValue = dataBaseValue;
    }

    @Override
    public String convert(double valueIn) {
        return(super.appendType(Double.toString(calculate(valueIn))));
    }

    private double calculate(double valueIn) {
        return valueIn * dataBaseValue;
    }
}