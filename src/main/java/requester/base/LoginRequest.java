package requester.base;

import java.util.Optional;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import requester.dto.LoginDto;
import requester.dto.LoginORO;

public class LoginRequest extends BasePostRequest implements Request<LoginDto, LoginORO>
{
   private LoginDto input;

   public LoginRequest(LoginDto input)
   {
      super(BodyType.JSON, Optional.empty(), Optional.empty());
      LoginRequest.ENDPOINT = "/login";
      this.input = input;
   }

   @Override
   public LoginORO perform()
   {
      HttpPost request = this.setupRequest(input, false);
      try (CloseableHttpResponse response = HttpClient.httpClient.execute(request))
      {
         String jsessionid = response.getHeaders("Set-Cookie")[0].getValue();
         HttpClient.jsessionId = jsessionid.split(";")[0].split("=")[1];
         String stringResponse = EntityUtils.toString(response.getEntity());
         return HttpClient.gson.fromJson(stringResponse, LoginORO.class);
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }
   }

   @Override
   public LoginDto getInput()
   {
      return this.input;
   }

}
