package social_network.repo.file;
import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.Validator;

import java.time.LocalDateTime;

/**
 * Text file repository for users; one user per line, with the format id;firstName;lastName
 */
public class MessageFileRepository extends AbstractFileRepository<Integer, Message> {

    /**
     *
     * @param validator - the validation strategy for users
     * @param fileName  - the path of the text file
     */
    public MessageFileRepository(Validator<Message> validator, String fileName) {
        super(validator, fileName);
    }

    /**
     *
     * @param line - a line with the format id;fromId;toId;message
     * @return the message described by the line
     */
    @Override
    protected Message extractEntity(String line) {
        String[] fields = line.split(";", -1);
        Message f = new Message(Integer.parseInt(fields[0]), Long.parseLong(fields[1]), Long.parseLong(fields[2]), fields[3]);
        return f;
    }

    /**
     *
     * @param f - the message to be converted
     * @return the line id;fromId;toId;message
     */
    @Override
    protected String createEntityAsString(Message f) {
        return f.getId() + ";" + f.getFromId() + ";" + f.getToId() + ";" + f.getMessage();
    }
}
