package task1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient
{

    /**
     * @author gshadrin
     */
    public static void main(String [] args)
    {
        try
        {
//            InetAddress address = InetAddress.getLocalHost();
//            InetAddress address = InetAddress.getByName("localhost");
            InetAddress address = InetAddress.getByAddress(new byte [] {127,0,0,1});
            Socket socket = new Socket(address, TCPServer.PORT);
            
            System.out.println("Введите два числа, разделенных пробелом:");        
            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
                        
            try
            {
            	PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "Cp1251"));
            	BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "Cp1251"));    
            	
            	pw.println(str);    
            	pw.flush();        
	            System.out.println(socketReader.readLine());
            }
            finally
            {
            	System.out.println("Закрываем сокет");
                socket.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}