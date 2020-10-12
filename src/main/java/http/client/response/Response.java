package http.client.response;

import com.google.gson.JsonObject;

public class Response {
	public int statusCode;
	public JsonObject json;

	public Response(int statusCode, JsonObject json) {
		super();
		this.statusCode = statusCode;
		this.json = json;
	}
}
