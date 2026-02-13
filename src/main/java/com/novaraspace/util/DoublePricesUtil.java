package com.novaraspace.util;

public class DoublePricesUtil {

    public static double normalizePrice(double price) {
        return Math.round(price * 100.0) / 100.0;
    }

    public static boolean areEqual(double price1, double price2) {
        double price1Rounded = DoublePricesUtil.normalizePrice(price1);
        double price2Rounded = DoublePricesUtil.normalizePrice(price2);

        //Just to be safe
        return Math.abs(price1Rounded - price2Rounded) <= 0.01;
    }
}
