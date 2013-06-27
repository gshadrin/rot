package task8;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {
	private final static String QUEUE_NAME = "TASK_8";

	public static void main(String[] argv) throws java.io.IOException,
			java.lang.InterruptedException 
	{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		QueueingConsumer consumer = new QueueingConsumer(channel);
		channel.basicConsume(QUEUE_NAME, true, consumer);

		while (true) {
			QueueingConsumer.Delivery delivery = consumer.nextDelivery();
			String message = new String(delivery.getBody());

			System.out.println("Received '" + message + "'");
			int res = doWork(message);
			System.out.println("Done. Result " + res);
		}
	}

	private static int doWork(String task) throws InterruptedException 
	{
		String[] arr = task.split(" ");
		int a = Integer.parseInt(arr[0]);
		int b = Integer.parseInt(arr[1]);
		return a + b;
	}
}
