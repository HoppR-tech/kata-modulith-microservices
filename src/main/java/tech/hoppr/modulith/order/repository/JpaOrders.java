package tech.hoppr.modulith.order.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrders extends JpaRepository<OrderEntity, String> {

	@Configuration
	class JpaOrdersConfiguration {

		@Bean
		OrderRepository orderRepository(JpaRepository<OrderEntity, String> jpa) {
			return new DbOrderRepository(jpa);
		}

	}

}
