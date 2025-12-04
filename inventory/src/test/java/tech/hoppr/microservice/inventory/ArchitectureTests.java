package tech.hoppr.microservice.inventory;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTests {

	private static final String BASE_PACKAGE = "tech.hoppr.microservice.inventory";

	@Test
	void every_controller_should_be_in_a_controller_package() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

		ArchRule rule = classes()
			.that()
			.haveSimpleNameEndingWith("Controller")
			.should()
			.resideInAnyPackage("..controller..");

		rule.check(importedClasses);
	}

	@Test
	void every_entities_should_be_side_by_side_with_its_repository() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

		ArchRule rule = classes()
			.that()
			.haveSimpleNameEndingWith("Entity")
			.should()
			.resideInAnyPackage("..repository..")
			.andShould()
			.bePackagePrivate();

		rule.check(importedClasses);
	}

	@Test
	void no_domain_service_should_depend_on_API_side() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

		ArchRule rule = noClasses()
			.that()
			.resideInAPackage("..service..")
			.should()
			.dependOnClassesThat()
			.resideInAnyPackage("..controller..");

		rule.check(importedClasses);
	}

	@Test
	void domain_service_should_depend_on_implemented_partners() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages(BASE_PACKAGE);

		ArchRule rule = noClasses()
			.that()
			.resideInAPackage("..service..")
			.should()
			.dependOnClassesThat()
			.haveSimpleNameEndingWith("Impl")
			.orShould()
			.dependOnClassesThat()
			.haveSimpleNameEndingWith("Adapter");
		// could use thatAreNotInterfaces

		rule.check(importedClasses);
	}
}
