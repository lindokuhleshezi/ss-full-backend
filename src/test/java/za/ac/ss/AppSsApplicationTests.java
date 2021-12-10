package za.ac.ss;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import za.ac.ss.exception.ResourceNotFoundException;

@SpringBootTest
class AppSsApplicationTests {

	
	@Test
	void contextLoads() throws ResourceNotFoundException {
		//System.out.println(String.format("Properties %s", properties));
	}

}
