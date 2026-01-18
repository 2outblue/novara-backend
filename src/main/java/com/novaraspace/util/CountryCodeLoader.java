package com.novaraspace.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novaraspace.model.other.CountryCode;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CountryCodeLoader { //TODO: Move this to the component package ?
    private final ObjectMapper mapper;
    private List<CountryCode> cachedData;

    public CountryCodeLoader(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @PostConstruct
    private void init() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/CountryCodes.json");
        cachedData = Arrays.asList(mapper.readValue(resource.getInputStream(), CountryCode[].class));
    }

    public List<CountryCode> getCachedData() {
        return this.cachedData;
    }

    public List<String> getCountryNames() {
        return cachedData.stream().map(CountryCode::getName).toList();
    }

    public List<String> getDialCodes() {
        return cachedData.stream().map(CountryCode::getDialCode).toList();
    }

}
