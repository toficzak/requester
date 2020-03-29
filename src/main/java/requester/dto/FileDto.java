package requester.dto;

import requester.base.BaseDto;

public class FileDto implements BaseDto
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