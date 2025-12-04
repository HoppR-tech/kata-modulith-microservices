package tech.hoppr.microservice.order.spi.inventory;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import tech.hoppr.microservice.order.spi.inventory.on_order_placed.OrderPlacedDto;

public interface InventoryClient {

	@PostExchange("/inventories/on-order-placed")
	void accept(@RequestBody OrderPlacedDto request);

}
