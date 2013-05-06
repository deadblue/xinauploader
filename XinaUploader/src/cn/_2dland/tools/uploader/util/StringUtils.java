package cn._2dland.tools.uploader.util;

/**
 * 字符串工具类
 * @author deadblue
 */
public class StringUtils {

	/** 1 kilo byte */
	private static final long ONE_KB = 1024;
	/** 1 mega byte */
	private static final long ONE_MB = 1024 * 1024;
	/** 1 giga byte */
	private static final long ONE_GB = 1024 * 1024 * 1024;

	/** 1 minute */
	private static final int ONE_MINUTE = 60;

	/**
	 * 格式化速度值
	 * @param speed 速度值（字节/秒）
	 * @return
	 */
	public static String formatSpeed(double speed) {
		// 速度单位
		String unit = " b";
		if(speed > ONE_GB) {
			speed /= ONE_GB;
			unit = "gb";
		} else if(speed > ONE_MB) {
			speed /= ONE_MB;
			unit = "mb";
		} else if(speed > ONE_KB) {
			speed /= ONE_KB;
			unit = "kb";
		}
		// 生成速度值字符串
		String result = String.format("%6.2f %s/s", speed, unit);
		return result;
	}

	/**
	 * 格式化时间
	 * @param seconds
	 * @return
	 */
	public static String formatTime(int seconds) {
		int minute = seconds / ONE_MINUTE;
		int second = seconds % ONE_MINUTE;
		String result = String.format("%3d分%02d秒", minute, second);
		return result;
	}

}
