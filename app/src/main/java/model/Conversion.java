package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Conversion {
    @SerializedName("conversion_items")
    @Expose
    private List<ConversionItem> conversionItems = null;

    public List<ConversionItem> getConversionItems() {
        return conversionItems;
    }

    public void setConversionItems(List<ConversionItem> conversionItems) {
        this.conversionItems = conversionItems;
    }

    public int listSize() {
        return conversionItems.size();
    }

    @Override
    public String toString(){
        return conversionItems.toString();
    }
}
