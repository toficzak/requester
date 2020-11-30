package http.client.base.request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import org.apache.http.client.methods.HttpPut;
import com.google.gson.JsonObject;
import http.client.response.Response;

public abstract class BasePutRequest extends BaseRequest {

  public BasePutRequest(String endpoint, JsonObject jsonPayload) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.jsonPayload = jsonPayload;
  }

  public BasePutRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams) {
    super(endpoint, pathParams, queryParams);
  }

  public BasePutRequest(String endpoint, String filePath) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.filePath = filePath;
  }

  public BasePutRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, JsonObject jsonPayload) {
    super(endpoint, pathParams, queryParams);
    this.jsonPayload = jsonPayload;
  }

  public BasePutRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, String filePath) {
    super(endpoint, pathParams, queryParams);
    this.filePath = filePath;
  }

  public Response execute() throws URISyntaxException, IOException {
    HttpPut request = new HttpPut(super.generateUrl());
    super.prepareRequest(request);
    return super.execute(request);
  }

}
