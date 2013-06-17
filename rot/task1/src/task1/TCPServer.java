package task1;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.StringTokenizer;

public class TCPServer
{
    public final static int PORT = 40000;
    
    private static int st_counter = 0; 

    /**
     * @author gshadrin
     */
    public static void main(String [] args)
    {
        try
        {
        	System.out.println("Start");
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true)
            {
                Socket socket = serverSocket.accept();
                st_counter++;
                ClientServant servant = new ClientServant(socket, st_counter);
                new Thread(servant).start();
            }
        }
        catch (Exception ex)
        { 
            ex.printStackTrace();
        }
    }
        
    public static void closeStream(Closeable stream)
    {
        if (stream != null)
        {
            try
            {
                stream.close();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    static class ClientServant
        implements Runnable
    {
        private Socket m_socket;
        private int m_iID;
        
        public ClientServant(Socket socket, int iID)
        {
            m_socket = socket;
            m_iID = iID;
        }

        public void run()
        {
            try
            {
                InputStream is = m_socket.getInputStream();
                OutputStream os = m_socket.getOutputStream();
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "Cp1251"));
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(os, "Cp1251"));
                    // закроем сразу
                    //while (true)
                    //{
                        StringTokenizer strtok = new StringTokenizer(reader.readLine(), " ");
                        int val1 = Integer.parseInt(strtok.nextToken());
                        int val2 = Integer.parseInt(strtok.nextToken());
                        
                        pw.println(Integer.toString(val1 + val2));
                        pw.flush();
                    //}
                }
                finally
                {
                    closeStream(os);
                    closeStream(is);
                    m_socket.close();
                }
            }
            catch (Exception ex)
            {
                System.err.println(MessageFormat.format(
                    "client {0} terminated with error: {1}", 
                    new Object []{String.valueOf(m_iID), ex}));
            }
        }
        
        
    }
}