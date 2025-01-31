package tech.hoppr.modulith.entity;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class EntityPersister {

	private final EntityManager entityManager;

	public <T, K> T find(Class<T> entityClass, K id) {
		return entityManager.find(entityClass, id);
	}

	public <T, K> T upsert(Class<T> entityClass, K id, Function<T, T> upsertFn) {
		FindOrCreate<T> result = Optional.ofNullable(entityManager.find(entityClass, id))
			.map(FindOrCreate::found)
			.orElseGet(() -> FindOrCreate.toCreate(defaultConstructor(entityClass)))
			.map(upsertFn);

		T entity = result.element();

		switch (result) {
			case FindOrCreate.Create<T> ignored -> entityManager.persist(entity);
			case FindOrCreate.Found<T> ignored -> entityManager.merge(entity);
		}

		return entity;
	}

	@SneakyThrows
	private static <T> T defaultConstructor(Class<T> entityClass) {
		return entityClass.getDeclaredConstructor().newInstance();
	}

}
