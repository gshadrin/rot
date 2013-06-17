package chat;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class JMSApplication{
    
    private static GUI gui;
    
    
    private static ActiveMQConnectionFactory connectionFactory=null;
    
    private static Connection connection=null;
    
    private static Session session;
    
    private static Destination destination;
    
    private static String queue= "Chat1";
    public static void main(String[] args) {
                           
                gui = new GUI();
                gui.setVisible(true);
                
                listening();                
               
                gui.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if (connection!=null){
                            try {
                                connection.close();
                            } catch (JMSException ex) {
                                Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    
                });
    }
    
    public static void sendMessage(String mess){
        if (Connected() && !mess.equals("")){
            destination=getDestinationTopic();
            if (destination!=null){
                try {
                	
                    MessageProducer producer = session.createProducer(destination);
                    TextMessage message =session.createTextMessage(mess);
                    producer.send(message, DeliveryMode.NON_PERSISTENT, 0, 100000);
                } catch (JMSException ex) {
                    Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
            }}
    }
    
    
    public static void listening(){
        if (Connected()){
           
                destination=getDestinationTopic();
                
                
           
           if (destination!=null){
                try {
                	
                    MessageConsumer consumer=session.createConsumer(destination);              
                    consumer.setMessageListener(new MessageListener() {

                        @Override
                        public void onMessage(Message msg) {
                            TextMessage textmessage=(TextMessage)msg;
                            try {
                                gui.addMessage(textmessage.getText());
                            } catch (JMSException ex) {
                                Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    
                } catch (JMSException ex) {
                    Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
                   
                }
           }
        }
    }
  
    public static Boolean Connected(){
        try {
            if (connection==null){
                connectionFactory=getConnectionFactory();
                connection=connectionFactory.createConnection(); 
                connection.start(); 
                session =((ActiveMQConnection) connection).createSession(false, Session.AUTO_ACKNOWLEDGE); 
            }else{
                connection.start();
            }
            return true;
        } catch (JMSException ex) {
            Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
   
    private static ActiveMQConnectionFactory getConnectionFactory(){
        return new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                                             ActiveMQConnection.DEFAULT_PASSWORD,
                                             "failover://tcp://localhost:61616");
    }

 
    private static Destination getDestinationTopic(){
        try {
            return session.createTopic(queue);
        } catch (JMSException ex) {
            Logger.getLogger(JMSApplication.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
}