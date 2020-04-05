package requester.entities;

public class UserEntity implements Entity
{
   public Long id;

   public static UserEntityBuilder getBuilder()
   {
      return new UserEntityBuilder();
   }

   public static class UserEntityBuilder
   {
      private UserEntity userEntity = new UserEntity();

      public UserEntityBuilder withId(Long id)
      {
         this.userEntity.id = id;
         return this;
      }

      public UserEntity build()
      {
         return this.userEntity;
      }
   }

   @Override
   public String toString()
   {
      return "UserEntity [id=" + id + "]";
   }

}
