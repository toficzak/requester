package http.client;

import krzysztof.property.reader.PropertyReader;

public enum UrlCreator {
	INSTANCE;

	PropertyReader pr = new PropertyReader("config.properties", this.getClass().getClassLoader());
	String host = pr.getProperty("host");
	String api = pr.getProperty("api");

	public String getBaseUrl() {
		return this.host + this.api;
	}
}
