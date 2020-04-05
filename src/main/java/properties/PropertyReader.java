package properties;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader
{
   private final static String PROPERTY_FILENAME = "config.properties";
   private final static Properties PROPERTIES = PropertyReader.getPropValues();

   public static String getProperty(PropertyTypes type)
   {
      return (String) PROPERTIES.get(type.name());
   }

   private static Properties getPropValues()
   {
      Properties properties = new Properties();

      try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream(PROPERTY_FILENAME))
      {
         if (inputStream != null)
         {
            properties.load(inputStream);
         }
         else
         {
            throw new FileNotFoundException("property file '" + PROPERTY_FILENAME + "' not found in the classpath");
         }
         return properties;
      }
      catch (Exception e)
      {
         System.out.println("Exception: " + e);
         throw new RuntimeException();
      }
   }
}