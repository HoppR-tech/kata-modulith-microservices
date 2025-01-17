package tech.hoppr.modulith.model;

public record Quantity(int value) {

    public static Quantity of(int value) {
        return new Quantity(value);
    }

}
