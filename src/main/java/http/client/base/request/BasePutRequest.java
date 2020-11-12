package http.client.base.request;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import com.google.gson.JsonObject;
import http.client.response.Response;

public abstract class BasePutRequest extends BaseRequest {

  protected String endpoint;
  protected JsonObject jsonPayload;
  protected String filePath;

  public BasePutRequest(String endpoint, JsonObject jsonPayload) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    this.jsonPayload = jsonPayload;
  }

  public BasePutRequest(String endpoint, String filePath) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
    this.filePath = filePath;
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
    this.prepareRequest(request);
    return super.execute(request);
  }

  private void prepareRequest(HttpPut request) throws IOException {
    if (jsonPayload != null) {
      StringEntity payload = new StringEntity(jsonPayload.toString());
      request.setEntity(payload);
    }
    if (filePath != null && !filePath.isBlank()) {
      HttpEntity p = new ByteArrayEntity(Files.readAllBytes(new File(filePath).toPath()));
      request.setEntity(p);
    }
  }

}
