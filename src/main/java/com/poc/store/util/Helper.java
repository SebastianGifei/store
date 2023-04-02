package com.poc.store.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Helper {

    public static String getNewCorrelationId() {
        return UUID.randomUUID().toString();
    }
}
