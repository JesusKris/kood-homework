package com.kood.homework.translationapi.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;

/**
 * IpRateLimiter provides rate-limiting functionality based on IP addresses for
 * different endpoints.
 * <p>
 * This class allows controlling the rate at which requests from specific IP
 * addresses are processed
 * for different API endpoints. It utilizes the Google Guava RateLimiter to
 * enforce the rate limits.
 * </p>
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
@Component
public class IpRateLimiter {

    /**
     * A concurrent map to store rate limiters for different endpoints and IP
     * addresses.
     * The outer map uses endpoint as the key, and the inner map uses IP address as
     * the key.
     */
    private final Map<String, Map<String, RateLimiter>> endpointRateLimiters = new ConcurrentHashMap<>();

    /**
     * Attempts to acquire a permit from the rate limiter associated with the
     * specified endpoint and IP address.
     *
     * @param endpoint         The API endpoint for which the rate limiter is
     *                         applied.
     * @param ipAddress        The IP address for which the rate limiter is applied.
     * @param permitsPerSecond The maximum number of permits the rate limiter allows
     *                         per second.
     * @return {@code true} if a permit was acquired successfully, {@code false}
     *         otherwise.
     */

    public boolean tryAcquire(String endpoint, String ipAddress, double permitsPerSecond) {
        Map<String, RateLimiter> ipRateLimiters = endpointRateLimiters.computeIfAbsent(endpoint,
                k -> new ConcurrentHashMap<>());

        RateLimiter rateLimiter = ipRateLimiters.computeIfAbsent(ipAddress, k -> RateLimiter.create(permitsPerSecond));
        return rateLimiter.tryAcquire();
    }
}