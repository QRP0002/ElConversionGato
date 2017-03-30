package pojo.convert;

import pojo.convert.Conversions;

public class SpeedConversion extends Conversions {
    @Override
    public String convert(double speedIn) {
        return(super.getConversionType().equals("mph") ?
                (Double.toString(speedIn * 1.609344) + " KPH")
                : (Double.toString(speedIn / 1.609344) + " MPH"));
    }
}
