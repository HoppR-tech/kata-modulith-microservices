package tech.hoppr.modulith.loyalty.repository.jpa;

import lombok.RequiredArgsConstructor;
import tech.hoppr.modulith.loyalty.model.Loyalty;
import tech.hoppr.modulith.loyalty.model.LoyaltyPointsAdded;
import tech.hoppr.modulith.loyalty.model.LoyaltyPointsCancelled;
import tech.hoppr.modulith.loyalty.repository.LoyaltyRepository;
import tech.hoppr.modulith.order.model.OrderId;
import tech.hoppr.modulith.shared.CustomerId;

import java.util.List;

@RequiredArgsConstructor
public class DbLoyaltyRepository implements LoyaltyRepository {

	private final JpaLoyalties loyalties;

	@Override
	public Loyalty ofCustomer(CustomerId customerId) {
		List<LoyaltyEntity> entities = loyalties.findAllByCustomerId(customerId.value());

		if (entities.isEmpty()) {
			return Loyalty.empty(customerId);
		}

		Loyalty.Builder builder = Loyalty.builder();

		entities.forEach(entity -> builder.history(
			OrderId.of(entity.getOrderId()),
			entity.getNumberOfPoints()
		));

		return builder.customerId(customerId).build();
	}

	@Override
	public void save(Loyalty loyalty) {
		loyalty.occurredEvents().forEach(event -> {
			switch (event) {
				case LoyaltyPointsAdded e -> apply(e);
				case LoyaltyPointsCancelled e -> apply(e);
			}
		});
	}

	private void apply(LoyaltyPointsAdded event) {
		loyalties.save(LoyaltyEntity.builder()
			.customerId(event.customerId().value())
			.orderId(event.orderId().value())
			.numberOfPoints(event.addedPoints())
			.build());
	}

	private void apply(LoyaltyPointsCancelled event) {
		loyalties.deleteByCustomerIdAndOrderId(
			event.customerId().value(),
			event.orderId().value());
	}
}
