package com.novaraspace.model.dto.booking;

import com.novaraspace.model.enums.BaggageCapacity;
import com.novaraspace.model.enums.ExtraServiceCode;

import java.util.Map;

public class ServicesPricingDTO {

    private Map<ExtraServiceCode, Double> servicesPrices;
    private Map<BaggageCapacity, Double> baggagePrices;

    public Map<ExtraServiceCode, Double> getServicesPrices() {
        return servicesPrices;
    }

    public ServicesPricingDTO setServicesPrices(Map<ExtraServiceCode, Double> servicesPrices) {
        this.servicesPrices = servicesPrices;
        return this;
    }

    public Map<BaggageCapacity, Double> getBaggagePrices() {
        return baggagePrices;
    }

    public ServicesPricingDTO setBaggagePrices(Map<BaggageCapacity, Double> baggagePrices) {
        this.baggagePrices = baggagePrices;
        return this;
    }
}
