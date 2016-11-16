package com.lifetools.test.activemq;

import com.lifetools.commons.util.TimeUtils;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

/**
 * Created by Zheng.Ke
 * Date 2016/11/14.
 */
public class MyProducer {

    public void send() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.31.202:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("HELLO.QUEUE");
        MessageProducer messageProducer = session.createProducer(destination);

        TextMessage textMessage = session.createTextMessage("Hello, QUEUE. " + TimeUtils.getSimpleFullEngString(new Date()));
        messageProducer.send(textMessage);

        session.close();
        connection.close();
    }
}
