package tech.hoppr.microservice.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.hoppr.microservice.inventory.repository.InventoryQueries;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoryController {

	private final InventoryQueries queries;

	@PostMapping("/check")
	@ResponseStatus(code = OK)
	public CheckOutput placeOrder() {
		List<CheckOutput.ProductDto> products = queries.check().stream()
			.map(entity -> new CheckOutput.ProductDto(entity.productRef(), entity.quantity()))
			.toList();

		return new CheckOutput(products);
	}

	public record CheckOutput(List<ProductDto> products) {

		record ProductDto(String ref, int quantity) {}

	}

}
