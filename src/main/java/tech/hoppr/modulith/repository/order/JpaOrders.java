package tech.hoppr.modulith.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.modulith.entity.OrderEntity;

@Repository
public interface JpaOrders extends JpaRepository<OrderEntity, String> {
}
