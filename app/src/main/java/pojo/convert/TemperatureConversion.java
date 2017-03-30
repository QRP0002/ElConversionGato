package pojo.convert;

import pojo.convert.Conversions;

public class TemperatureConversion extends Conversions {
    @Override
    public String convert(double valueIn) {
        return(super.getConversionType().equals("Fahr.") ?
                Double.toString(((valueIn - 32) * 5) / 9) + " \u2103" :
                Double.toString(((valueIn * 9) / 5) + 32) + " \u2109");
    }
}
