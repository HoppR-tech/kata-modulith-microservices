package tech.hoppr.modulith.order.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import tech.hoppr.modulith.shared.OrderId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@ToString
public final class Order {
    private final OrderId id;
    private final List<Item> items;
	private Instant canceledAt;

	public void cancel(Instant requestedAt) {
		this.canceledAt = requestedAt;
	}

	public Optional<Instant> canceledAt() {
		return Optional.ofNullable(canceledAt);
	}

	public boolean isCanceled() {
		return canceledAt != null;
	}
}
