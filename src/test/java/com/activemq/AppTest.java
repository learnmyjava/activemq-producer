package com.activemq;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * 点对点
	 * 将消息发送到mq队列存储
	 */
	@Test
	public void mqQueue(){
		
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("class:spring/applicationContext-jms.xml");
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		// 第二步：从容器中获得JMSTemplate对象。
//		JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");

			// 第三步：从容器中获得一个Destination对象
			Queue queue = (Queue) applicationContext.getBean("txnQueue");
			// 第四步：使用JMSTemplate对象发送消息，需要知道Destination
			jmsTemplate.send(queue, new MessageCreator() {
				
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage("hello activmq this is message");
					return textMessage;
				}
			});
		}
				
		long endtime = System.currentTimeMillis();
		System.out.println("总耗时:"+(endtime-starttime)+"ms");

	}
	
}
