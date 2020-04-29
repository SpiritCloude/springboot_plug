package springboot;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

	@Autowired
	RabbitTemplate rabbitTemplate;

	/**
	 * 消息发送成功的回调
	 * 需要开启
	 * # 开启发送确认消息有没有收到
	 * spring.rabbitmq.publisher-confirm-type=CORRELATED
	 **/
	ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
		@Override
		public void confirm(CorrelationData correlationData, boolean ack, String cause) {
			System.out.println("异常处理...消息id" + correlationData + "ack:" + ack);
			if (!ack) {
				System.out.println("异常处理...");
			}
		}
	};
	
	/**
	 * 发生异常时的消息返回提醒
	 * 需要开启
	 * # 开启发送失败退回（找不到exchange/routingKey等）
	 * spring.rabbitmq.publisher-returns=true
	 **/
	ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {

		@Override
		public void returnedMessage(org.springframework.amqp.core.Message message,
				int replyCode,
				String replyText,
				String exchange,
				String routingKey) {
			System.out.println("reurn exchange...");
		}
		
	};
	
	public void send(Object messgae, Map<String, Object> properties) {
		MessageHeaders messageHandler = new MessageHeaders(properties);
		Message msg = MessageBuilder.createMessage(messgae, messageHandler);
		//全局唯一id
		CorrelationData id = new CorrelationData(TimeUnit.MILLISECONDS.toString());
		rabbitTemplate.convertAndSend("exchange", "routingKey", msg, id);
	}

}
