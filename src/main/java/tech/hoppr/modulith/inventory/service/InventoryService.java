package tech.hoppr.modulith.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.inventory.model.ReserveProducts;
import tech.hoppr.modulith.inventory.model.Reservation;
import tech.hoppr.modulith.inventory.repository.InventoryRepository;

@RequiredArgsConstructor
public class InventoryService {

	private final InventoryRepository inventories;

	@Transactional
    public void handle(ReserveProducts command) {
		Reservation reservation = Reservation.create(command.orderId(), command.products());
		inventories.save(reservation);
    }

}
