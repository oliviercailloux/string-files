package io.github.oliviercailloux.samples.string_files;

import java.util.concurrent.Callable;

class Invoker implements Callable<Void> {
	@Override
	public Void call() throws Exception {
		final Class<? extends Invoker> class1 = getClass();
		final ClassLoader myLoader = class1.getClassLoader();
		FilteringLoaderTests.LOGGER.info("My loader: {}.", myLoader.getName());
		final StringFilesUtils instance = myLoader
				.loadClass("io.github.oliviercailloux.samples.string_files.UtilsWithFile")
				.asSubclass(StringFilesUtils.class).getDeclaredConstructor().newInstance();
		instance.setReferenceFolder(".");
		return null;
	}
}