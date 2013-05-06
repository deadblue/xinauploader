package cn._2dland.tools.uploader.cui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import cn._2dland.common.util.IniFile;
import cn._2dland.tools.uploader.Runtime;
import cn._2dland.tools.uploader.UploadClient;
import cn._2dland.tools.uploader.Logger;
import cn._2dland.tools.uploader.sina.SinaClient;
import cn._2dland.tools.uploader.task.TaskNode;
import cn._2dland.tools.uploader.task.TaskQueue;
import cn._2dland.tools.uploader.util.FileUtils;

/**
 * 控制台引擎
 * @author deadblue
 */
public class CUIEngine {

	/** 上传客户端 */
	private UploadClient client = null;
	/** 任务队列 */
	private TaskQueue queue = new TaskQueue();

	public CUIEngine(String taskFile) {
		// 设置httpclient的log级别为error
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "error");

		// 读取配置文件
		StringBuffer buf = new StringBuffer();
		buf.append(Runtime.home).append(File.separator)
			.append("conf").append(File.separator).append("upload.ini");
		IniFile ini = new IniFile(buf.toString());
		// 初始化客户端
		client = new SinaClient(ini.getConfigValue("cui", "account"), 
				ini.getConfigValue("cui", "password"));
		client.setHandler(new CUIUploadHanlder());
		// 解析任务文件
		praseTaskFile(taskFile);
	}

	/**
	 * 解析任务文件，填充任务队列
	 * @param taskFile
	 */
	private void praseTaskFile(String taskFile) {
		try {
			File file = new File(taskFile);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			for(String line; (line = reader.readLine()) != null; ) {
				line = line.trim();
				if(line.length() == 0) continue;
				if(line.startsWith("//")) continue;
				if(line.matches(".*\\|\\d+$")) {
					int pos = line.lastIndexOf('|');
					String videoFile = line.substring(0, pos);
					String times = line.substring(pos + 1);
					queue.addTask(videoFile, Integer.parseInt(times));
				} else {
					queue.addTask(line, 1);
				}
			}
			reader.close();
		} catch (Exception e) {
			Logger.print2c("解析任务文件出错！");
			Logger.error(e);
		}
	}

	/**
	 * 执行上传任务
	 */
	public void start() {
		// 登录失败直接退出
		Logger.print2c("开始登陆账户...");
		if(!client.login()) {
			Logger.print2c("登陆失败，请检查账户设置...");
			return;
		}
		Logger.print2c("开始执行上传任务...");
		// 遍历队列并执行
		while(queue.getRemainTaskCount() > 0) {
			TaskNode task = queue.getNextTask();
			String videoFile = task.getVideoFile();
			String videoName = FileUtils.getFileShortName(videoFile);
			for(int i=0; i< task.getTimes(); i++) {
				String videoId = client.upload(videoFile);
				String line = String.format("%s => VID:%s", videoName, videoId);
				Logger.print2cf(line);
			}
		}
		Logger.print2c("全部任务完成！");
	}

}
