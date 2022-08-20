package duc.nhh.storemanager.exception;

public class InvalidInfoException extends RuntimeException {
    public InvalidInfoException(String message) {
        super("Invalid " + message);
    }
}
