package cn._2dland.tools.uploader;

/**
 * 运行环境
 * @author deadblue
 */
public class Runtime {

	/** 所在目录 */
	public static String home = null;

	static {
		home = System.getProperty("user.dir");
	}

}
