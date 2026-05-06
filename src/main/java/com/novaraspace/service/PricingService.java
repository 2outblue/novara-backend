package com.novaraspace.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.dto.booking.ServicesPricingDTO;
import com.novaraspace.model.enums.BaggageCapacity;
import com.novaraspace.model.enums.ExtraServiceCode;
import com.novaraspace.model.other.ServicePricesJSON;
import com.novaraspace.util.DoublePricesUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PricingService {

    private final ObjectMapper objectMapper;

    private Map<BaggageCapacity, Double> baggageStaticMultipliers = new LinkedHashMap<>();
    private Map<ExtraServiceCode, ServicePricesJSON> serviceStaticPrices;

    public PricingService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ServicesPricingDTO getServiceOfferForNewBooking(int paxCount) {
        Map<ExtraServiceCode, Double> servicesPrices = simulateDynamicServicePrices(paxCount);

        Double baggageBasePrice = servicesPrices.get(ExtraServiceCode.baggage);
        if (baggageBasePrice == null) {
            return new ServicesPricingDTO().setServicesPrices(servicesPrices);
        }

        if (paxCount < 2) {
            servicesPrices.remove(ExtraServiceCode.shared);
        }

        Map<BaggageCapacity, Double> baggagePrices = baggageStaticMultipliers.entrySet()
                .stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
                    double multiplier = e.getValue();
                    return DoublePricesUtil.normalizePrice(baggageBasePrice * multiplier);
                },(v1, v2) -> v1, LinkedHashMap<BaggageCapacity, Double>::new));

        return new ServicesPricingDTO()
                .setServicesPrices(servicesPrices)
                .setBaggagePrices(baggagePrices);
    }


    private Map<ExtraServiceCode, Double> simulateDynamicServicePrices(int paxCount) {
        return serviceStaticPrices.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry ->  {
                            double price = addRandomDeviation(entry.getValue().getBasePrice());
                            if (entry.getKey().equals(ExtraServiceCode.priority)
                            || entry.getKey().equals(ExtraServiceCode.insurance)) {
                                price = DoublePricesUtil.normalizePrice(price * paxCount);
                            }
                            return price;
                        },(v1, v2) -> v1, LinkedHashMap<ExtraServiceCode, Double>::new));
    }

    private double addRandomDeviation(double value) {
        int deviation = ThreadLocalRandom.current().nextInt(-10, 11) * 10;
        return value + deviation;
    }

    @PostConstruct
    private void initServicePrices() throws IOException {
        ClassPathResource servicePricesPath = new ClassPathResource("data/ExtraServicePrices.json");
        ClassPathResource baggagePricesPath = new ClassPathResource("data/BaggagePrices.json");

        baggageStaticMultipliers = objectMapper
                .readValue(baggagePricesPath.getInputStream(),
                        new TypeReference<LinkedHashMap<BaggageCapacity, Double>>() {});

        serviceStaticPrices = Arrays.stream(
                objectMapper.readValue(servicePricesPath.getInputStream(), ServicePricesJSON[].class))
                .collect(Collectors.toMap(sp -> ExtraServiceCode.valueOf(sp.getService()), Function.identity(),
                        (v1, v2) -> v1,
                        LinkedHashMap<ExtraServiceCode, ServicePricesJSON>::new
                        ));

        if (baggageStaticMultipliers.isEmpty() || serviceStaticPrices.isEmpty()) {
            throw new RuntimeException("Could not generate Extra Service offers map.");
        }
    }


}
