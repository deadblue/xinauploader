package cn._2dland.tools.uploader.task;

/**
 * 任务队列
 * @author deadblue
 */
public class TaskQueue {

	/** 当前节点 */
	private TaskNode current = null;
	/** 最后节点 */
	private TaskNode last = null;
	/** 剩余节点数 */
	private int remainCount = 0;

	public TaskQueue() {}

	public void addTask(String videoFile, int times) {
		TaskNode node = new TaskNode(videoFile, times);
		if(last != null) {
			last.setNext(node);
		} else {
			current = node;
		}
		last = node;
		remainCount ++;
	}

	public TaskNode getNextTask() {
		TaskNode node = current;
		if(node != null) {
			// 返回当前节点，并将指针移向下一个
			current = node.getNext();
			// 计数器减一
			remainCount --;
		}
		return node;
	}

	public int getRemainTaskCount() {
		return remainCount;
	}

}
