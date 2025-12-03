package tech.hoppr.modulith.order.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.shared.Item;

import java.util.List;

@Builder
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Order {
    private final OrderId id;
    private final List<Item> items;
}
