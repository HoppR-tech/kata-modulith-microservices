package tech.hoppr.modulith.loyalty.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.loyalty.model.Loyalty;
import tech.hoppr.modulith.loyalty.repository.LoyaltyRepository;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.shared.CustomerId;

@RequiredArgsConstructor
public class LoyaltyService {

	private final LoyaltyRepository loyalties;

	@Transactional
	public void addPoints(CustomerId customerId, OrderId orderId, int numberOfProducts) {
		Loyalty loyalty = loyalties.ofCustomer(customerId);
		loyalty.computePoints(orderId, numberOfProducts);
		loyalties.save(loyalty);
	}

	@Transactional
	public void cancelPoints(CustomerId customerId, OrderId orderId) {
		Loyalty loyalty = loyalties.ofCustomer(customerId);
		loyalty.cancel(orderId);
		loyalties.save(loyalty);
	}

}
