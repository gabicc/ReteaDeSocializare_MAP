package social_network.repo;

import social_network.domain.Entity;
import social_network.domain.Message;
import social_network.domain.validators.Validator;

public class MessageRepository<Integer> extends Entity<Integer> {
    Validator<Message> validator;
//    Repository<Integer, Message> repoM = new MessageFileRepository(validator, "cucu");
//    Map<RepositoryType, Supplier<Repository<Integer, Message>>> repo =
//            Map.of(
//                    RepositoryType.MEMORY, () -> new InMemoryRepository<>(validator),
//                    RepositoryType.FILE, () -> new MessageFileRepository(validator, "data/messages.csv"),
//                    RepositoryType.DATABASE, () -> new MessageDbRepository()
//            );
}
