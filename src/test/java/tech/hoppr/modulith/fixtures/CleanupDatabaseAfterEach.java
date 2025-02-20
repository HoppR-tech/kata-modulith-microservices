package tech.hoppr.modulith.fixtures;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

public class CleanupDatabaseAfterEach implements AfterEachCallback {

	private void truncateAllTables(JdbcTemplate jdbcTemplate) {
		// Step 1: Query all table names from the public schema
		String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_type = 'BASE TABLE'";

		List<String> tableNames = jdbcTemplate.queryForList(sql, String.class);

		// Step 2: Generate TRUNCATE statements for all tables
		for (String tableName : tableNames) {
			// Step 3: Execute TRUNCATE for each table
			String truncateSql = String.format("TRUNCATE TABLE %s RESTART IDENTITY CASCADE", tableName);
			jdbcTemplate.execute(truncateSql);
		}
	}

	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		ApplicationContext app = SpringExtension.getApplicationContext(context);
		JdbcTemplate jdbc = app.getBean(JdbcTemplate.class);
		truncateAllTables(jdbc);
	}
}
