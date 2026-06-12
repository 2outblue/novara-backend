package com.novaraspace.config;

import com.github.benmanes.caffeine.jcache.configuration.CaffeineConfiguration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.OptionalLong;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {


    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cacheManager -> {
            cacheManager.createCache("buckets", configCreatedMinutes(10_000, 12));
            cacheManager.createCache("locationGroups", configAccessedMinutes(5, 44000));
            cacheManager.createCache("destinations", configAccessedMinutes(5, 44000));
            cacheManager.createCache("location", configAccessedMinutes(100, 44000));
            cacheManager.createCache("routeAvailability", configAccessedMinutes(200, 44000));
            cacheManager.createCache("flightSchedule", configAccessedMinutes(800, 1400));
        };
    }

    private CaffeineConfiguration<Object, Object> configCreatedMinutes(long size, int minutes) {
        CaffeineConfiguration<Object, Object> config = new CaffeineConfiguration<>();
        config.setMaximumSize(OptionalLong.of(size));
        config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, minutes)));
        config.setStoreByValue(false);
        return config;
    }

    private CaffeineConfiguration<Object, Object> configAccessedMinutes(long size, int minutes) {
        CaffeineConfiguration<Object, Object> config = new CaffeineConfiguration<>();
        config.setMaximumSize(OptionalLong.of(size));
        config.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(TimeUnit.MINUTES, minutes)));
        config.setStoreByValue(false);
        return config;
    }
}
