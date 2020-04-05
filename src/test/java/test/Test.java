package test;

import java.util.Map;

import database.DatabaseConnector;
import entities.UserEntity;
import entities.UserEntityRetriever;
import project.data.objects.input.FileDto;
import project.data.objects.input.LoginDto;
import project.data.objects.output.FileORO;
import project.requests.FileCreateRequest;
import project.requests.FileUploadRequest;
import project.requests.LoginRequest;

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
