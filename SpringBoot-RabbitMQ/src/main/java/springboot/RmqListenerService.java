package springboot;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import springboot.bean.People;

@Service
public class RmqListenerService {
	
	//自动应答ack消费
	@RabbitListener(queues = "queue.news")
	public void receive(@Payload String msg) {
		System.out.println("RmqListenerService。。。msg" + msg);
	}
}

