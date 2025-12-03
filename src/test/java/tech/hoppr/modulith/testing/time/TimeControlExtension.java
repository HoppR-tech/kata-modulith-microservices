package tech.hoppr.modulith.testing.time;


import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.Collection;

public class TimeControlExtension implements TestExecutionListener, Ordered {

	@Override
	public void beforeTestExecution(TestContext testContext) throws Exception {
		ApplicationContext app = testContext.getApplicationContext();
		resetClock(app);
	}

	private static void resetClock(ApplicationContext app) {
		Collection<TestableClock> listThatCanBeReset = app.getBeansOfType(TestableClock.class).values();
		listThatCanBeReset.forEach(TestableClock::reset);
	}

	@Override
	public int getOrder() {
		return 1000;
	}

}
