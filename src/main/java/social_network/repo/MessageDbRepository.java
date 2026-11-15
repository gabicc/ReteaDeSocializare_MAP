package social_network.repo;

import social_network.domain.Entity;
import social_network.domain.Friendship;
import social_network.domain.Message;

import java.util.Optional;

public class MessageDbRepository implements Repository<Integer, Message> {
    @Override
    public Optional<Message> findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public Optional<Message> save(Message entity) {
        return null;
    }

    @Override
    public Optional<Message> delete(Integer id) {
        return null;
    }

    @Override
    public Optional<Message> update(Message entity) {
        return null;
    }
}
