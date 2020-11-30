package http.client.base.request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import com.google.gson.JsonObject;
import http.client.response.Response;

public abstract class BasePostRequest extends BaseRequest {

  public BasePostRequest(String endpoint, JsonObject jsonPayload) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.jsonPayload = jsonPayload;
  }

  public BasePostRequest(String endpoint, String filePath) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.filePath = filePath;
  }

  public BasePostRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams) {
    super(endpoint, pathParams, queryParams);
  }

  public BasePostRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, JsonObject jsonPayload) {
    super(endpoint, pathParams, queryParams);
    this.jsonPayload = jsonPayload;
  }

  public BasePostRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, String filePath) {
    super(endpoint, pathParams, queryParams);
    this.filePath = filePath;
  }

  public Response execute() throws URISyntaxException, IOException {
    HttpPost request = new HttpPost(super.generateUrl());
    super.prepareRequest(request);
    return super.execute(request);
  }

}
