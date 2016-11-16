package com.lifetools.test.activemq;

import javax.jms.JMSException;

/**
 * Created by Zheng.Ke
 * Date 2016/11/14.
 */
public class HelloMain {
    public static void main(String[] args) throws InterruptedException, JMSException {
        MyTopicConsumer myConsumer = new MyTopicConsumer();
        myConsumer.receive();
    }
}
