package social_network.exceptions;

public class ApplicationFailureException extends RuntimeException {
    public ApplicationFailureException(String message) {
        super(message);
    }
}
