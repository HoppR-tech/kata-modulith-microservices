package tech.hoppr.modulith.loyalty.model;

import tech.hoppr.modulith.shared.CustomerId;

public sealed interface LoyaltyEvent permits LoyaltyPointsAdded, LoyaltyPointsCancelled {

	CustomerId customerId();

}
