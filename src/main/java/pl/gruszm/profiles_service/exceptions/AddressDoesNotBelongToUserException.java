package pl.gruszm.profiles_service.exceptions;

public class AddressDoesNotBelongToUserException extends Exception
{
    public AddressDoesNotBelongToUserException(String message)
    {
        super(message);
    }
}
