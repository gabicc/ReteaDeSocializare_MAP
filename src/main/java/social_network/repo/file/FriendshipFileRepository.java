package social_network.repo.file;
import social_network.domain.Friendship;
import social_network.domain.Validator;
import social_network.domain.User;

import java.time.LocalDateTime;

/**
 * Text file repository for users; one user per line, with the format id;firstName;lastName
 */
public class FriendshipFileRepository extends AbstractFileRepository<Long, Friendship> {

    /**
     *
     * @param validator - the validation strategy for users
     * @param fileName  - the path of the text file
     */
    public FriendshipFileRepository(Validator<Friendship> validator, String fileName) {
        super(validator, fileName);
    }

    /**
     *
     * @param line - a line with the format id1;id2;date
     * @return the friendship described by the line
     */
    @Override
    protected Friendship extractEntity(String line) {
        String[] fields = line.split(";", -1);
        Friendship f = new Friendship(Long.parseLong(fields[0]), Long.parseLong(fields[1]), Long.parseLong(fields[2]), LocalDateTime.parse(fields[3]));
        return f;
    }

    /**
     *
     * @param f - the friendship to be converted
     * @return the line id;firstName;lastName
     */
    @Override
    protected String createEntityAsString(Friendship f) {
        return f.getId() + ";" + f.getId1() + ";" + f.getId2() + ";" + f.getDate();
    }
}
