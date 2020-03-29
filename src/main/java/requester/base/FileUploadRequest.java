package requester.base;

import java.util.Map;
import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import requester.dto.FolderEntryORO;

public class FileUploadRequest extends BasePostRequest implements Request<Void, FolderEntryORO>
{
   public FileUploadRequest(Map<String, String> queryParams, String filePath)
   {
      super(BodyType.FILE, Optional.of(queryParams), Optional.of(filePath));
      ENDPOINT = "/upload";
   }

   @Override
   public FolderEntryORO perform()
   {
      HttpPost request = this.setupRequest(null, true);
      CloseableHttpClient client = HttpClientBuilder.create().build();
      try (CloseableHttpResponse response = client.execute(request))
      {
         return HttpClient.gson.fromJson(EntityUtils.toString(response.getEntity()), FolderEntryORO.class);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }
   }

   @Override
   public Void getInput()
   {
      return null;
   }

}
