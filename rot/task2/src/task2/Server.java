package task2;

import helma.xmlrpc.WebServer;

import java.io.IOException;

public class Server 
{
	private final static int PORT = 40000;

	public static void main(String[] args) 
	{
		try 
		{
			// Start the server, using built-in version
			System.out.println("Attempting to start XML-RPC Server...");
			WebServer server = new WebServer(PORT);

			System.out.println("Started successfully.");

			// Register our handler class as area
			server.addHandler("solver", new Solver());

		} catch (IOException e) 
		{
			System.out.println("Could not start server: " + e.getMessage());
		}
	}
}
