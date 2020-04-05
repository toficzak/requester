package requester.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import requester.entities.Entity;

public class DatabaseConnector<T extends Entity>
{
   private final static String CONNECTION_STRING = DatabaseConnector.generateConnectionString();
   private final static String USER_NAME = PropertyReader.getProperty(PropertyTypes.DB_USER_NAME);
   private final static String USER_PASSWORD = PropertyReader.getProperty(PropertyTypes.DB_USER_PASSWORD);

   public T select(String query, Function<ResultSet, T> mapper)
   {
      return this.runQuery(query, mapper);
   }

   private static String generateConnectionString()
   {
      return new StringBuilder("jdbc:postgresql://")
         .append(PropertyReader.getProperty(PropertyTypes.DB_HOST))
         .append(":")
         .append(PropertyReader.getProperty(PropertyTypes.DB_PORT))
         .append("/")
         .append(PropertyReader.getProperty(PropertyTypes.DB_DATABASE_NAME))
         .toString();
   }

   private static Connection generateConnection()
   {
      try
      {
         return DriverManager.getConnection(DatabaseConnector.CONNECTION_STRING, DatabaseConnector.USER_NAME,
            DatabaseConnector.USER_PASSWORD);
      }
      catch (SQLException e)
      {
         System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         throw new RuntimeException();
      }
   }

   private T runQuery(String query, Function<ResultSet, T> mapper)
   {
      try (
         Connection connection = DatabaseConnector.generateConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(query))
      {
         return mapper.apply(preparedStatement.executeQuery());
      }
      catch (SQLException e)
      {
         System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
         throw new RuntimeException();
      }
      catch (Exception e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }
   }
}