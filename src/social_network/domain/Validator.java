package social_network.domain;

import social_network.repo.Network;

/*
    * Validator class is an abstract class that defines the validate method for validating entities.
    * It takes a generic type parameter T, which represents the type of entity to be validated.
    * The validate method is implemented by subclasses to perform specific validation logic for different entity types.
 */
public abstract class Validator<T> {
    protected Network network;

    public  Validator(Network network) {
        this.network = network;
    }
    public abstract void validate(T entity);
}
