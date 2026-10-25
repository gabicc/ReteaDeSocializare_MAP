package social_network.domain;

import social_network.repo.Network;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * ValidatorFriendship class is responsible for validating Friendship entities.
 * It checks if the friendship IDs are not null, if they are not the same, and if the friendship already exists in the network.
 */
public class ValidatorFriendship extends Validator<Friendship> {
    public ValidatorFriendship(Network network) {
        super(network);
    }

    @Override
    public void validate(Friendship entity) {
        if (entity.getId1() == null || entity.getId2() == null) {
            throw new IllegalArgumentException("Friendship ID cannot be null");
        }
        if (entity.getId1().equals(entity.getId2())) {
            throw new IllegalArgumentException("Friendship cannot be between the same user");
        }
        if(entity.getDate() == null || entity.getDate().isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Friendship date cannot be null or in the future");
        }
        for(Friendship f: network.getAllFriendshipList()) {
            if((f.getId1() == entity.getId1() && f.getId2() == entity.getId2()) ||
                    (f.getId1() == entity.getId2() && f.getId2() == entity.getId1())) {
                throw new IllegalArgumentException("Friendship between users " + entity.getId1() + " and " + entity.getId2() + " already exists");
            }
        }
//        for (long friendshipId : network.getAllFriendships().getOrDefault(entity.getId1(), new HashSet<>())) {
//            if ((friendshipId == entity.getId1() && friendshipId == entity.getId2()) ||
//                    (friendshipId == entity.getId2() && friendshipId == entity.getId1())) {
//                throw new IllegalArgumentException("Friendship between users " + entity.getId1() + " and " + entity.getId2() + " already exists");
//            }
//        }
    }
}
