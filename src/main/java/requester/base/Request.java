package requester.base;

public interface Request<I, O>
{
   O perform();

   I getInput();
}
