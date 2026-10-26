package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.domain.Validator;
import social_network.repo.file.FriendshipFileRepository;
import social_network.repo.file.MessageFileRepository;
import social_network.repo.file.UserFileRepository;

public class RepositoryFactory {
    public Repository<Long, User> createUserRepository(Validator<User> validator) {
        return new UserFileRepository(validator, "data/users.csv");
    }

    public Repository<Long, Friendship> createFriendshipRepository(Validator<Friendship> validator) {
        return new FriendshipFileRepository(validator, "data/friendships.csv");
    }

//    public Repository<Long, Message> createMessageRepository(Validator<Message> validator) {
//        return new MessageFileRepository(validator, "data/messages.csv");
//    }
}
