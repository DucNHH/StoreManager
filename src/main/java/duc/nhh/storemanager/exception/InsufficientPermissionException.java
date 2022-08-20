package duc.nhh.storemanager.exception;

public class InsufficientPermissionException extends RuntimeException {
    public InsufficientPermissionException() {
        super("Insufficient permission to handle this action");
    }
}
