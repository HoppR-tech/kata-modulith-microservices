package tech.hoppr.microservice.order.shared;

public record ProductRef(String value) {

    public static ProductRef of(String value) {
        return new ProductRef(value);
    }

}
