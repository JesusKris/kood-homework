package com.kood.homework.translationapi.model;


import java.util.Set;


/**
 * Represents parameters for translation history requests.
 * It includes the order parameter to specify the sorting order (asc or desc).
 * 
 * 
 * <p>Used for verification of the request query parameters.
 * 
 * <p>
 * <strong>Author:</strong> JesusKris
 * </p>
 */
public class TranslationHistoryParameter {
    private final String order;

    private static final Set<String> VALID_ORDERS = Set.of("asc", "desc");

    
    /**
     * Constructs a new TranslationHistoryParameter with the specified order.
     *
     * @param order The sorting order (asc or desc).
     * @throws IllegalArgumentException If the order parameter is invalid.
     */
    public TranslationHistoryParameter(String order) throws IllegalArgumentException {
        if (order == null || !isValidOrder(order)) {
            throw new IllegalArgumentException("Invalid order parameter");
        }

        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }

    private static boolean isValidOrder(String order) {
        return VALID_ORDERS.contains(order);
    }
}