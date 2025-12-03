package tech.hoppr.modulith.testing.time;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
public class TestableClock extends Clock {

	private Instant now;

	@Override
	public ZoneId getZone() {
		return ZoneId.of("UTC");
	}

	@Override
	public Clock withZone(ZoneId zone) {
		throw new UnsupportedOperationException();
	}

	public void setTo(Instant now) {
		this.now = now;
	}

	public void reset() {
		this.now = null;
	}

	@Override
	public Instant instant() {
		if (now == null) {
			return Clock.systemUTC().instant();
		}
		return now;
	}
}
