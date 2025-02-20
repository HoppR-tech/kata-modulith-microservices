package tech.hoppr.modulith.loyalty.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.loyalty.listener.OrderEventListener;
import tech.hoppr.modulith.loyalty.repository.LoyaltyRepository;
import tech.hoppr.modulith.loyalty.repository.jpa.DbLoyaltyRepository;
import tech.hoppr.modulith.loyalty.repository.jpa.JpaLoyalties;
import tech.hoppr.modulith.loyalty.service.LoyaltyService;

@Configuration
public class LoyaltyConfiguration {

	@Bean
	LoyaltyRepository loyaltyRepository(JpaLoyalties jpaLoyalties) {
		return new DbLoyaltyRepository(jpaLoyalties);
	}

	@Bean
	LoyaltyService loyaltyService(LoyaltyRepository loyalties) {
		return new LoyaltyService(loyalties);
	}

	@Bean
	OrderEventListener orderEventListener(LoyaltyService loyaltyService) {
		return new OrderEventListener(loyaltyService);
	}

}
