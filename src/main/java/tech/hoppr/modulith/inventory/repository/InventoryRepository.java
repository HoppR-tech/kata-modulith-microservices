package tech.hoppr.modulith.inventory.repository;

import tech.hoppr.modulith.shared.OrderId;
import tech.hoppr.modulith.inventory.model.Reservation;

import java.util.Optional;

public interface InventoryRepository {

	void save(Reservation reservation);

	Optional<Reservation> reservationOf(OrderId orderId);
}
