package com.novaraspace.model.embedded;

import jakarta.persistence.Embeddable;

@Embeddable
public class DestinationDetails {

    private String detailName;
    private String detailValue;

    public String getDetailName() {
        return detailName;
    }

    public DestinationDetails setDetailName(String detailName) {
        this.detailName = detailName;
        return this;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public DestinationDetails setDetailValue(String detailValue) {
        this.detailValue = detailValue;
        return this;
    }

}
