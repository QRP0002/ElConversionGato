package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ConversionItem {
    @SerializedName("convert_id")
    @Expose
    private String convertId;
    @SerializedName("convert_type")
    @Expose
    private String convertType;
    @SerializedName("convert_from")
    @Expose
    private String convertFrom;
    @SerializedName("convert_to")
    @Expose
    private String convertTo;
    @SerializedName("convert_formula")
    @Expose
    private String convertFormula;
    @SerializedName("convert_value")
    @Expose
    private String convertValue;

    public String getConvertId() {
        return convertId;
    }

    public void setConvertId(String convertId) {
        this.convertId = convertId;
    }

    public String getConvertType() {
        return convertType;
    }

    public void setConvertType(String convertType) {
        this.convertType = convertType;
    }

    public String getConvertFrom() {
        return convertFrom;
    }

    public void setConvertFrom(String convertFrom) {
        this.convertFrom = convertFrom;
    }

    public String getConvertTo() {
        return convertTo;
    }

    public void setConvertTo(String convertTo) {
        this.convertTo = convertTo;
    }

    public String getConvertFormula() {
        return convertFormula;
    }

    public void setConvertFormula(String convertFormula) {
        this.convertFormula = convertFormula;
    }

    public String getConvertValue() {
        return convertValue;
    }

    public void setConvertValue(String convertValue) {
        this.convertValue = convertValue;
    }

    @Override
    public String toString() {
        return (convertId + "-" + convertType + "-" + convertFrom
                + "-" + convertTo + "-" + convertFormula + "-" + convertValue);
    }
}
