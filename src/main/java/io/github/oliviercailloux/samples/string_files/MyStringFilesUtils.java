package io.github.oliviercailloux.samples.string_files;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

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
		this.referenceFolder = referenceFolder;
		return !Files.isSameFile(this.referenceFolder, referenceFolder);
	}

	private Path asRelativePath(String path) {
		final Path asPath = Path.of(checkNotNull(path));
		checkArgument(!asPath.isAbsolute());
		return asPath;
	}

	@Override
	public String getAbsolutePath(String pathRelativeToReference) {
		return referenceFolder.resolve(asRelativePath(pathRelativeToReference)).toAbsolutePath().toString();
	}

	@Override
	public ImmutableList<String> getContentUsingIso88591Charset(String pathRelativeToReference) throws IOException {
		return ImmutableList.copyOf(Files.readAllLines(referenceFolder.resolve(asRelativePath(pathRelativeToReference)),
				StandardCharsets.ISO_8859_1));
	}

	@Override
	public String getPathRelativeToReference(String pathRelativeToCurrent) {
		return referenceFolder.relativize(asRelativePath(pathRelativeToCurrent)).toString();
	}
}
