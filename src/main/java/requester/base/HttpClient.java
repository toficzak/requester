package requester.base;

import java.net.URISyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HttpClient
{
   public static CloseableHttpClient httpClient = HttpClients.createDefault();
   public static String jsessionId;

   public static String serverHost = "http://localhost:8080/";
   public static String rest = "web/api/v1";

   public static Gson gson = new GsonBuilder().setPrettyPrinting().create();

   public static String generateHost()
   {
      return new StringBuilder(serverHost).append(rest).toString();
   }

   public static String generateUrl(String endpoint, Optional<Map<String, String>> optQueryParams)
   {
      try
      {
         String url = new StringBuilder(generateHost())
            .append(endpoint)
            .toString();

         if (optQueryParams.isEmpty() || optQueryParams.get().isEmpty())
         {
            return url;
         }
         URIBuilder builder = new URIBuilder(url);

         for (Entry<String, String> param : optQueryParams.get().entrySet())
         {
            builder.addParameter(param.getKey(), param.getValue());
         }
         return builder.build().toString();
      }
      catch (URISyntaxException e)
      {
         System.err.println("Nie umiem stworzyć URLa.");
         throw new RuntimeException();
      }
   }

}
