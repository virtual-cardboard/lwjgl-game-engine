package common.loader.loadtask;

import java.io.File;

public final class FontLoadTask extends LoadTask {

	private String fontName;

	public FontLoadTask(String fontName) {
		super(true);
		this.fontName = fontName;
	}

	@Override
	public void load() {
		String pngName = fontName + ".png";
		File image = new File(pngName);
//		String fontFileName = fontName + ;
//		File fontFile = new File(fontFileName);
		if (!image.exists()) {
			image = new File(FontLoadTask.class.getProtectionDomain().getCodeSource().getLocation().getPath() + pngName);
		}
	}

}
