package com.novaraspace.component;

import jakarta.validation.constraints.Email;
import org.springframework.stereotype.Component;

@Component
public class DataMasker {

    private final char mask = '*';

    public DataMasker() {}

    public String maskEmail(@Email String email) {
        if (email == null || email.length() < 2) {return "**";}

        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "**";
        }

        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        StringBuilder sb = new StringBuilder();

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

    public String hardMaskEmail(@Email String email) {
        if (email == null || email.length() < 2) {return "**";}

        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "**";
        }
        String firstChar = email.substring(0, 1);
        StringBuilder sb = new StringBuilder(firstChar);
        sb.append("***").append("@");

        String domainPart = email.substring(atIndex + 1);
        int dotIndex = domainPart.lastIndexOf('.');

        if (dotIndex <= 0) {
            String lastChars = "";
            if (domainPart.length() >= 2) {
                lastChars = domainPart.substring(domainPart.length() - 2);
            } else {
                lastChars = domainPart.substring(domainPart.length() - 1);
            }
            sb.append("**").append(lastChars);
        } else {
            sb.append("**").append(domainPart.substring(dotIndex));
        }

        return sb.toString();
    }
}
