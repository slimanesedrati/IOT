package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMSAppGUI {
    private JFrame frame;

    public JMSAppGUI() {
        frame = new JFrame("JMS Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel label = new JLabel("Enter Text:");
        JTextField textField = new JTextField();

                // create checkbox 
        JCheckBox c1 = new JCheckBox("Topic"); 
        JCheckBox c2 = new JCheckBox("Queue"); 

        JButton sendButton = new JButton("Send");
        JButton exitButton = new JButton("Exit");


        panel.add(c1);
        panel.add(c2);
        panel.add(label);
        panel.add(textField);
        panel.add(sendButton);
        panel.add(exitButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Implement code to send JMS message based on user input
                String text = textField.getText();

                if (c1.isSelected() && c2.isSelected() == false ) {
                    JMSPublisher publisher = new JMSPublisher("MyTopic"); // Specify your destination
                    publisher.sendMessage("from Topic: " + text);
                    publisher.close();
                }
                else if(c2.isSelected() && c1.isSelected() == false) {
                    JMSProducer producer = new JMSProducer("MyQueue"); // Specify your destination
                    producer.sendMessage("from Queue: " + text);
                    producer.close();
                }
                else if(c2.isSelected() && c1.isSelected() ) {
                    JMSPublisher publisher = new JMSPublisher("MyTopic"); // Specify your destination
                    publisher.sendMessage("from Topic : " + text);
                    publisher.close();
                    // 
                    JMSProducer producer = new JMSProducer("MyQueue"); // Specify your destination
                    producer.sendMessage("from Queue : " + text);
                    producer.close();
                };

                // Call your JMS message sending code here
                // For example, you can use your PublisherMessage class to send the message.
                // PublisherMessage.publish(text);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JMSAppGUI();
            }
        });
    }
}
