package com.shudailaoshi.service.sys.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-core.xml", "classpath:spring-mybatis.xml" })
public class UserServiceImplTest {

	@Test
	public void testSave() {
		try {
			// System.out.println(this.userDao.getField(new User("cmsleo"),
			// "user_name").getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
