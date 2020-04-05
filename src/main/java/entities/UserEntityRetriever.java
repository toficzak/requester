package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class UserEntityRetriever
{
   public static Function<ResultSet, UserEntity> single = resultSet -> {
      try
      {
         resultSet.next();
         return UserEntity.getBuilder().withId(resultSet.getLong("id")).build();
      }
      catch (SQLException e)
      {
         e.printStackTrace();
         throw new RuntimeException();
      }
   };

}
