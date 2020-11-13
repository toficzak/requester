package http.client.base.request;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.message.BasicHeader;
import http.client.response.Response;

public abstract class BaseDeleteRequest extends BaseRequest {

  protected String endpoint;

  public BaseDeleteRequest(String endpoint) {
    super(endpoint, Collections.emptyMap(), Collections.emptyMap());
  }

  public BaseDeleteRequest(String endpoint, Map<String, String> pathParams,
      Map<String, String> queryParams) {
    super(endpoint, pathParams, queryParams);
  }

  public Response execute() throws URISyntaxException {
    HttpDelete request = new HttpDelete(super.generateUrl());
    BasicHeader h = new BasicHeader("Content-Type", "application/json");
    List<Header> headers = new ArrayList<>();
    headers.add(h);
    return super.httpClient.execute(request, headers);
  }

}
