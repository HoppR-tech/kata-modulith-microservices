package tech.hoppr.order.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.order.model.OrderId;

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
