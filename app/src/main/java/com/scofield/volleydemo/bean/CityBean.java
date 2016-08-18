package com.scofield.volleydemo.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author scofield@tronsis.com
 * @date 2016/8/15 18:01
 */
public class CityBean {

    /**
     * code : kyoto
     * country : japan
     * averagePriceDay : 660
     * averagePriceDayWithCar : 880
     * rangePrice : 200-1000
     * seqOrder : 5
     * image : /CITY/BK/21654332
     * totalDeplays : 1
     */

    private String code;
    private String country;
    private double averagePriceDay;
    private double averagePriceDayWithCar;
    private String rangePrice;
    private int seqOrder;
    @SerializedName("")
    private String image;
    private int totalDeplays;

    public String getCode() { return code;}

    public void setCode(String code) { this.code = code;}

    public String getCountry() { return country;}

    public void setCountry(String country) { this.country = country;}

    public double getAveragePriceDay() { return averagePriceDay;}

    public void setAveragePriceDay(double averagePriceDay) { this.averagePriceDay = averagePriceDay;}

    public double getAveragePriceDayWithCar() { return averagePriceDayWithCar;}

    public void setAveragePriceDayWithCar(double averagePriceDayWithCar) { this.averagePriceDayWithCar = averagePriceDayWithCar;}

    public String getRangePrice() { return rangePrice;}

    public void setRangePrice(String rangePrice) { this.rangePrice = rangePrice;}

    public int getSeqOrder() { return seqOrder;}

    public void setSeqOrder(int seqOrder) { this.seqOrder = seqOrder;}

    public String getImage() { return image;}

    public void setImage(String image) { this.image = image;}

    public int getTotalDeplays() { return totalDeplays;}

    public void setTotalDeplays(int totalDeplays) { this.totalDeplays = totalDeplays;}
}
