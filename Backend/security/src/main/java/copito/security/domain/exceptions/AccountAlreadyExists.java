package copito.security.domain.exceptions;

public class AccountAlreadyExists extends RuntimeException{
    public AccountAlreadyExists(String message){
        super(message);
    }
}
