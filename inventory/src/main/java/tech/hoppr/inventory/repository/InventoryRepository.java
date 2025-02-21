package tech.hoppr.inventory.repository;

import tech.hoppr.inventory.model.OrderId;
import tech.hoppr.inventory.model.Reservation;

import java.util.Optional;

public interface InventoryRepository {

	void save(Reservation reservation);

	Optional<Reservation> reservationOf(OrderId orderId);

    void deleteForOrder(OrderId orderId);
}
