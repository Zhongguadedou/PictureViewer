package swing;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * @Description
 * @Author admin
 * @Date 2021/11/26 16:53
 */
final class MyFileChooser extends JFileChooser {
	private final MyFilterWrapper filter;

	public MyFileChooser(MyFilterWrapper filter) {
		this.filter = filter;
		// 扩展名过滤
		setFileFilter(filter);

		// 文件选择属性设置
		setMultiSelectionEnabled(true);
		setAcceptAllFileFilterUsed(false);
		setFileSelectionMode(FILES_AND_DIRECTORIES);
	}

	public String [] getAbsolutePathsRecursively() {
		ArrayList<String> paths = new ArrayList<String>();
		File[] files = getSelectedFiles();
		traverse(files, paths);
		return paths.toArray(new String [] {});
	}

	private void traverse(File [] files, ArrayList<String> paths) {
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				traverse(f.listFiles(this.filter), paths);
			} else if (f.isFile() && this.filter.accept(f)) {
				paths.add(f.getAbsolutePath());
			}
		}
	}
}
