package social_network.repo.file;
import social_network.domain.Validator;
import social_network.domain.User;

/**
 * Text file repository for users; one user per line, with the format id;firstName;lastName
 */
public class UserFileRepository extends AbstractFileRepository<Long, User> {

    /**
     *
     * @param validator - the validation strategy for users
     * @param fileName  - the path of the text file
     */
    public UserFileRepository(Validator<User> validator, String fileName) {
        super(validator, fileName);
    }

    /**
     *
     * @param line - a line with the format id;firstName;lastName
     * @return the user described by the line
     */
    @Override
    protected User extractEntity(String line) {
        String[] fields = line.split(";", -1);
        User u = new User(Long.parseLong(fields[0]), fields[1], fields[2]);
        return u;
    }

    /**
     *
     * @param u - the user to be converted
     * @return the line id;firstName;lastName
     */
    @Override
    protected String createEntityAsString(User u) {
        return u.getId() + ";" + u.getFirstName() + ";" + u.getLastname();
    }
}
