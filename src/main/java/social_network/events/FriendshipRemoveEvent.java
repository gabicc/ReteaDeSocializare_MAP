package social_network.events;

import social_network.domain.Friendship;
import social_network.domain.User;

import java.time.LocalDateTime;
/*
    This class represents a friendship removal event that occurs in the social network. It extends the SocialEvent class and is used to notify observers when a friendship is removed.
*/

public class FriendshipRemoveEvent extends SocialEvent<Friendship> {
    public FriendshipRemoveEvent(Friendship data) {
        super(data);
    }
}