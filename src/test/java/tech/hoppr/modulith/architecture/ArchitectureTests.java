package tech.hoppr.modulith.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
	packages = { "tech.hoppr.modulith" },
	importOptions ={ ImportOption.DoNotIncludeTests.class }
)
public class ArchitectureTests {

	@ArchTest
	static final ArchRule inventory_must_not_depend_on_order =
		noClasses().that().resideInAPackage("..inventory..")
				.should().dependOnClassesThat().resideInAPackage("..order..");

	@ArchTest
	static final ArchRule order_must_not_depend_on_inventory =
		noClasses().that().resideInAPackage("..order..")
				.should().dependOnClassesThat().resideInAPackage("..inventory..");
}
