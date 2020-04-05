package requester;

import java.util.Map;

import requester.base.DatabaseConnector;
import requester.base.FileCreateRequest;
import requester.base.FileUploadRequest;
import requester.base.LoginRequest;
import requester.dto.FileDto;
import requester.dto.FileORO;
import requester.dto.LoginDto;
import requester.entities.UserEntity;
import requester.entities.UserEntityRetriever;

public class Test
{

   @org.junit.Test
   public void test() throws Exception
   {
      new LoginRequest(new LoginDto("webvrtest@webvr.fream.pl", "webvr")).perform();

      FileDto fileDto = new FileDto("krowki.mp4", "Mp4", 9590210, "Video", null);
      FileORO fileORO = new FileCreateRequest(fileDto).perform();

      String hash = fileORO.hash;

      new FileUploadRequest(Map.of("id", hash), "/home/krzysztof/Videos/krowki.mp4").perform();

      UserEntity resposne = new DatabaseConnector<UserEntity>().select("select * from users where id=1", UserEntityRetriever.single);
      System.out.println(resposne);
   }

}
