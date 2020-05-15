package io.github.oliviercailloux.samples.string_files;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class MyStringFilesUtils implements StringFilesUtils {

	private Path referenceFolder;

	public static MyStringFilesUtils newInstance() {
		return new MyStringFilesUtils();
	}

	private MyStringFilesUtils() {
		this.referenceFolder = Path.of("");
	}

	@Override
	public boolean setReferenceFolder(Path referenceFolder) throws IOException {
		checkNotNull(referenceFolder);
		final Path oldRef = this.referenceFolder;
		this.referenceFolder = referenceFolder;
		return !oldRef.normalize().equals(referenceFolder.normalize());
	}

	private Path asPathRelativeToReference(String path) {
		/**
		 * Slightly ambiguous what an absolute path means in terms of strings, here we
		 * consider the case where the string is interpretable as a path using the
		 * default file system; other equally valid interpretations exist.
		 */
		checkArgument(!Path.of(path).isAbsolute());
		return referenceFolder.resolve(checkNotNull(path));
	}

	@Override
	public String getAbsolutePath(String pathRelativeToReference) {
		return asPathRelativeToReference(pathRelativeToReference).toAbsolutePath().toString();
	}

	@Override
	public ImmutableList<String> getContentUsingIso88591Charset(String pathRelativeToReference) throws IOException {
		final List<String> allLines = Files.readAllLines(asPathRelativeToReference(pathRelativeToReference),
				StandardCharsets.ISO_8859_1);
		return ImmutableList.copyOf(allLines);
	}

	@Override
	public String getPathRelativeToReference(String pathRelativeToCurrent) {
		return referenceFolder.relativize(Path.of(checkNotNull(pathRelativeToCurrent))).toString();
	}
}
