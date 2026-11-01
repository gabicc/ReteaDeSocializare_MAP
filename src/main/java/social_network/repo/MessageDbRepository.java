package social_network.repo;

import social_network.domain.Entity;
import social_network.domain.Friendship;
import social_network.domain.Message;

public class MessageDbRepository implements Repository<Integer, Message> {
    @Override
    public Message findOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public Message save(Message entity) {
        return null;
    }

    @Override
    public Message delete(Integer id) {
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }
}
