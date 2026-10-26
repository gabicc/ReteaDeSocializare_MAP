package social_network.repo;

import social_network.domain.Friendship;

public class FriendshipDbRepository implements Repository<Long, Friendship> {
    @Override
    public Friendship findOne(Long id) {
        return null;
    }

    @Override
    public Iterable<Friendship> findAll() {
        return null;
    }

    @Override
    public Friendship save(Friendship entity) {
        return null;
    }

    @Override
    public Friendship delete(Long id) {
        return null;
    }

    @Override
    public Friendship update(Friendship entity) {
        return null;
    }
}
