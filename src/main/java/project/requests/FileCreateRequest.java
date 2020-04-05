package project.requests;

import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import http.client.BasePostRequest;
import http.client.BodyType;
import http.client.HttpClient;
import http.client.Request;
import project.data.objects.input.FileDto;
import project.data.objects.output.FileORO;

public class FileCreateRequest extends BasePostRequest implements Request<FileDto, FileORO>
{
   private FileDto input;

   public FileCreateRequest(FileDto input)
   {
      super(BodyType.JSON, Optional.empty(), Optional.empty());
      ENDPOINT = "/files";
      this.input = input;
   }

   @Override
   public FileORO perform()
   {
      HttpPost request = this.setupRequest(input, true);
      CloseableHttpClient client = HttpClientBuilder.create().build();
      try (CloseableHttpResponse response = client.execute(request))
      {
         String stringResponse = EntityUtils.toString(response.getEntity());
         return HttpClient.gson.fromJson(stringResponse, FileORO.class);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }
   }

   @Override
   public FileDto getInput()
   {
      return this.input;
   }

}
