package cn._2dland.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * INI文件读取类
 * @author deadblue
 */
public class IniFile {

	/** 配置项 */
	private Map<String, Map<String, String>> config;

	public IniFile(String fileName) {
		this(new File(fileName));
	}
	public IniFile(File file) {
		if(!file.exists())
			return;
		try {
			FileInputStream fis = new FileInputStream(file);
			parse(fis);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
	public IniFile(InputStream stream) {
		parse(stream);
	}

	/**
	 * 获取配置值
	 * @param section
	 * @param key
	 * @return
	 */
	public String getConfigValue(String section, String key) {
		String value = null;
		if(config.containsKey(section)){
			Map<String, String> secCfg = config.get(section);
			if(secCfg.containsKey(key)){
				value = secCfg.get(key);
			}
		}
		return value;
	}

	/**
	 * 获取配置值
	 * @param section 节
	 * @param key 名称
	 * @param defValue 默认值
	 * @return
	 */
	public String getConfigValue(String section, String key, String defValue) {
		String value = null;
		if(config.containsKey(section)){
			Map<String, String> secCfg = config.get(section);
			if(secCfg.containsKey(key)){
				value = secCfg.get(key);
			}
		}
		return (value==null) ? defValue : value;
	}

	/**
	 * 解析ini文件
	 * @param stream
	 */
	private void parse(InputStream stream) {
		try {
			config = new HashMap<String, Map<String,String>>();
			String currSec = null;
			Map<String, String> currCfg = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			for(String line; (line=br.readLine())!=null; ) {
				line = line.trim();
				// 注释行
				if(line.startsWith(";")) continue;
				// 小节开始
				if(line.matches("^\\[\\w+\\].*$")){
					String nextSec = line.substring(1, line.indexOf("]"));
					if(currSec!=null && currCfg!=null){
						config.put(currSec, currCfg);
						currCfg = null;
					}
					currSec = nextSec;
					continue;
				}
				// 配置行
				if(line.matches("^\\w+=.*$")){
					int eq = line.indexOf("=");
					String key = line.substring(0, eq);
					String value = line.substring(eq+1);

					if(currCfg == null)
						currCfg = new HashMap<String, String>();
					currCfg.put(key, value);
					continue;
				}
			}
			if(currSec!=null && currCfg!=null){
				config.put(currSec, currCfg);
			}
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
