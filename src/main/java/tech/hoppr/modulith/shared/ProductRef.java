package tech.hoppr.modulith.shared;

public record ProductRef(String value) {

    public static ProductRef of(String value) {
        return new ProductRef(value);
    }

}
