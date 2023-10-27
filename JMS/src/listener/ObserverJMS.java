package listener;

import java.util.Properties;
import java.util.Random;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.naming.Context;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class ObserverJMS {

    private ActiveMQTopic destination;
    protected MessageConsumer msg_consumer;
    protected Connection connexion;

    public ObserverJMS(int clientNumber) {
        try {
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
            ActiveMQConnectionFactory connectionfactory = new

            ActiveMQConnectionFactory("tcp://localhost:61616");
            connectionfactory.setTrustAllPackages(true);
            connexion = connectionfactory.createConnection();
            connexion.start();
            destination = new ActiveMQTopic("durableListener");
            Session session = connexion.createSession(true,

                    TopicSession.AUTO_ACKNOWLEDGE);

            msg_consumer = session.createConsumer(destination);
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
        new ObserverJMS(new Random().nextInt());
    }
}