package com.novaraspace.model.other;

public class ServicePricesJSON {
    private String service;
    private double lowSeason;
    private double basePrice;
    private double highSeason;

    public String getService() {
        return service;
    }

    public ServicePricesJSON setService(String service) {
        this.service = service;
        return this;
    }

    public double getLowSeason() {
        return lowSeason;
    }

    public ServicePricesJSON setLowSeason(double lowSeason) {
        this.lowSeason = lowSeason;
        return this;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public ServicePricesJSON setBasePrice(double basePrice) {
        this.basePrice = basePrice;
        return this;
    }

    public double getHighSeason() {
        return highSeason;
    }

    public ServicePricesJSON setHighSeason(double highSeason) {
        this.highSeason = highSeason;
        return this;
    }
}
