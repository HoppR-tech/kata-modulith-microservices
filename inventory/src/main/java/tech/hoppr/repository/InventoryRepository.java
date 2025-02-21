package tech.hoppr.repository;

import tech.hoppr.shared.OrderId;
import tech.hoppr.model.Reservation;

import java.util.Optional;

public interface InventoryRepository {

	void save(Reservation reservation);

	Optional<Reservation> reservationOf(OrderId orderId);

    void deleteForOrder(OrderId orderId);
}
