package com.kood.homework.translationapi.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.util.concurrent.RateLimiter;

public class IpRateLimiter {
    private final Map<String, Map<String, RateLimiter>> endpointRateLimiters = new ConcurrentHashMap<>();

    public boolean tryAcquire(String endpoint, String ipAddress, double permitsPerSecond) {
        Map<String, RateLimiter> ipRateLimiters = endpointRateLimiters.computeIfAbsent(endpoint, k -> new ConcurrentHashMap<>());
        RateLimiter rateLimiter = ipRateLimiters.computeIfAbsent(ipAddress, k -> RateLimiter.create(permitsPerSecond));
        return rateLimiter.tryAcquire();
    }
}
