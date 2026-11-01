package social_network.repo;

import social_network.domain.Entity;
import social_network.domain.Friendship;
import social_network.domain.Validator;
import social_network.repo.file.FriendshipFileRepository;

import java.util.Map;
import java.util.function.Supplier;

public class FriendshipRepository<ID> extends Entity<ID> {
    Validator<Friendship> validator;
//    Map<RepositoryType, Supplier<Repository<ID, ? extends Entity<ID>>>> repo =
//            Map.of(
//                    RepositoryType.MEMORY, () -> new InMemoryRepository<>(validator),
//                    RepositoryType.FILE, () -> new FriendshipFileRepository(validator, "data/friendships.csv"),
//                    RepositoryType.DATABASE, () -> new FriendshipDbRepository()
//            );

}
