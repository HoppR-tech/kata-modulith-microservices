package tech.hoppr.order.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrders extends JpaRepository<OrderEntity, String> {
}
