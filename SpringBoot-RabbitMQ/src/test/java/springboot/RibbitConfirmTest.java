package springboot;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.ReturnListener;

import springboot.bean.People;

@SpringBootTest
public class RibbitConfirmTest {
	
	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	void msgSend2() {	
		Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(false);
		try {
			//消息确认模式
			channel.confirmSelect();
			
			//发消息mandatory true if the 'mandatory' flag is to be set will be return
			channel.basicPublish("amq.direct", "news", true, null, JSON.toJSONString(new People("haha", 18)).getBytes());
			
			//添加确认监听
			channel.addConfirmListener(new ConfirmListener() {

				@Override
				public void handleAck(long deliveryTag, boolean multiple) throws IOException {
					System.err.println("send success...");
				}

				@Override
				public void handleNack(long deliveryTag, boolean multiple) throws IOException {
					System.err.println("send false...");
				}
			});
			
			//发送失败返回的
			channel.addReturnListener(new ReturnListener() {

				@Override
				public void handleReturn(int replyCode,
			            String replyText,
			            String exchange,
			            String routingKey,
			            AMQP.BasicProperties properties,
			            byte[] body) throws IOException {
					// TODO Auto-generated method stub
					System.err.println(replyText + exchange + routingKey);
				}
				
			});
			
		} catch (IOException e) {
			// rabbit异常
			e.printStackTrace();
		}
	}
	
	
	@Test
	void msgSend3() {
		Channel channel = rabbitTemplate.getConnectionFactory().createConnection().createChannel(true);
			try {
				
				channel.txSelect();//开启事务
				
				channel.basicPublish("amq.direct", "news", null, "msg".getBytes());
				
				channel.txCommit();//提交事务
			} catch (IOException e) {
				try {
					//回滚事务
					channel.txRollback();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			System.out.println("msgSend");
	}
	
}
