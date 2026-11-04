import org.zeromq.ZMQ;

public class Worker {

	public static void main(String[] args) throws InterruptedException {

		// Prepare our context and subscriber
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);

		subscriber.connect("tcp://localhost:5563");
		subscriber.subscribe("B".getBytes());
		
		ZMQ.Socket sender = context.socket(ZMQ.PUSH);		
		sender.bind("tcp://127.0.0.1:5564");
		
		String[] arr;
		int sum;
		while (true) {
			// Read envelope with address
			String address = new String(subscriber.recv(0));
			// Read message contents
			String contents = new String(subscriber.recv(0));
			System.out.println("recv: " + contents);
			arr = contents.split(" ");
			
			sum = Integer.valueOf(arr[0]) + Integer.valueOf(arr[1]);
			sender.send(Integer.toString(sum).getBytes(), 0);
			System.out.println("send sum : " + sum);
			
		}
	}
}