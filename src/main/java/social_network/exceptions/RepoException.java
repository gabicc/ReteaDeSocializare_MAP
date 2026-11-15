package social_network.exceptions;

public class RepoException extends RuntimeException {
    public RepoException(String messsage) {
        super(messsage);
    }

    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }
}
