package com.lifetools.test.activemq;

import org.junit.Test;

import javax.jms.JMSException;

/**
 * http://ifeve.com/java-reflection/
 * Created by Zheng.Ke
 * Date 2016/11/8.
 */
public class HelloTest {

    @Test
    public void test1() throws JMSException {
        MyProducer myProducer = new MyProducer();
        myProducer.send();
    }

    @Test
    public void test2() throws JMSException {
        MyConsumer myConsumer = new MyConsumer();
        myConsumer.receive();
    }

    @Test
    public void test3() throws JMSException {
        MyTopicProducer myProducer = new MyTopicProducer();
        myProducer.send();
    }

    @Test
    public void test4() throws JMSException, InterruptedException {
        MyTopicConsumer myConsumer = new MyTopicConsumer();
        myConsumer.receive();
    }

    @Test
    public void test5() {

    }

    @Test
    public void test6() {

    }

    @Test
    public void test7() {

    }

    @Test
    public void test8() {

    }

    @Test
    public void test9() {

    }

}
