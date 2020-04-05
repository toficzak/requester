package project.data.objects.input;

import dto.BaseDto;

public class LoginDto implements BaseDto
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