package tech.hoppr.modulith.loyalty;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import tech.hoppr.modulith.TestcontainersConfiguration;
import tech.hoppr.modulith.fixtures.CleanupDatabaseAfterEach;
import tech.hoppr.modulith.loyalty.model.Loyalty;
import tech.hoppr.modulith.loyalty.repository.LoyaltyRepository;
import tech.hoppr.modulith.loyalty.service.LoyaltyService;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.CUSTOMER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID;
import static tech.hoppr.modulith.fixtures.ApplicationFixtures.ORDER_ID_2;

@Transactional
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ExtendWith(CleanupDatabaseAfterEach.class)
public class HistorizeOrderPointsTest {

	@Autowired
	LoyaltyRepository loyalties;
	@Autowired
	LoyaltyService loyaltyService;

	@Test
	void historize_order_points() {
		loyaltyService.addPoints(CUSTOMER_ID, ORDER_ID, 10);
		loyaltyService.addPoints(CUSTOMER_ID, ORDER_ID_2, 13);

		Loyalty actualLoyalty = loyalties.ofCustomer(CUSTOMER_ID);

		assertThat(actualLoyalty.total()).isEqualTo(230);
	}
}
