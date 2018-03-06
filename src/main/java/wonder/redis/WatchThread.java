package wonder.redis;


public abstract class WatchThread extends Thread
{
	private boolean alive = true;

	public void kill()
	{
		alive = false;
	}

	public final void run()
	{
		while (alive)
		{
			try
			{
				task();
			} catch (Throwable t)
			{
				t.printStackTrace();
			}
		}
	}

	protected abstract void task();

	public boolean isAvailable()
	{
		return alive;
	}
	
	public static void main(String[] args){
		Thread t1 = new WatchThread(){
			@Override
			protected void task() {
				int id = 123;
				String filename = id + "" ;
				synchronized(filename){
					try {
						System.out.println("thread1: " + System.currentTimeMillis());
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread t2 = new WatchThread(){
			@Override
			protected void task() {
				int id = 123;
				String filename = 123 + "" ;
				synchronized(filename){
					try {
						System.out.println("thread2: " + System.currentTimeMillis());
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread t3 = new WatchThread(){
			@Override
			protected void task() {
				int id = 123;
				String filename = 123 + "" ;
				synchronized(filename){
					try {
						System.out.println("thread3: " + System.currentTimeMillis());
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
//		t1.start();
		t2.start();
		t3.start();
	}
}