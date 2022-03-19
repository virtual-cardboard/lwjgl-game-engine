package common.loader.loadtask;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import common.loader.IOLoadTask;

public class TextFileLoadTask implements IOLoadTask<String> {

	private String filePath;

	public TextFileLoadTask(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String loadIO() throws IOException {
		byte[] encoded = Files.readAllBytes(new File(filePath).toPath());
		return new String(encoded, Charset.defaultCharset());
	}

}
