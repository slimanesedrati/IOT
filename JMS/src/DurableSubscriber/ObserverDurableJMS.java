package DurableSubscriber;

import java.util.Random;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class ObserverDurableJMS {
    private ActiveMQTopic destination;
    protected MessageConsumer msg_consumer;
    protected Connection connexion;
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;

    public ObserverDurableJMS(int clientNumber) {
        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connectionFactory.setTrustAllPackages(true);
            connection = connectionFactory.createConnection();
            connection.setClientID("lyazidSab");
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = new ActiveMQTopic("durableListener");
            connectionFactory.setTrustAllPackages(true);
            msg_consumer = session.createDurableSubscriber(destination, "lyazidSab");
            msg_consumer.setMessageListener(new ConsumerMsgListener());
            System.out.println("je suis le client #" + clientNumber);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println(clientNumber + ": ++++++++++");
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ObserverDurableJMS(new Random().nextInt());
    }
}