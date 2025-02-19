package tech.hoppr.modulith.repository.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.hoppr.modulith.entity.ReservationEntity;

import java.util.List;

@Repository
public interface JpaInventories extends JpaRepository<ReservationEntity, String> {

	List<ReservationEntity> findAllByOrderId(String orderId);

}
