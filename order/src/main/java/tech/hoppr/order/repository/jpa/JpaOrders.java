package tech.hoppr.order.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.order.repository.jpa.OrderEntity;

@Repository
public interface JpaOrders extends JpaRepository<OrderEntity, String> {
}
