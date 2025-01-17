package tech.hoppr.modulith.model;

import java.util.UUID;

public record OrderId(String value) {

    public static OrderId generate() {
        String value = UUID.randomUUID().toString();
        return new OrderId(value);
    }

}
