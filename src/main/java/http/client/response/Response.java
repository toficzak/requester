package http.client.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Response {
  public int statusCode;
  public JsonElement json;

  public JsonObject getJsonObject() {
    return json.getAsJsonObject();
  }

  public JsonArray getJsonArray() {
    return json.getAsJsonArray();
  }

  public Response(int statusCode, JsonElement json) {
    super();
    this.statusCode = statusCode;
    this.json = json;
  }
}
