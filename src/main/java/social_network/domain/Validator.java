package social_network.domain;

import social_network.repo.Network;

/*
    * Validator class is an abstract class that defines the validate method for validating entities.
    * It takes a generic type parameter T, which represents the type of entity to be validated.
    * The validate method is implemented by subclasses to perform specific validation logic for different entity types.
 */
@FunctionalInterface
public interface Validator<T> {
    void validate(T entity);

    default Validator<T> andThen(Validator<T> other) {
        if(other == null) {
            throw new IllegalArgumentException("Celalalt validator nu e bun");
        }
        return entity -> {
            this.validate(entity);
            other.validate(entity);
        };
    }
//    Network network = null;

//    public  Validator(Network network) {
//        Validator.network = network;
//    }

}
