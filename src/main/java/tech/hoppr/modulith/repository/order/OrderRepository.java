package tech.hoppr.modulith.repository.order;

import tech.hoppr.modulith.model.Order;
import tech.hoppr.modulith.model.OrderId;

public interface OrderRepository {

	Order getBy(OrderId orderId);

    void save(Order order);
}
