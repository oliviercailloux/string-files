package io.github.oliviercailloux.samples.string_files;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.Callable;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

class FilteringLoaderTests {
	@SuppressWarnings("unused")
	static final Logger LOGGER = LoggerFactory.getLogger(FilteringLoaderTests.class);

	@Test
	void testFilter() throws Exception {
		final FilteringLoader filtering = new FilteringLoader(ImmutableSet.of("java.io.File"),
				getClass().getClassLoader());
		LOGGER.info("Filtering loader: {}.", filtering.getName());
		final Callable<Void> instance = filtering.loadClass(getClass().getPackageName() + ".Invoker")
				.asSubclass(Callable.class).getDeclaredConstructor().newInstance();
		LOGGER.info("The loader: {}.", instance.getClass().getClassLoader().getName());
		assertThrows(IllegalStateException.class, () -> instance.call());
	}

}
