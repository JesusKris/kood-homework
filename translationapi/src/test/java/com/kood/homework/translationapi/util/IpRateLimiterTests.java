package com.kood.homework.translationapi.util;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
public class IpRateLimiterTests {

    @Autowired
    private IpRateLimiter ipRateLimiter;

    @BeforeEach
    void resetState() {
        ipRateLimiter = new IpRateLimiter();
    }


    @Test
    void testTryAcquire() {
        String endpoint = "/translation";
        String ipAddress = "127.0.0.1";
        double permitsPerSecond = 20;

        boolean hasPermit = ipRateLimiter.tryAcquire(endpoint, ipAddress, permitsPerSecond);

        assertTrue(hasPermit, "Acquiring permit should be successful");
    }


    @Test
    void testTryAcquireRateLimitExceeded() {
        String endpoint = "/translation";
        String ipAddress = "127.0.0.1";
        double permitsPerSecond = 1;

        boolean hasPermit = ipRateLimiter.tryAcquire(endpoint, ipAddress, permitsPerSecond);

        assertTrue(hasPermit, "Acquiring permit should be successful");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }

        boolean hasPermit2 = ipRateLimiter.tryAcquire(endpoint, ipAddress, permitsPerSecond);

        assertFalse(hasPermit2, "Acquiring permit should not be successful");
    }


    @Test
    void testTryAcquireMultipleEndpoints() {
        String endpoint = "/translation";
        String endpoint2 = "/language";
        String ipAddress = "127.0.0.1";
        double permitsPerSecond = 1;


        boolean endpointHasPermit = ipRateLimiter.tryAcquire(endpoint, ipAddress, permitsPerSecond);

        assertTrue(endpointHasPermit, "Acquiring permit should be successful for first endpoint");

        boolean endpoint2HasPermit = ipRateLimiter.tryAcquire(endpoint2, ipAddress, permitsPerSecond);

        assertTrue(endpoint2HasPermit, "Acquiring permit should be successful for second endpoint");
    }


    @Test
    void testTryAcquireMultipleIPs() {
        String endpoint = "/translation";
        String ipAddress = "127.0.0.1";
        String ipAddress2 = "127.0.0.2";
        double permitsPerSecond = 1;


        boolean ipAddressHasPermit = ipRateLimiter.tryAcquire(endpoint, ipAddress, permitsPerSecond);

        assertTrue(ipAddressHasPermit, "Acquiring permit should be successful for first endpoint");

        boolean ipAddress2HasPermit = ipRateLimiter.tryAcquire(endpoint, ipAddress2, permitsPerSecond);

        assertTrue(ipAddress2HasPermit, "Acquiring permit should be successful for second endpoint");
    }
}