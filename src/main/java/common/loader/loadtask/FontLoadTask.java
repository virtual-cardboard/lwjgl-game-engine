package common.loader.loadtask;

import static java.io.File.pathSeparator;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public final class FontLoadTask extends OpenglLoadTask {

	private String fontName;

	public FontLoadTask(String fontName) {
		this.fontName = fontName;
	}

	public FontLoadTask(CountDownLatch countDownLatch, String fontName) {
		super(countDownLatch);
		this.fontName = fontName;
	}

	@Override
	public void loadNonOpenGl() throws IOException {
		String pngName = fontName + ".png";
		File file = new File(pngName);
		if (!file.exists()) {
			file = new File(FontLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "fonts" + pathSeparator + pngName);
		}
	}

	@Override
	public void loadOpengl() {
	}

}
