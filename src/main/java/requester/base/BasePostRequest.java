package requester.base;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Optional;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;

public abstract class BasePostRequest extends BaseRequest
{
   private BodyType bodyType;
   private String filePath;

   public BasePostRequest(BodyType bodyType, Optional<Map<String, String>> optQueryParams, Optional<String> optFilePath)
   {
      super();
      this.bodyType = bodyType;
      optQueryParams.ifPresent(queryParams -> {
         super.queryParams = queryParams;
      });
      optFilePath.ifPresent(filePath -> {
         this.filePath = filePath;
      });
   }

   protected <T extends BaseDto> HttpPost setupRequest(T input, boolean addSessionCookie)
   {
      String endpoint = HttpClient.generateUrl(ENDPOINT, Optional.ofNullable(queryParams));
      HttpPost request = new HttpPost(endpoint);

      switch (this.bodyType)
      {
      case FILE:
         request.setEntity(new FileEntity(new File(filePath)));
         break;
      case JSON:
         try
         {
            request.setEntity(new StringEntity(HttpClient.gson.toJson(input)));
         }
         catch (UnsupportedEncodingException e1)
         {
            e1.printStackTrace();
            throw new RuntimeException();
         }
         break;
      default:
         break;

      }
      request.setHeader("Content-type", "application/json");
      if (addSessionCookie)
      {
         request.setHeader("cookie", "JSESSIONID=" + HttpClient.jsessionId);
      }
      return request;
   }

}
