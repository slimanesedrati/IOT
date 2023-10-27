package UI;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class JMSPublisher {

    private Connection connection;
    private Session session;
    private MessageProducer publisher;
    private Destination destination;
    private ActiveMQConnectionFactory connectionFactory;

    public JMSPublisher(String destinationName) {
        try {
            // Create a connection factory
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

            // Create a connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Define the destination (Queue or Topic)
            destination = session.createTopic(destinationName); // Change to createTopic for Topics

            // Create a producer
            publisher = session.createProducer(destination);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text) {
        try {
            // Create a text message (you can change this to ObjectMessage for more complex data)
            ObjectMessage message = session.createObjectMessage();
            message.setObject(new MyMsg(text)); // Assuming MyMsg is a class with a constructor that takes text

            // Send the message
            publisher.send(message);

            // System.out.println("Message sent: " + text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            publisher.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
