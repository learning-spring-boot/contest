package de.votesapp.client;

import java.net.URI;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

/**
 * Found here: http://stackoverflow.com/questions/21998405/spring-4-0-0-basic-
 * authentication-with-resttemplate
 */
public class AuthHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

	protected HttpHost host;
	protected String userName;
	protected String password;

	public AuthHttpComponentsClientHttpRequestFactory(final HttpHost host, final String userName, final String password) {
		super();
		this.host = host;
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
		// Create AuthCache instance
		final AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		final BasicScheme basicAuth = new BasicScheme();
		authCache.put(host, basicAuth);

		// Add AuthCache to the execution context
		final HttpClientContext localcontext = HttpClientContext.create();
		localcontext.setAuthCache(authCache);

		if (userName != null) {
			final BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
			credsProvider.setCredentials(new AuthScope(host), new UsernamePasswordCredentials(userName, password));
			localcontext.setCredentialsProvider(credsProvider);
		}
		return localcontext;
	}

}
