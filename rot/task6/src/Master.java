import java.util.Random;

import org.zeromq.ZMQ;

public class Master {
	
	public static void main(String[] args) throws InterruptedException {
		// Prepare our context and publisher
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket publisher = context.socket(ZMQ.PUB);

		publisher.bind("tcp://*:5563");

		new Thread(new Runnable() {

			@Override
			public void run() {

			}
		}).start();
		
		new ListenerResults(context).start();

		Random randomGenerator = new Random();
		int a, b;
		String str;
		while (true) {
			publisher.send("B".getBytes(), ZMQ.SNDMORE);
			a = randomGenerator.nextInt(100);
			b = randomGenerator.nextInt(100);
			str = a + " " + b;
			publisher.send(str.getBytes(), 0);
			System.out.println("send: " + str);

			Thread.sleep(1000);

		}
	}

}

class ListenerResults extends Thread {
	private ZMQ.Context ctx;

	public ListenerResults(ZMQ.Context ctx) {
		super();
		this.ctx = ctx;
	}

	public void run() {
		ZMQ.Socket receiver = ctx.socket(ZMQ.PULL);
		receiver.connect("tcp://127.0.0.1:5564");
		String mess;
		System.out.println("start listening result");
		
		while (true) {
			mess = new String(receiver.recv(0));
			System.out.println("recv sum: " + mess);
		}
	}
}