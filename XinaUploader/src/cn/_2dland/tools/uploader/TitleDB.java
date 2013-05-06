package cn._2dland.tools.uploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 标题数据库
 * @author deadblue
 */
public class TitleDB {

	/** 随机数发生器 */
	private static final Random random = new Random(System.currentTimeMillis());
	/** 标题列表 */
	private static List<String> titles = new ArrayList<String>();

	static {
		// 从配置文件中读取title，并存入列表中
		StringBuffer buf = new StringBuffer();
		buf.append(Runtime.home).append(File.separator)
			.append("data").append(File.separator).append("title.db");
		File file = new File(buf.toString());
		if(file.exists() && file.isFile()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				for(String line; (line = reader.readLine()) != null; ) {
					line = line.trim();
					// 跳过空白和注释行
					if(line.length() == 0) continue;
					if(line.startsWith("//")) continue;
					titles.add(line);
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取标题
	 * @return
	 */
	public static String getRandomTitle() {
		String title = null;
		if(titles.size() > 0) {
			int index = random.nextInt(titles.size());
			title = titles.get(index);
		} else {
			title = "标题被阿伊吃掉了~";
		}

		return title;
	}
}
