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
@RabbitListener(queues = "queue.news")
public class RmqConfirmListener {
	/** 
	 * 
	 * 开启手动ack应答消费 
	 * listener.direct.acknowledge-mode: manual
	 */
	@RabbitHandler
	public void receive2(People people, Message msg, Channel channel) throws IOException {
		try {
			//逻辑处理
			System.out.println("RmqListenerService。。。people" + people);
			
			//手动ack当前消息
			channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
			System.out.println("手动ack当前消息");
		} catch (Exception e) {
			// ack或者逻辑  异常执行unack
			channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, true);
			//channel.basicReject(msg.getMessageProperties().getDeliveryTag(), true);
		}
		
	}
	
}

