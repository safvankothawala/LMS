package com.lms.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@SpringBootConfiguration
@ComponentScan(basePackages = "com.lms.app")
class LmsApplicationTests {

	@Test
	void contextLoads() {
	}
	
	

}
