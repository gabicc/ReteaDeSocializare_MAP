package social_network.exceptions;

public class FriendshipDoesntExist extends RuntimeException {
    public FriendshipDoesntExist(String message) {
        super(message);
    }
}
