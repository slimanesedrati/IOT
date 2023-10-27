package QTlistner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class PublisherMessage {
    ActiveMQConnectionFactory connectionFactory;
    Connection connection = null;
    Session session = null;
    Destination destination;
    Message message;
    boolean useTransaction = false;
    MessageProducer producer = null;

    public PublisherMessage() {
        try {
            connectionFactory = new ActiveMQConnectionFactory();
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("MyTopic");
            producer = session.createProducer(destination);
            publish();
        } catch (JMSException jmsEx) {
        } finally {
            try {
                producer.close();
                session.close();
                connection.close();
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void publish() throws JMSException {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String ligne = null;
        while (true) {
            System.out.println("Saisir votre texte:");
            try {
                MyMsg msg = new MyMsg();
                ligne = entree.readLine();
                msg.setTexte(ligne);
                ObjectMessage _message = session.createObjectMessage(msg);
                producer.send(_message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PublisherMessage();
    }
}