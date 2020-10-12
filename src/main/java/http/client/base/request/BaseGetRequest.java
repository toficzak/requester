package http.client.base.request;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeader;

import http.client.response.Response;

public abstract class BaseGetRequest extends BaseRequest {

	protected String endpoint;

	public BaseGetRequest(String endpoint) {
		super(endpoint, Collections.emptyMap(), Collections.emptyMap());
	}

	public BaseGetRequest(String endpoint, Map<String, String> pathParams, Map<String, String> queryParams) {
		super(endpoint, pathParams, queryParams);
	}

	public Response execute() throws URISyntaxException {
		HttpGet request = new HttpGet(super.generateUrl());
		BasicHeader h = new BasicHeader("Content-Type", "application/json");
		List<Header> headers = new ArrayList<>();
		headers.add(h);
		return super.httpClient.execute(request, headers);
	}
}
