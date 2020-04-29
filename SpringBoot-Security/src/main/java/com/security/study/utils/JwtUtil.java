package com.security.study.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import com.security.study.bean.User;


public class JwtUtil {
	//私钥
	String TOKEN_SECRET = "";
   
	/**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法
     *
     */
    public String createJWT(long ttlMillis, User user) {
        
        long nowMillis = System.currentTimeMillis();
        
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
       
        //创建payload的私有声明
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", user.getUsername());

        //生成签名的时候使用的秘钥secret,秘钥不能外露 指定签名的时候使用的签名算法，也就是header那部分
        byte[] keyBytes = Decoders.BASE64.decode(TOKEN_SECRET);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        
        //生成签发人
        String subject = user.getUsername();

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
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
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * Token的解密
     * @param token 加密后的token
     * @param user  用户的对象
     * @return body
     */
    public Claims parseJWT(String token, User user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        //得到DefaultJwtParser
        Claims claims = Jwts.parserBuilder()
                //设置签名的秘钥
                .setSigningKey(TOKEN_SECRET)
                //设置需要解析的jwt
                .build().parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 校验token
     * @param token
     * @param user
     * @return Boolean
     */
    public Boolean isVerify(String token, User user) {
        //签名秘钥，和生成的签名的秘钥一模一样
        String key = user.getPassword();

        try {
			Claims claims = Jwts.parserBuilder()
                //设置签名的秘钥
                .setSigningKey(key)
                //设置需要解析的jwt
                .build().parseClaimsJws(token)
                .getBody();

			if (claims.get("username").equals(user.getPassword())) {
				return true;
			}
		} catch (Exception e) {
			 return false;
		}

        return false;
    }

}
