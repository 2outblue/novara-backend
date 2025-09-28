package com.novaraspace.model.other;

public class CountryCode {
    private String name;
    private String dialCode;
    private String code;



    public String getName() {
        return name;
    }

    public CountryCode setName(String name) {
        this.name = name;
        return this;
    }

    public String getDialCode() {
        return dialCode;
    }

    public CountryCode setDialCode(String dialCode) {
        this.dialCode = dialCode;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CountryCode setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return "CountryCode{" +
                "name='" + name + '\'' +
                ", dialCode='" + dialCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
