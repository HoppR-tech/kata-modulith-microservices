package tech.hoppr.modulith.entity;

import lombok.Builder;

import java.util.function.Function;

public sealed interface FindOrCreate<T> {

	T element();

	<R> FindOrCreate<R> map(Function<T, R> mapFn);

	static <T> FindOrCreate<T> found(T element) {
		return new Found<>(element);
	}

	static <T> FindOrCreate<T> toCreate(T element) {
		return new Create<>(element);
	}

	record Found<T>(T element) implements FindOrCreate<T> {
		@Override
		public <R> FindOrCreate<R> map(Function<T, R> mapFn) {
			return found(mapFn.apply(element()));
		}
	}

	record Create<T>(T element) implements FindOrCreate<T> {
		@Override
		public <R> FindOrCreate<R> map(Function<T, R> mapFn) {
			return toCreate(mapFn.apply(element()));
		}
	}

}
