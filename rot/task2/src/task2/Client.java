package task2;

import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Client
{
	
	 public static void main(String args[]) 
	 {
		 try 
		 {
	         // Create the client, identifying the server
	         XmlRpcClient client =
	             new XmlRpcClient("http://localhost:40000/");
	         
	         System.out.println("Введите параметры квадратного урванения ax^2 + by + c через пробел");
	         
	         StringTokenizer strtok = new StringTokenizer(new Scanner(System.in).nextLine(), " ");
	         
	         // Create the request parameters using user input
	         Vector<Double> params = new Vector<Double>();
	         params.addElement(Double.parseDouble(strtok.nextToken()));
	         params.addElement(Double.parseDouble(strtok.nextToken()));
	         params.addElement(Double.parseDouble(strtok.nextToken()));
	         
	         // Issue a request
	         @SuppressWarnings("unchecked")
			Hashtable<String, Double>  result = (Hashtable<String, Double> )
	            client.execute("solver.solve", params);
	
	         // Report the results
	         System.out.println(result);
	     } 
		 catch (IOException e) 
		 {
	         System.err.println("IO Exception: " + e);
	     } catch (XmlRpcException e) 
	     {
	         System.err.println("Exception within XML-RPC: " + e);
	     }
	 }
}
