package tech.hoppr.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.inventory.model.CancelReservation;
import tech.hoppr.inventory.model.Reservation;
import tech.hoppr.inventory.model.ReserveProducts;
import tech.hoppr.inventory.repository.InventoryRepository;

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
