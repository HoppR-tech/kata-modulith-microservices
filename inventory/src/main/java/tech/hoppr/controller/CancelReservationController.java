package tech.hoppr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.service.OrderService;
import tech.hoppr.model.OrderId;
import tech.hoppr.model.Product;
import tech.hoppr.modulith.order.model.OrderCancelled;
import tech.hoppr.shared.OrderPlaced;

import java.util.List;

@RestController
public class CancelReservationController {

    @Autowired
    private final InventoryService inventoryService;
    
    @PostMapping("/cancel-reservation")
    public void handle(@RequestBody OrderCancelled event) {
        inventoryService.handle(CancelReservation.builder()
			.orderId(event.orderId())
			.build());
	}
}
