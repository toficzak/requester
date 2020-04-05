package http.client;

public interface Request<I, O>
{
   O perform();

   I getInput();
}
