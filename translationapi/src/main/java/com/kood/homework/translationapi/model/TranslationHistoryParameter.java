package com.kood.homework.translationapi.model;

public class TranslationHistoryParameter {

    private final String order;

    public TranslationHistoryParameter(String order) throws IllegalArgumentException {
        if (!isValidOrder(order)) {
            throw new IllegalArgumentException("Invalid order parameter");
        }

        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }

    private static boolean isValidOrder(String order) {
        return "desc".equals(order) || "asc".equals(order);
    }
}
