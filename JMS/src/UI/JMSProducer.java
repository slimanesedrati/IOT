package UI;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

public class JMSProducer {

    private Connection connection;
    private Session session;
    private MessageProducer producer;
    private Destination destination;
    private ActiveMQConnectionFactory connectionFactory;

    public JMSProducer(String destinationName) {
        try {
            // Create a connection factory
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);

            // Create a connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Define the destination (Queue or Topic)
            destination = session.createQueue(destinationName); // Change to createTopic for Topics

            // Create a producer
            producer = session.createProducer(destination);
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
            producer.send(message);

            // System.out.println("Queue: " + text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
