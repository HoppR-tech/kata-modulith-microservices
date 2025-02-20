package tech.hoppr.modulith.inventory.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaInventories extends JpaRepository<ReservationEntity, String> {

	List<ReservationEntity> findAllByOrderId(String orderId);

	void deleteAllByOrderId(String orderId);

}
