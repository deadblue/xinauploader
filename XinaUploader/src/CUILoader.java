import cn._2dland.tools.uploader.Logger;
import cn._2dland.tools.uploader.cui.CUIEngine;

/**
 * CUI入口
 * @author deadblue
 */
public class CUILoader {

	public static void main(String[] args) {
		if(args.length > 0) {
			String taskFile = args[0];
			Logger.print2c("上传器启动...");
			Logger.print2c("任务文件: " + taskFile);
			new CUIEngine(taskFile).start();
			Logger.close();
		} else {
			printUsage();
		}
	}

	private static void printUsage() {
		System.out.println("Usage: CUILoader <task_file>");
	}

}
