package DurableSubscriber;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class ConsumerMsgListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        ObjectMessage myobject = (ObjectMessage) message;
        MyMsg mess = null;
        try {

            mess = (MyMsg) myobject.getObject();
            System.out.println(" message received :" + mess.getTexte());
        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}