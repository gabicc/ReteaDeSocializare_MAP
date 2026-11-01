package social_network.repo;

import social_network.domain.User;

public class UserDbRepository implements Repository<Long, User> {
    @Override
    public User findOne(Long id) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public User save(User entity) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }
}
