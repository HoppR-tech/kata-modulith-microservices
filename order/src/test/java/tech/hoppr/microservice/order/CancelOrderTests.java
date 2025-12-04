package tech.hoppr.microservice.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.microservice.order.model.Item;
import tech.hoppr.microservice.order.model.Order;
import tech.hoppr.microservice.order.model.WithdrawalPeriodHasExpired;
import tech.hoppr.microservice.order.published.OrderCanceled;
import tech.hoppr.microservice.order.repository.OrderRepository;
import tech.hoppr.microservice.order.service.OrderService;
import tech.hoppr.microservice.order.model.OrderId;
import tech.hoppr.microservice.order.model.Quantity;
import tech.hoppr.microservice.order.testing.time.AutoConfigureTimeControl;
import tech.hoppr.microservice.order.testing.time.TestableClock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;
import static tech.hoppr.microservice.order.assertions.OrderAssertions.assertThat;
import static tech.hoppr.microservice.order.assertions.EventCaptorAssertions.assertThat;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.ORDER_ID;
import static tech.hoppr.microservice.order.fixtures.OrderFixtures.PRODUCT_REF;

@Transactional
@SpringBootTest
@AutoConfigureTimeControl
public class CancelOrderTests {

	static final Instant PLACED_AT = Instant.ofEpochMilli(1000);

	@MockitoBean
	OrderId.Provider idProvider;
	@Autowired
	OrderRepository orders;
	@Autowired
	EventCaptor eventCaptor;
	@Autowired
	OrderService orderService;
	@Autowired
	TestableClock clock;

	@BeforeEach
	void setUp() {
		when(idProvider.get())
			.thenReturn(ORDER_ID);

		orders.save(Order.place()
			.id(ORDER_ID)
			.items(List.of(Item.builder()
				.product(PRODUCT_REF)
				.quantity(Quantity.of(2))
				.build()))
			.requestedAt(PLACED_AT)
			.build());
	}

	@AfterEach
	void tearDown() {
		orders.remove(ORDER_ID);
	}

	private Order cancelOrder() {
		orderService.cancelOrder(ORDER_ID);

		return orders.getBy(ORDER_ID);
	}

	@ParameterizedTest
	@ValueSource(ints = { 0, 1, 5, 13, 14 })
	void cancel_an_order(int numberOfDays) {
		Instant cancelledAt = PLACED_AT.plus(numberOfDays, ChronoUnit.DAYS);
		clock.setTo(cancelledAt);

		Order actualOrder = cancelOrder();

		assertThat(actualOrder)
			.hasAnId()
			.isCanceled()
			.isCanceledAt(cancelledAt);
	}

	@Test
	void cannot_cancel_an_order_after_14_days() {
		clock.setTo(PLACED_AT.plus(15, ChronoUnit.DAYS));

		assertThatExceptionOfType(WithdrawalPeriodHasExpired.class)
			.isThrownBy(this::cancelOrder)
			.withMessage("The withdrawal period has expired.")
			.satisfies(e -> Assertions.assertThat(e.getOrderId()).isEqualTo(ORDER_ID));
	}

	@Test
	void notify_that_order_is_canceled() {
		Instant cancelledAt = clock.setTo(PLACED_AT.plus(0, ChronoUnit.DAYS));

		cancelOrder();

		assertThat(eventCaptor).hasCaptured(OrderCanceled.builder()
			.orderId(ORDER_ID)
			.canceledAt(cancelledAt)
			.build());
	}

}
