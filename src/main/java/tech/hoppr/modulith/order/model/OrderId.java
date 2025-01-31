package tech.hoppr.modulith.order.model;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

public record OrderId(String value) {

    public static OrderId of(String value) {
        return new OrderId(value);
    }

	public interface Provider {

		OrderId get();

		static Provider timeOrdered() {
			return new TimeOrdered();
		}

		final class TimeOrdered implements Provider {

			@Override
			public OrderId get() {
				Ulid ulid = UlidCreator.getMonotonicUlid();
				return new OrderId(ulid.toLowerCase());
			}

		}

	}

}
