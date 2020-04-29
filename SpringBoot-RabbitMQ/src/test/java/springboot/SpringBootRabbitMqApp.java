package springboot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Return;
import com.rabbitmq.client.ReturnCallback;
import com.rabbitmq.client.ReturnListener;

import springboot.bean.People;

@SpringBootTest
class SpringBootRabbitMqApp {
	/*****1.异步      2.应用解耦      3.流量削峰 
	
	 * queue 消息队列
			1.point----point 点对点
			2.topic 发布订阅
	 *
	 * exchange 类型
			1.direct  直连
		 	2.topic   发布订阅
		 	3.fanout  广播

	 * publisher----------->  exchange ----Binding------->Queue <-----------consumer
	 * 	                               ----Binding------->Queue
	 * 	                               ----Binding------->Queue
	 */
	@Autowired
	RabbitTemplate rabbitTemplate;

	/* 默认java-serialized-object 
	 */
	@Test
	void msgSend1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "exchange-routingKey-object");
		rabbitTemplate.convertAndSend("amq.direct", "news", map);
		System.out.println("msgSend");
	}
	
	@Test
	void msgSend2() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "exchange-routingKey-object");
		rabbitTemplate.convertAndSend("amq.direct", "news", new People("haha", 18));
		System.out.println("msgSend");
	}
	
	@Test
	void msgReceive() {
//		Object object = rabbitTemplate.receive("queue.news");
//		System.out.println(object.getClass());
//		System.out.println(object);
		
		People p = (People)rabbitTemplate.receiveAndConvert("queue.news");
		System.out.println(p.getClass());
		System.out.println(p);
	}

}
