package io.github.oliviercailloux.samples.string_files;

import java.nio.file.Files;

public class MyStringFilesUtils implements StringFilesUtils {

	public static MyStringFilesUtils newInstance() {
		return new MyStringFilesUtils();
		Files.readAllLines()
	}
}
