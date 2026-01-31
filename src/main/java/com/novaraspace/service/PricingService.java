package com.novaraspace.service;

import org.springframework.stereotype.Service;

@Service
public class PricingService {






    public double normalizePrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }
}
