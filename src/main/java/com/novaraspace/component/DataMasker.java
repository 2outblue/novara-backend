package com.novaraspace.component;

import org.springframework.stereotype.Component;

@Component
public class DataMasker {

    private final char mask = '*';

    public DataMasker() {}

    public String maskEmail(String email) {
        if (email == null || email.length() < 2) {return "**";}

        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "**";
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        String maskedLocal = localPart.charAt(0) + String.valueOf(mask).repeat(Math.max(0, localPart.length() - 1));

        int dotIndex = domainPart.lastIndexOf('.');
        if (dotIndex <= 0) {
            return maskedLocal + "@" + domainPart;
        }
        String domainName = domainPart.substring(0, dotIndex);
        String extension = domainPart.substring(dotIndex);

        String maskedDomain = domainName.charAt(0) + String.valueOf(mask).repeat(Math.max(0, domainName.length() - 1));
        if (maskedDomain.length() == 1) {
            maskedDomain = "*";
        }
        return maskedLocal + "@" + maskedDomain + extension;
    }

    public String maskPhoneNumber(String rawNumber) {
        if (rawNumber == null || rawNumber.length() < 2) {return "**";}

        String maskedPart = rawNumber.substring(0, rawNumber.length() - 2);
        String visiblePart = rawNumber.substring(rawNumber.length() - 2);
        int maskLength = maskedPart.length();
        return String.valueOf(mask).repeat(maskLength) + visiblePart;
    }
}
