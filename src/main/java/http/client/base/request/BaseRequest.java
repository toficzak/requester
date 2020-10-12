package http.client.base.request;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeader;

import http.client.HttpClient;
import http.client.UrlCreator;
import http.client.response.Response;

abstract class BaseRequest {

	protected HttpClient httpClient = HttpClient.INSTANCE;
	private UrlCreator urlCreator = UrlCreator.INSTANCE;

	protected String url;
	private Map<String, String> pathParams;
	private Map<String, String> queryParams;
	private List<Header> headers = new ArrayList<>();

	public BaseRequest(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
		this.url = urlCreator.getBaseUrl() + endpoint;
		this.pathParams = pathParams;
		this.queryParams = queryParams;
	}

	public void addHeader(String key, String value) {
		this.headers.add(new BasicHeader(key, value));
	}

	public Response execute(HttpRequestBase request) {
		return HttpClient.INSTANCE.execute(request, this.headers);
	}

	public String generateUrl() throws URISyntaxException {

		URIBuilder builder = new URIBuilder(this.url);

		if (queryParams != null && !queryParams.isEmpty()) {
			for (Entry<String, String> param : queryParams.entrySet()) {
				builder.addParameter(param.getKey(), param.getValue());
			}
		}

		String fullUrl = builder.build().toString();
		if (pathParams != null && !pathParams.isEmpty()) {
			for (Entry<String, String> param : pathParams.entrySet()) {
				fullUrl = fullUrl.replace(param.getKey(), param.getValue());
			}
		}
		return fullUrl;
	}
}
