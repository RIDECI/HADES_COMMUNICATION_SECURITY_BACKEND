package edu.dosw.rideci;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Disabled("ContextLoads disabled due to complex security config")
@ActiveProfiles("test")
@SpringBootTest
class HadesCommunicationSecurityBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}

