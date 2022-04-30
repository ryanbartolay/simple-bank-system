package com.ryan.banking.web.configuration;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Sample cache provider in spring, should be using redis TODO: move to redis
 * 
 * @author barto
 *
 */
@EnableCaching
@Component
public class BankCacheManager implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    private static final String[] CACHES = { "accounts", "account", "users", "user", "transactions", "transaction" };

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(Arrays.asList(CACHES));
    }

    @CacheEvict(allEntries = true, cacheNames = { "accounts", "account", "users", "user", "transactions",
            "transaction" })
    @Scheduled(fixedDelay = 600000)
    public void cacheEvict() {
    }
}