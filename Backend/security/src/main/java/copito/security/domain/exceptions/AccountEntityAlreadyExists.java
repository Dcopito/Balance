package copito.security.domain.exceptions;

public class AccountEntityAlreadyExists extends RuntimeException{
    public AccountEntityAlreadyExists(String message){
        super(message);
    }
}
