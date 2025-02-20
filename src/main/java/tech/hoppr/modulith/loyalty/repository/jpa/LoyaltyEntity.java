package tech.hoppr.modulith.loyalty.repository.jpa;

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

@IdClass(LoyaltyEntity.PK.class)
@Entity
@Table(name = "loyalties")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
public class LoyaltyEntity {

	@Id
	@Column(name = "customer_id")
	@EqualsAndHashCode.Include
	private String customerId;

	@Id
	@Column(name = "order_id")
	private String orderId;

	@Column(name = "number_of_points")
	private int numberOfPoints;

	@RequiredArgsConstructor
	@EqualsAndHashCode
	@ToString
	@Getter
	public static class PK {
		private String customerId;
		private String orderId;
	}

}
