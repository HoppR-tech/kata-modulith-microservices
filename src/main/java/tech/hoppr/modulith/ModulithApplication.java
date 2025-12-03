package tech.hoppr.modulith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.hoppr.modulith.shared.MessageEmitter;
import tech.hoppr.modulith.shared.SpringMessageEmitter;

import java.time.Clock;

@SpringBootApplication
public class ModulithApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulithApplication.class, args);
    }

	@Configuration
	public static class SharedConfiguration {

		@ConditionalOnMissingBean(Clock.class)
		@Bean
		Clock clock() {
			return Clock.systemUTC();
		}

		@Bean
		MessageEmitter messageEmitter(ApplicationEventPublisher publisher) {
			return new SpringMessageEmitter(publisher);
		}

	}


}
