package tech.hoppr.microservice.order.model;

public record ProductRef(String value) {

    public static ProductRef of(String value) {
        return new ProductRef(value);
    }

}
