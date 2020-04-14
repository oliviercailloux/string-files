package io.github.oliviercailloux.samples.string_files;

import static com.google.common.base.Preconditions.checkState;

import java.security.SecureClassLoader;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;

class FilteringLoader extends SecureClassLoader {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(FilteringLoader.class);

	private ImmutableSet<String> forbiddenClasses;

	FilteringLoader(Set<String> forbiddenClasses, ClassLoader parent) {
		super("Filtering", parent);
		this.forbiddenClasses = ImmutableSet.copyOf(forbiddenClasses);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		LOGGER.info("Loading {}.", name);
		checkState(!forbiddenClasses.contains(name), "Please do NOT use this class: " + name + ".");
		return super.loadClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		TODO();
		return super.findClass(name);
	}
}