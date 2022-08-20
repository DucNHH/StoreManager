package duc.nhh.storemanager.exception;

public class WrongInfoException extends RuntimeException{
    public WrongInfoException(String message) {
        super("Wrong " + message);
    }
}
