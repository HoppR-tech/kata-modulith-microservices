package tech.hoppr.modulith.loyalty.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLoyalties extends JpaRepository<LoyaltyEntity, String> {

	List<LoyaltyEntity> findAllByCustomerId(String customerId);

	void deleteByCustomerIdAndOrderId(String customerId, String orderId);
}
