package tech.hoppr.modulith.loyalty.repository;

import tech.hoppr.modulith.loyalty.model.Loyalty;
import tech.hoppr.modulith.shared.CustomerId;

public interface LoyaltyRepository {

	Loyalty ofCustomer(CustomerId customerId);

	void save(Loyalty loyalty);
}
