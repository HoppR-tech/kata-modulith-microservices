package tech.hoppr.modulith.order.repository;

import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.shared.OrderId;

public interface OrderRepository {

	Order getBy(OrderId orderId);

    void save(Order order);
}
