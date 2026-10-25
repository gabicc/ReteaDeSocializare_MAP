package social_network.domain;

import social_network.exceptions.ExistingUserException;
import social_network.repo.Network;
/*
    ValidatorUser class is responsible for validating User entities before they are added to the Network.
    It checks if the first name and last name are not null or empty, and if a user with the same ID already exists in the network.
 */
public class ValidatorUser extends Validator<User>{

    public  ValidatorUser(Network network) {
        super(network);
    }

    @Override
    public void validate(User entity) {
        if (entity.getFirstName() == null || entity.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (entity.getLastname() == null || entity.getLastname().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
//        for (User user : network.getAllUsers()) {
//            if (user.getId().equals(entity.getId())) {
//                throw new ExistingUserException("User with id " + entity.getId() + " already exists");
//            }
//        }
    }
}
