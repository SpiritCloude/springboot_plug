package springboot;

import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class RedissonSaveInfo {
/*============================ redisson快速存取对象===============================================*/	

	
	@Autowired
	RedissonClient redissonClient;

	/**
	 * 	 购物车存在redis
	 */
	@Test
	public void putObject(){
		RMap<String, Object> cart = redissonClient.getMap("cart");
		Pms phone = new Pms("123","1000","手机");
		Pms ears = new Pms("124","80","耳机");
		List<Pms> list = new ArrayList<Pms>();
		list.add(phone);
		list.add(ears);
		
		String ee= JSON.toJSONString(list);
		System.out.println(ee);
		cart.put("cart:userid", ee);
	}
	
	@Test
	public void getObject() {
		RMap<Object, Object> cart = redissonClient.getMap("cart:userid");
		
		ArrayList<Pms> list = (ArrayList<Pms>) cart.get("cart:userid");
		list.forEach(System.out::println);
		
	}
}

class Pms implements Serializable{
	String id;
	String price;
	String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Pms(String id, String price, String name) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
	}
	@Override
	public String toString() {
		return "Pms [id=" + id + ", price=" + price + ", name=" + name + "]";
	}
}