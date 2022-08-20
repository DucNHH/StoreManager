package duc.nhh.storemanager.exception;

public class NotEnoughProductException extends RuntimeException {
    public NotEnoughProductException(String message) {
        super("Not enough " + message);
    }
}
