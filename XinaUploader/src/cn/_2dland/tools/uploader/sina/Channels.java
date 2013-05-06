package cn._2dland.tools.uploader.sina;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Channels {

	/** 随机数发生器 */
	private static final Random random = new Random(System.currentTimeMillis());
	/** 频道列表 */
	private static Map<String, String> channelMap = null;

	static {
		channelMap = new HashMap<String, String>();
		channelMap.put("1", "娱乐");
		channelMap.put("2", "搞笑");
		channelMap.put("4", "体育");
		channelMap.put("5", "社会");
		channelMap.put("6", "音乐");
		channelMap.put("7", "生活");
		channelMap.put("8", "科技");
		channelMap.put("9", "猎奇");
		channelMap.put("10", "动漫");
		channelMap.put("11", "游戏");
		channelMap.put("12", "广告");
		channelMap.put("13", "教育");
		channelMap.put("15", "汽车");
		channelMap.put("16", "旅游");
		channelMap.put("17", "原创");
		channelMap.put("18", "宠物");
		channelMap.put("19", "房产");
		channelMap.put("21", "育儿");
		channelMap.put("24", "拍客");
		channelMap.put("25", "军事");
		channelMap.put("26", "时尚");
	}

	/**
	 * 随机抽取一个频道
	 * @return
	 */
	public static String getRandomChannel() {
		int index = random.nextInt(channelMap.size());
		String[] ids = channelMap.keySet().toArray(new String[]{});
		return ids[index];
	}

	/**
	 * 获取频道名称
	 * @param channelId
	 * @return
	 */
	public static String getChannelName(String channelId) {
		return channelMap.get(channelId);
	}

}
