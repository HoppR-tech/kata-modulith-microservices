package tech.hoppr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.model.CancelReservation;
import tech.hoppr.model.Reservation;
import tech.hoppr.model.ReserveProducts;
import tech.hoppr.repository.InventoryRepository;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void handle(ReserveProducts command) {
		Reservation reservation = Reservation.create(command.orderId(), command.products());
		inventories.save(reservation);
    }

	@Transactional
	public void handle(CancelReservation command) {
		inventories.deleteForOrder(command.orderId());
	}
}
