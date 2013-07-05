package cn._2dland.tools.uploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传日志类
 * @author deadblue
 */
public class Logger {

	/** 控制台输出时的前缀 */
	private static final String PREFIX = "XinaUploader";
	/** 日期格式化 */
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	/** 时间格式化 */
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	/** 日期格式化 */
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 错误输出 */
	private static PrintWriter errorWriter = null;
	static {
		StringBuffer buf = new StringBuffer();
		buf.append(Runtime.home).append(File.separator)
			.append("log").append(File.separator).append("error.log");
		try {
			File file = new File(buf.toString());
			FileOutputStream fos = new FileOutputStream(file, true);
			errorWriter = new PrintWriter(fos);
		} catch (Exception e) {}
	}

	/**
	 * 输出到控制台
	 * @param line
	 */
	public static void print2c(String line) {
		line = String.format("[%s] %s - %s", PREFIX, timeFormat.format(new Date()), line);
		System.out.println(line);
	}

	/**
	 * 输出到文件
	 * @param line
	 */
	public static void print2f(String line) {
		Date now = new Date();
		// 打开日志文件
		StringBuffer buf = new StringBuffer();
		buf.append(Runtime.home).append(File.separator)
			.append("log").append(File.separator)
			.append("upload_").append(dateFormat.format(now)).append(".log");
		try {
			File logFile = new File(buf.toString());
			FileOutputStream fos = new FileOutputStream(logFile, true);
			PrintWriter writer = new PrintWriter(fos);
			// 写入文件内容
			line = String.format("%s - %s", timeFormat.format(now), line);
			writer.println(line);
			writer.close();
		} catch(Exception e) {}
	}
	
	/**
	 * 输出到控制台和文件
	 * @param line
	 */
	public static void print2cf(String line) {
		print2c(line);
		print2f(line);
	}

	/**
	 * 记录错误信息
	 * @param error
	 */
	public static void error(Throwable error) {
		// 在控制台输出异常
		print2c("发生异常: " + error.getMessage());
		// 将错误信息写入文件
		errorWriter.println(datetimeFormat.format(new Date()));
		error.printStackTrace(errorWriter);
	}

	public static void close() {
		errorWriter.close();
	}

}
