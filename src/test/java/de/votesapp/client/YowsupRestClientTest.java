package de.votesapp.client;

import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

public class YowsupRestClientTest {
	RestTemplate restTemplate = new RestTemplate();
	YowsupRestClient cut = new YowsupRestClient("base", restTemplate);

	@Test
	public void should_delete_message_after_fetching() {
		final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

		mockServer.expect(MockRestRequestMatchers.requestTo("base/groupMessage")) //
		.andExpect(MockRestRequestMatchers.method(HttpMethod.GET)) //
		.andRespond(MockRestResponseCreators.withSuccess("[{" + //
				" \"id\" : \"42\", " + //
				"\"group\" : \"41214214\", " + //
				"\"sender\" : \"41214214\", " + //
				"\"text\" : \"41214214\" " + //
				"}]", MediaType.APPLICATION_JSON));

		mockServer.expect(MockRestRequestMatchers.requestTo("base/groupMessage/42")) //
		.andExpect(MockRestRequestMatchers.method(HttpMethod.DELETE)) //
		.andRespond(MockRestResponseCreators.withSuccess("", MediaType.APPLICATION_JSON));

		final GroupMessage[] messages = cut.fetchGroupMessages();

		assertThat(messages, is(not(emptyArray())));
		mockServer.verify();

	}
}
