package social_network.domain;

import social_network.repo.Network;

import java.util.Set;
import java.util.function.Predicate;

public final class UserCriteria {
    public UserCriteria() {}

    public static Predicate<User> prenumeleIncepeCu(String prefix) {
        return user -> user.getFirstName().startsWith(prefix);
    }

    public static Predicate<User> areNumele(String name) {
        return user -> user.getLastname().equals(name);
    }

//    public static Predicate<User> areCelPutinPrieteni(int n) {
//        Class<Network> network = social_network.repo.Network.class;
//        Set<Long> friends = network.getAllFriendsId();
//    }


}
