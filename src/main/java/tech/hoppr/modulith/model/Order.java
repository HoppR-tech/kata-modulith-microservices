package tech.hoppr.modulith.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Builder
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Order {
    private final OrderId id;
    private final List<Item> items;

    public static Order create(List<Item> items) {
        return Order.builder()
                .id(OrderId.generate())
                .items(items)
                .build();
    }
}
