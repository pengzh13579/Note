import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * ���������̳߳��ܹ����������ô���
 * ��һ��������Դ���ġ�ͨ���ظ������Ѵ������߳̽����̴߳�����������ɵ����ġ�
 * �ڶ��������Ӧ�ٶȡ������񵽴�ʱ��������Բ���Ҫ�ȵ��̴߳�����������ִ�С�
 * ����������̵߳Ŀɹ����ԡ�
 * �߳���ϡȱ��Դ����������ƵĴ���������������ϵͳ��Դ�����ή��ϵͳ���ȶ��ԡ�
 * ʹ���̳߳ؿ��Խ���ͳһ�ķ��䣬���źͼ�ء�
 * ����Ҫ��������������̳߳أ��������ԭ������ָ�ơ�
 * @author peng
 */
public class ThreadPoolExecutorDemo extends ThreadPoolExecutor {

	public ConcurrentHashMap<String, String> runningKeyMap = new ConcurrentHashMap<String, String>();
	
	// corePoolSize:ָ�����̳߳��е��߳�����������������������ӵ������ǿ����µ��߳�ȥִ�У����Ƿŵ�workQueue���������ȥ��
	// maximumPoolSize:ָ�����̳߳��е�����߳���������������������ʹ�õ�workQueue������е����ͣ������̳߳ػῪ�ٵ�����߳�������
	// keepAliveTime:���̳߳��п����߳���������corePoolSizeʱ��������̻߳��ڶ೤ʱ���ڱ����١�
	// unit:keepAliveTime�ĵ�λ��
	// workQueue:������У�����ӵ��̳߳��У�����δ��ִ�е�����
	// 	        ArrayBlockingQueue:���캯��һ��Ҫ����С��
	// 	        LinkedBlockingQueue:���캯��������С��Ĭ��Ϊ��Integer.MAX_VALUE ������������������ʱ��������� �ڴ�ľ���
	// 	        SynchronousQueue:ͬ�����У�һ��û�д洢�ռ���������� ��������ͬ�������������̡߳�
	// 	        PriorityBlockingQueue:���ȶ��С�
	// threadFactory:�̹߳��������ڴ����̣߳�һ����Ĭ�ϼ��ɡ�
	// handler:�ܾ����ԣ�������̫������������ʱ����ξܾ�����
	// 			AbortPolicy��Ĭ�ϣ�:ֱ������
	// 			CallerRunsPolicy:�õ����ߵ��߳�ִ������
	// 			DiscardOldestPolicy:������������õ�����
	// 			DiscardPolicy:������ǰ����
	public ThreadPoolExecutorDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}
	
	protected void beforeExecute(Thread t, Runnable r) {
		TaskRun task = (TaskRun) r;
		if (runningKeyMap.get(task.key) == null) {
			runningKeyMap.put(task.key, "");
			System.out.println("�߳�ִ�п�ʼ" + task.key);
		} else {
			((TaskRun) r).isRunning = false;
		}
	}

	protected void afterExecute(Runnable r, Throwable t) {
		TaskRun task = (TaskRun) r;
		runningKeyMap.remove(task.key);
		System.out.println("�߳�ִ�н���" + task.key);
	}
	public static void main(String[] args) {
		ThreadPoolExecutor threadPool = new ThreadPoolExecutorDemo(
				5, 
				5, 
				10, 
				TimeUnit.SECONDS, 
				new ArrayBlockingQueue<Runnable>(10, false), 
				Executors.defaultThreadFactory(), 
				new ThreadPoolExecutor.CallerRunsPolicy());
		
		// threadPool.getTaskCount()���̳߳��Ѿ�ִ�еĺ�δִ�е�������������Ϊͳ�ƵĹ����п��ܻᷢ���仯����ֵ�Ǹ�����ֵ��
		// threadPool.getCompletedTaskCount():����ɵ������������Ǹ�����ֵ����ֵС�ڵ���TaskCount��
		// threadPool.getLargestPoolSize()���̳߳�����������߳�����������ͨ����ֵ�ж��̳߳��Ƿ������������ֵ�����̳߳ص�����С�����ʾ�̳߳�����������
		// threadPool.getPoolSize()���̳߳ص�ǰ���߳�������
		// threadPool.getActiveCount()���̳߳��л���߳���������ִ�����񣩣��Ǹ�����ֵ��
		for (int i = 0; i < 10; i++) {
			threadPool.execute(new TaskRun("�߳�" + i));
		}
	}

}

class TaskRun implements Runnable {
	public String key; // �̱߳��
	public boolean isRunning = true;

	public TaskRun(String key) {
		this.key = key;
	}

	public void run() {
		if (isRunning) {
			System.out.println("ִ��" + key);
		}
	}
}
