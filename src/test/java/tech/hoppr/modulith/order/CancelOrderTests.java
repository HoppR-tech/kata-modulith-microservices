package tech.hoppr.modulith.order;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.fixtures.CleanupDatabaseAfterEach;
import tech.hoppr.modulith.order.model.Item;
import tech.hoppr.modulith.order.model.Order;
import tech.hoppr.modulith.order.model.OrderCancelled;
import tech.hoppr.modulith.order.model.OrderException;
import tech.hoppr.modulith.order.model.WithdrawalPeriodExceeded;
import tech.hoppr.modulith.order.repository.OrderRepository;
import tech.hoppr.modulith.order.service.OrderService;
import tech.hoppr.modulith.shared.MessageBus;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.CUSTOMER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.PRODUCT_REF;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.QTY_TEN;
import static tech.hoppr.modulith.order.assertions.OrderAssertions.assertThat;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
public class CancelOrderTests {

	static final Instant PLACED_AT = Instant.ofEpochMilli(0);

	@MockitoBean
	Clock clock;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderRepository orders;
	@MockitoBean
	MessageBus messageBus;
	@Autowired
	OrderRepository orderRepository;

	@BeforeEach
	void setUp() {
		orders.save(Order.builder()
			.id(ORDER_ID)
			.customerId(CUSTOMER_ID)
			.item(Item.builder()
				.product(PRODUCT_REF)
				.quantity(QTY_TEN)
				.build())
			.placedAt(PLACED_AT)
			.build());
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5, 13, 14})
	void cancel_an_order(int numberOfDays) {
		Instant cancelledAt = fixClockFromOrderDate(numberOfDays);

		orderService.cancel(ORDER_ID);

		Order actualOrder = orderRepository.getBy(ORDER_ID);

		assertThat(actualOrder)
			.isCancelledSince(cancelledAt);

		orderCancelledShouldBeEmitted(cancelledAt);
	}

	private void orderCancelledShouldBeEmitted(Instant cancelledAt) {
		verify(messageBus).emit(OrderCancelled.builder()
			.orderId(ORDER_ID)
			.customerId(CUSTOMER_ID)
			.cancelledAt(cancelledAt)
			.build());
	}

	@ParameterizedTest
	@ValueSource(ints = {15, 50, 100})
	void order_cannot_be_cancelled(int numberOfDays) {
		fixClockFromOrderDate(numberOfDays);

		ThrowableAssert.ThrowingCallable callable = () -> orderService.cancel(ORDER_ID);

		assertThatExceptionOfType(WithdrawalPeriodExceeded.class)
			.isThrownBy(callable)
			.isInstanceOf(OrderException.class)
			.withMessage("Cannot cancel order 'FR001' because the withdrawal period has been exceeded");
	}

	private Instant fixClockFromOrderDate(int numberOfDays) {
		Instant fakeNow = PLACED_AT.plus(numberOfDays, ChronoUnit.DAYS);

		when(clock.instant())
			.thenReturn(fakeNow);

		return fakeNow;
	}

}
