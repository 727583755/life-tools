package com.lifetools.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Zheng.Ke
 * Date 2016/11/14.
 */
public class MyConsumer {
    public void receive() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.202:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("HELLO.QUEUE");
        MessageConsumer messageConsumer = session.createConsumer(destination);

        Message message = messageConsumer.receive();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println(textMessage.getText());
        }

        messageConsumer.close();
        session.close();
        connection.close();
    }
}
