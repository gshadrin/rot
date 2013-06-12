package task8;
import java.util.Random;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Master {
	private final static String QUEUE_NAME = "TASK_8";

	private static Random random = new Random();

	public static void main(String[] argv) throws java.io.IOException 
	{

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);

		while (true) {
			String message = getMessage();
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

			System.out.println("Sent '" + message + "'");
		}

		// channel.close();
		// connection.close();
	}

	private static String getMessage() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append(random.nextInt()).append(" ").append(random.nextInt());
		return builder.toString();
	}

}
