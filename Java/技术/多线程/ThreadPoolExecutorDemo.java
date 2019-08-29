import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/***
 * 合理利用线程池能够带来三个好处。
 * 第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗。
 * 第二：提高响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行。
 * 第三：提高线程的可管理性。
 * 线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性。
 * 使用线程池可以进行统一的分配，调优和监控。
 * 但是要做到合理的利用线程池，必须对其原理了如指掌。
 * @author peng
 */
public class ThreadPoolExecutorDemo extends ThreadPoolExecutor {

	public ConcurrentHashMap<String, String> runningKeyMap = new ConcurrentHashMap<String, String>();
	
	// corePoolSize:指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去。
	// maximumPoolSize:指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量。
	// keepAliveTime:当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁。
	// unit:keepAliveTime的单位。
	// workQueue:任务队列，被添加到线程池中，但尚未被执行的任务。
	// 	        ArrayBlockingQueue:构造函数一定要传大小。
	// 	        LinkedBlockingQueue:构造函数不传大小会默认为（Integer.MAX_VALUE ），当大量请求任务时，容易造成 内存耗尽。
	// 	        SynchronousQueue:同步队列，一个没有存储空间的阻塞队列 ，将任务同步交付给工作线程。
	// 	        PriorityBlockingQueue:优先队列。
	// threadFactory:线程工厂，用于创建线程，一般用默认即可。
	// handler:拒绝策略，当任务太多来不及处理时，如何拒绝任务。
	// 			AbortPolicy（默认）:直接抛弃
	// 			CallerRunsPolicy:用调用者的线程执行任务
	// 			DiscardOldestPolicy:抛弃队列中最久的任务
	// 			DiscardPolicy:抛弃当前任务
	public ThreadPoolExecutorDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}
	
	protected void beforeExecute(Thread t, Runnable r) {
		TaskRun task = (TaskRun) r;
		if (runningKeyMap.get(task.key) == null) {
			runningKeyMap.put(task.key, "");
			System.out.println("线程执行开始" + task.key);
		} else {
			((TaskRun) r).isRunning = false;
		}
	}

	protected void afterExecute(Runnable r, Throwable t) {
		TaskRun task = (TaskRun) r;
		runningKeyMap.remove(task.key);
		System.out.println("线程执行结束" + task.key);
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
		
		// threadPool.getTaskCount()：线程池已经执行的和未执行的任务总数，因为统计的过程中可能会发生变化，该值是个近似值；
		// threadPool.getCompletedTaskCount():已完成的任务数量，是个近似值，该值小于等于TaskCount；
		// threadPool.getLargestPoolSize()：线程池曾经的最大线程数量，可以通过该值判断线程池是否满过。如该数值等于线程池的最大大小，则表示线程池曾经满过；
		// threadPool.getPoolSize()：线程池当前的线程数量；
		// threadPool.getActiveCount()：线程池中活动的线程数（正在执行任务），是个近似值。
		for (int i = 0; i < 10; i++) {
			threadPool.execute(new TaskRun("线程" + i));
		}
	}

}

class TaskRun implements Runnable {
	public String key; // 线程编号
	public boolean isRunning = true;

	public TaskRun(String key) {
		this.key = key;
	}

	public void run() {
		if (isRunning) {
			System.out.println("执行" + key);
		}
	}
}
