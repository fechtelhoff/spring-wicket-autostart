package de.fechtelhoff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"test"})
@SpringBootTest
class SpringWicketAutostartApplicationTests {

	@Test
	void contextLoads() {
		// To be continued
		Assertions.assertTrue(true);
	}
}
