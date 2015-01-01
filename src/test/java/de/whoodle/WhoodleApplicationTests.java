package de.whoodle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.votesapp.VotesAppApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VotesAppApplication.class)
@WebAppConfiguration
public class WhoodleApplicationTests {

	@Test
	public void contextLoads() {
	}

}
