package springboot;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


public class JwtTest {
/*	hrader{
			"alg": "HS256",
			"typ": "JWT"}
		
	Payload{
  			"sub": "1234567890",
  			"name": "John Doe",
  			"admin": true}
  		registered claims ：这是一组预定义声明，不是强制性的，但建议使用，以提供一组有用的，可互操作的声明。
		其中包括： iss（发行人）， exp（到期时间），sub（主题）， aud（受众）。
	
	Signature
		HMACSHA256(
	  		base64UrlEncode(header) + "." +
	  		base64UrlEncode(payload),
	  		secret)	
	  		
	--在response的header中添加
			Authorization: Bearer <token>
*/
	@Test
	public void generate() {
		long nowMillis = System.currentTimeMillis();
        
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
       
        //创建payload的私有声明
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", "username");

        //生成签名的时候使用的秘钥secret,秘钥不能外露 指定签名的时候使用的签名算法，也就是header那部分
        byte[] keyBytes = Decoders.BASE64.decode("");
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        //生成签发人
        String subject = "admin";

        //下面就是在为payload添加各种标准声明
        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
        		//如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(new Date())
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(key);
        long ttlMillis = System.currentTimeMillis()+ 15 * 60 * 1000;
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
		System.err.println( builder.compact());
	}
	
	/**
	 *  生成key */
	@Test
	public void generateBASE64() {
		byte[] mima = Base64.getEncoder().encode("My JWT token secret=".getBytes());
		System.err.println(mima.toString());
	}
}
