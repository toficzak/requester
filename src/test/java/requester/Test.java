package requester;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Test
{

   @org.junit.Test
   public void test() throws Exception
   {
      HttpPost post = new HttpPost("http://localhost:8080/web/api/v1/login");

      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      StringEntity postingString = new StringEntity(gson.toJson(new LoginDto("webvrtest@webvr.fream.pl", "webvr")));
      post.setEntity(postingString);
      post.setHeader("Content-type", "application/json");

      LoginORO oro = new LoginORO();

      CloseableHttpClient httpClient = HttpClients.createDefault();
      HttpClientContext context = HttpClientContext.create();
      BasicCookieStore cookieStore = new BasicCookieStore();
      context.setCookieStore(cookieStore);
      BasicClientCookie cookie = null;

      String asd = "";

      try (CloseableHttpResponse response = httpClient.execute(post))
      {
         String jsessionid = response.getHeaders("Set-Cookie")[0].getValue();
         String jsessionvalue = jsessionid.split(";")[0].split("=")[1];

         asd = jsessionvalue;

         Calendar calendar = Calendar.getInstance();
         calendar.add(Calendar.DAY_OF_YEAR, 100);
         Date date = calendar.getTime();

         cookie = new BasicClientCookie("JSESSIONID", jsessionvalue);
         cookie.setPath("/");
         cookie.setExpiryDate(date);
         cookie.setAttribute(ClientCookie.DOMAIN_ATTR, "true");
         cookie.setDomain(".app.localhost");
         cookie.setSecure(false);
         cookieStore.addCookie(cookie);

         String stringResponse = EntityUtils.toString(response.getEntity());
         oro = gson.fromJson(stringResponse, LoginORO.class);
      }

      FileDto fileDto = new FileDto("doge.jpeg", "Jpeg", 12448, "Image", null);

      System.out.println(gson.toJson(fileDto));

      HttpPost filePost = new HttpPost("http://localhost:8080/web/api/v1/files");
      postingString = new StringEntity(gson.toJson(fileDto));
      filePost.setEntity(postingString);
      filePost.setHeader("Content-type", "application/json");
      filePost.setHeader("cookie", "JSESSIONID=" + asd);
      FileORO fileOro = null;

      CloseableHttpClient clientWithCookie = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
      try (
         CloseableHttpResponse response = clientWithCookie.execute(filePost))
      {
         String stringResponse = EntityUtils.toString(response.getEntity());
         fileOro = gson.fromJson(stringResponse, FileORO.class);
      }
      System.out.println(gson.toJson(fileOro));

      HttpPost uploadPost = new HttpPost("http://localhost:8080/web/api/v1/upload?id=" + fileOro.hash);
      uploadPost.setHeader("cookie", "JSESSIONID=" + asd);

      File file = new File("/home/krzysztof/Desktop/doge.jpg");

      FileEntity entity = new FileEntity(file);

      uploadPost.setEntity(entity);

      FolderEntryORO feORO = new FolderEntryORO();
      CloseableHttpClient clientWithFile = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

      try (
         CloseableHttpResponse response = clientWithFile.execute(uploadPost))
      {
         String stringResponse = EntityUtils.toString(response.getEntity());
         feORO = gson.fromJson(stringResponse, FolderEntryORO.class);
      }
      System.out.println(gson.toJson(feORO));
   }

   class LoginDto
   {
      String login;
      String password;

      public LoginDto(String login, String password)
      {
         super();
         this.login = login;
         this.password = password;
      }
   }

   class FileORO
   {
      String hash;
   }

   class FolderEntryORO
   {
      String hash;
      String name;
      Long id;
   }

   class LoginORO
   {
      String email;
   }

   class FileDto
   {
      String name;
      String mime;
      Integer size;
      String role;
      Long folderId;

      public FileDto(String name, String mime, Integer size, String role, Long folderId)
      {
         super();
         this.name = name;
         this.mime = mime;
         this.size = size;
         this.role = role;
         this.folderId = folderId;
      }
   }

}
