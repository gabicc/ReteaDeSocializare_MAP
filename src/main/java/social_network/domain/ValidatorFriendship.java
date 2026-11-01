package social_network.domain;

import social_network.repo.Network;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * ValidatorFriendship class is responsible for validating Friendship entities.
 * It checks if the friendship IDs are not null, if they are not the same, and if the friendship already exists in the network.
 */
public class ValidatorFriendship implements Validator<Friendship> {
    public ValidatorFriendship() {
    }

    private final Validator<Friendship> validator = fr -> {
        if(fr.getId1() == null || fr.getId2() == null) {
            throw new IllegalArgumentException("Friendship ID cannot be null");
        }
        if(fr.getId1().equals(fr.getId2())) {
            throw new IllegalArgumentException("Friendship cannot be between the same user");
        }
        if(fr.getDate() == null || fr.getDate().isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("Friendship date cannot be null or in the future");
        }
    };

    @Override
    public void validate(Friendship entity) {
        validator.validate(entity);
    }
//    @Override
//    public void validate(Friendship entity) {
//        if (entity.getId1() == null || entity.getId2() == null) {
//            throw new IllegalArgumentException("Friendship ID cannot be null");
//        }
//        if (entity.getId1().equals(entity.getId2())) {
//            throw new IllegalArgumentException("Friendship cannot be between the same user");
//        }
//        if(entity.getDate() == null || entity.getDate().isAfter(java.time.LocalDateTime.now())) {
//            throw new IllegalArgumentException("Friendship date cannot be null or in the future");
//        }
//    }
}
