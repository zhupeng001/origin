package wonder.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.*;

public class ThreadPool {
	protected final Log log = LogFactory.getLog(getClass());

	private String poolName = "default";
	private int corePoolSize = 2;
	private int maximumPoolSize = 5;
	private int keepAliveTime = 30;
	private long waitTime = 3000;
	private ThreadPoolExecutor executor;
	private int watchInterval = 300; // watchdog的间隔时间，如果为0，表示不监听。单位秒

	private WatchThread watchThread;
	public void init() {
		if (maximumPoolSize < corePoolSize)
			corePoolSize = maximumPoolSize;

		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
		RejectedExecutionHandler handler = new RejectedExecutionHandler() {
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				log.info(formatLog("WorkSize = " + getWorkSize()));
				try {
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
				}
				executor.execute(r);
			}
		};

		if (executor == null) {
			executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue, handler);
		}
		
		if(watchInterval > 0){
			watchThread = new LogWatchThread();
			watchThread.start();
		}
	}

	public long getWorkSize() {
		if (executor == null) {
			return -1;
		}
		return executor.getQueue().size();
	}

	public void execute(Runnable runnable) {
		if (executor == null)
			this.init();
		executor.execute(runnable);
	}

	private class LogWatchThread extends WatchThread {
		@Override
		protected void task() {
			try {
				sleep(watchInterval * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info(formatLog(String.format("run config : {corePoolSize:%s, maximumPoolSize:%s, keepAliveTime:%s, WorkSize:%s}", corePoolSize,
					maximumPoolSize, keepAliveTime, getWorkSize())));
		}
	}

	private String formatLog(String msg) {
		return "ThreadPool-" + poolName + ":" + msg;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public int getCorePoolSize() {
		return corePoolSize;
	}

	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}

	public int getMaximumPoolSize() {
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(int maximumPoolSize) {
		this.maximumPoolSize = maximumPoolSize;
	}

	public int getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}

	public void setExecutor(ThreadPoolExecutor executor) {
		this.executor = executor;
	}

	public int getWatchInterval() {
		return watchInterval;
	}

	public void setWatchInterval(int watchInterval) {
		this.watchInterval = watchInterval;
	}
}
