package tech.hoppr.modulith.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.repository.DbOrderRepository;
import tech.hoppr.modulith.repository.OrderRepository;
import tech.hoppr.modulith.service.InventoryService;
import tech.hoppr.modulith.service.OrderService;

@Configuration
public class ApplicationConfiguration {

    @Bean
    OrderRepository orderRepository(JPAQueryFactory factory) {
        return new DbOrderRepository(factory);
    }

    @Bean
    InventoryService inventoryService() {
        return new InventoryService();
    }

    @Bean
    OrderService orderService(OrderRepository orders, InventoryService inventoryService) {
        return new OrderService(orders, inventoryService);
    }
}
