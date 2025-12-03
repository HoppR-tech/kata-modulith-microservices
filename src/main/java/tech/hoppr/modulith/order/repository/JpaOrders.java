package tech.hoppr.modulith.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.modulith.order.entity.OrderEntity;

@Repository
public interface JpaOrders extends JpaRepository<OrderEntity, String> {
}
