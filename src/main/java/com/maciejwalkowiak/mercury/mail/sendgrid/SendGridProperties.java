package com.maciejwalkowiak.mercury.mail.sendgrid;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sendgrid")
class SendGridProperties {
	/**
	 * SendGrid username
	 */
	private String username;

	/**
	 * SendGrid password
	 */
	private String password;

	/**
	 * Proxy configuration
	 */
	private Proxy proxy;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public boolean isProxyConfigured() {
		return proxy != null
				&& proxy.getHost() != null
				&& proxy.getPort() != null;
	}

	public static class Proxy {
		/**
		 * SendGrid proxy host
		 */
		private String host;

		/**
		 * SendGrid proxy port
		 */
		private Integer port;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}
	}
}
