package requester.dto;

import requester.base.BaseDto;

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