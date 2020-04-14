package io.github.oliviercailloux.samples.string_files;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsWithFile implements StringFilesUtils {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(UtilsWithFile.class);

	@Override
	public boolean setReferenceFolder(String referenceFolder) {
		LOGGER.info("Path separator: {}.", File.pathSeparator);
		return true;
	}
}
