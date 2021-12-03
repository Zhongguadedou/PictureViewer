package swing;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:53
 */
final class MyFilterWrapper extends javax.swing.filechooser.FileFilter implements java.io.FileFilter {
	private final FileNameExtensionFilter filter;

	public MyFilterWrapper(String description, String... extensions) {
		this.filter = new FileNameExtensionFilter(description, extensions);
	}

	public boolean accept(File f) {
		return this.filter.accept(f);
	}

	public String getDescription() {
		return this.filter.getDescription();
	}
}
