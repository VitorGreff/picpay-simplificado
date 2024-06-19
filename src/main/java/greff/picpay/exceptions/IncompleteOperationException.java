package greff.picpay.exceptions;

public class IncompleteOperationException extends RuntimeException {
    public IncompleteOperationException(String message) {
        super(message);
    }
}
