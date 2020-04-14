package io.github.oliviercailloux.samples.string_files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

class StringFilesUtilsTests {

	@Test
	void testUpToDate() throws Exception {
		final FilteringLoader loader = new FilteringLoader(ImmutableSet.of(File.class));
		final StringFilesUtils instance = (StringFilesUtils) loader.loadClass(MyStringFilesUtils.class.getName())
				.asSubclass(StringFilesUtils.class).getDeclaredMethod("newInstance").invoke(null);
		instance.setReferenceFolder(".");
	}

	@Test
	void testEquivalentFolders() throws Exception {
		final MyStringFilesUtils utils = new MyStringFilesUtils();
		utils.setReferenceFolder(Path.of("."));
		assertFalse(utils.setReferenceFolder(Path.of("")));
	}

	@Test
	void testReadLines() throws Exception {
		final MyStringFilesUtils utils = new MyStringFilesUtils();
		try (FileSystem inMemoryFs = Jimfs.newFileSystem(Configuration.unix())) {
			Files.writeString(inMemoryFs.getPath("ploum.txt"), "H\u00E9 !\nBonjour.\n \t\n\r");

			utils.setReferenceFolder(inMemoryFs.getPath(""));
			final ImmutableList<String> readContent = utils.getContentUsingIso88591Charset("ploum.txt");
			assertEquals(ImmutableList.of("Hé !", "Bonjour.", " \t", ""), readContent);
		}
	}

	/**
	 * <p>
	 * Unfinished test! This only tests the case where the reference folder is
	 * relative, not the case where it is absolute.
	 * </p>
	 * <p>
	 * NOTE: this part is NOT graded, you do not <em>have to</em> finish this test
	 * (but doing so may help with testing your implementation).
	 * </p>
	 */
	@Test
	void testRelativeTo() throws Exception {
		final MyStringFilesUtils utils = new MyStringFilesUtils();

		utils.setReferenceFolder(Path.of("a", "b"));
		assertEquals("c", utils.getPathRelativeToReference("a/b/c"));

		utils.setReferenceFolder(Path.of(""));
		assertEquals("a/b/c", utils.getPathRelativeToReference("a/b/c"));

		utils.setReferenceFolder(Path.of("/root/subfolder"));
		/** TODO… */
	}

}
