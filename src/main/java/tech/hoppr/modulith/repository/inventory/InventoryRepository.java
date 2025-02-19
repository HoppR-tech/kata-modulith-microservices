package tech.hoppr.modulith.repository.inventory;

import tech.hoppr.modulith.model.OrderId;
import tech.hoppr.modulith.model.Reservation;

import java.util.Optional;

public interface InventoryRepository {

	void save(Reservation reservation);

	Optional<Reservation> reservationOf(OrderId orderId);
}
