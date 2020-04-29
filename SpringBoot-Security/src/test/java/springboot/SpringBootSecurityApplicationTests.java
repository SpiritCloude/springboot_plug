package springboot;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import com.security.study.SpringBootSecurityApplication;
import com.security.study.bean.User;
import com.security.study.dao.UserMapper;

@ContextConfiguration(classes = SpringBootSecurityApplication.class)
@SpringBootTest
class SpringBootSecurityApplicationTests {
//
	@Resource
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMapper userMapper;
	
	@Test
	void passEncode() {
		System.out.println(passwordEncoder.encode("123"));

	}

	@Test
	void contextLoads() {
		// load 用户信息
		User userDetails = userMapper.findByUserName("admin");
		System.out.println(userDetails);
		// load 用户角色列表
		//List<String> roles = jwtUserMapper.findRoleByUserName("123");

		// load 通过角色列表加载资源权限列表
		//List<String> authorties = jwtUserMapper.findAuthByUserName(roles);
	}

}
