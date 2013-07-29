package cn._2dland.tools.uploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * 标签数据库
 * @author deadblue
 */
public class TagDB {

	/** 随机数发生器 */
	private static final Random random = new Random(System.currentTimeMillis());
	/** tag列表 */
	private static List<String> tags = new ArrayList<String>();

	static {
		// 从配置文件中读取tag，并存入列表中
		StringBuffer buf = new StringBuffer();
		buf.append(Runtime.home).append(File.separator)
			.append("data").append(File.separator).append("tag.db");
		File file = new File(buf.toString());
		if(file.exists() && file.isFile()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				for(String line; (line = reader.readLine()) != null; ) {
					// 跳过空白和注释行
					if(line.length() == 0) continue;
					if(line.startsWith("//")) continue;
					tags.add(line);
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 随机获取若干个tag
	 * @param separator
	 * @return
	 */
	public static String getRandomTags(String separator) {
		// 预置tag
		StringBuffer buf = new StringBuffer("xuv2");
		// 产生2~3个tag
		int count = random.nextInt(2) + 2;
		count = Math.min(count, tags.size());
		// 取出指定数量的不重复tag
		HashSet<String> tagSet = new HashSet<String>(count);
		while(tagSet.size() < count) {
			int index = random.nextInt(tags.size());
			tagSet.add(tags.get(index));
		}
		// 拼成字符串返回
		for(String tag : tagSet) {
			buf.append(separator).append(tag);
		}
		return buf.toString();
	}

}
