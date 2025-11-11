package edu.dosw.rideci.HADES_COMMUNICATION_SECURITY_BACKEND;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
})
class HadesCommunicationSecurityBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
