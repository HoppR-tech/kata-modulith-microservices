package tech.hoppr.microservice.inventory.testing.time;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Clock;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AutoConfigureTimeControl.Config.class})
@TestExecutionListeners(value = TimeControlExtension.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface AutoConfigureTimeControl {

	@TestConfiguration
	class Config {

		@Bean
		Clock clock() {
			return new TestableClock();
		}

	}
}
