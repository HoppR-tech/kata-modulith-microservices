package tech.hoppr.modulith.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@IdClass(ReservationEntity.PK.class)
@Entity
@Table(name = "reservations")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class ReservationEntity {

	@Id
	@Column(name = "order_id")
	@EqualsAndHashCode.Include
	private String orderId;
    @Id
    @Column(name = "product_ref")
	@EqualsAndHashCode.Include
    private String productRef;
    @Column(name = "quantity")
    private int quantity;

	@RequiredArgsConstructor
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class PK {
		private String orderId;
		private String productRef;
	}

}
