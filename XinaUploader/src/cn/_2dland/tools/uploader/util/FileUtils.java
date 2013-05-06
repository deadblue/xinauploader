package cn._2dland.tools.uploader.util;

import java.io.File;

public class FileUtils {

	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension(String fileName) {
		if(fileName == null) return null;
		int pos = fileName.lastIndexOf('.');
		return pos < 0 ? null : fileName.substring(pos + 1);
	}

	/**
	 * 获取文件名称
	 * @param fileName
	 * @return
	 */
	public static String getFileShortName(String fileName) {
		if(fileName == null) return null;
		int pos = fileName.lastIndexOf(File.separator);
		return pos < 0 ? fileName : fileName.substring(pos + 1);
	}

}
