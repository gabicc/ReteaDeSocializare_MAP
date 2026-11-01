package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.domain.Validator;
import social_network.repo.file.FriendshipFileRepository;
import social_network.repo.file.MessageFileRepository;
import social_network.repo.file.UserFileRepository;

import java.util.Properties;

public class RepositoryFactory {

    String repoType;
    Properties props;
    public RepositoryFactory(Properties props){
        this.props = props;
        repoType = props.getProperty("repository.type");
    }



    public Repository<Long, User> createUserRepository(Validator<User> validator) {

        // asta daca repo type e NULL, nu ar fi mers cum era initial, adica repoType.equals("MEMORY")
        if("MEMORY".equals(repoType)) {
            return new InMemoryRepository<>(validator);
        }
        //TODO fix this
        else if(repoType.equals("FILE")) {
            return new UserFileRepository(validator, "data/users.csv");
        }
        //TODO fix this
        else if(repoType.equals("DATABASE")) {
            throw new UnsupportedOperationException("Repo type DATABASE not implemented");
            //return new UserDbRepository();
        }
        else{
            throw new UnsupportedOperationException("This repo type doesn't exist: " + repoType);
        }
        //return new UserFileRepository(validator, "data/users.csv");
    }

    //TODO as previeusly in the user part
    public Repository<Long, Friendship> createFriendshipRepository(Validator<Friendship> validator) {
        if("MEMORY".equals(repoType)){

        }
        return new FriendshipFileRepository(validator, "data/friendships.csv");
    }

    //TODO same as above
    public Repository<Integer, Message> createMessageRepository(Validator<Message> validator) {
        return new MessageFileRepository(validator, "data/messages.csv");
    }
}
