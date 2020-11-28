package http.client;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import http.client.response.Response;

public enum HttpClient {
  INSTANCE;

  private String session;
  private Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());
  private Pattern pattern = Pattern.compile("JSESSIONID=[a-zA-Z0-9+.-]+");

  public Response execute(HttpRequestBase request, List<Header> headers) {
    LOGGER.log(Level.FINE, request.toString());
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      if (session != null && !session.isBlank()) {
        request.setHeader("cookie", "JSESSIONID=" + session);
      }
      for (Header h : headers) {
        request.addHeader(h);
      }

      CloseableHttpResponse response = httpClient.execute(request);

      Header[] setCookieHeaders = response.getHeaders("Set-Cookie");
      for (Header h : setCookieHeaders) {
        if (h.getName().equals("Set-Cookie")) {
          Matcher m = pattern.matcher(h.getValue());
          if (m.find()) {
            session = h.getValue().substring(0, m.end(0)).replace("JSESSIONID=", "");
          }
        }
      }
      if (response.getStatusLine().getStatusCode() == 204) {
        return new Response(204, null);
      }
      HttpEntity entity = response.getEntity();
      String content = EntityUtils.toString(entity);

      JsonElement element = JsonParser.parseString(content);


      LOGGER.log(Level.FINE, response.getStatusLine().toString());
      return new Response(response.getStatusLine().getStatusCode(), element);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
