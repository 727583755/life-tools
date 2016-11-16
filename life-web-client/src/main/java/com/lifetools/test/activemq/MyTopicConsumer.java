package com.lifetools.test.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Zheng.Ke
 * Date 2016/11/14.
 */
public class MyTopicConsumer {
    public void receive() throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.202:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topic = session.createTopic("HELLO.TOPIC");
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Thread.sleep(Long.MAX_VALUE);

    }
}
