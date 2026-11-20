package social_network.repo;

import social_network.domain.Entity;
import social_network.domain.User;
import social_network.domain.validators.Validator;

public class UserRepository<ID> extends Entity<ID>{
    Validator<User> validator;
//    Map<RepositoryType, Supplier<Repository<ID, ? extends Entity<ID>>>> repo =
//            Map.of(
//                    RepositoryType.MEMORY, () -> new InMemoryRepository<>(validator),
//                    RepositoryType.FILE, () -> new UserFileRepository(validator, "data/users.csv"),
//                    RepositoryType.DATABASE, () -> new UserDbRepository()
//
//            );

}
