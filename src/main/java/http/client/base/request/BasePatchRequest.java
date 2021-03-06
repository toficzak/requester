package http.client.base.request;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import org.apache.http.client.methods.HttpPatch;
import com.google.gson.JsonObject;
import http.client.response.Response;

public abstract class BasePatchRequest extends BaseRequest {

  public BasePatchRequest(String endpoint, JsonObject jsonPayload) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.jsonPayload = jsonPayload;
  }

  public BasePatchRequest(String endpoint, String filePath) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    super.filePath = filePath;
  }

  public BasePatchRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, JsonObject jsonPayload) {
    super(endpoint, pathParams, queryParams);
    this.jsonPayload = jsonPayload;
  }

  public BasePatchRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams, String filePath) {
    super(endpoint, pathParams, queryParams);
    this.filePath = filePath;
  }

  public Response execute() throws URISyntaxException, IOException {
    HttpPatch request = new HttpPatch(super.generateUrl());
    super.prepareRequest(request);
    return super.execute(request);
  }

}
