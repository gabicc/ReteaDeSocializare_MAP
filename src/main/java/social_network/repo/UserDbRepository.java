package social_network.repo;

import social_network.domain.User;
import social_network.domain.Validator;
import social_network.exceptions.ApplicationFailureException;
import social_network.exceptions.RepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class UserDbRepository implements Repository<Long, User> {
    private Validator<User> validator;
    private Connection connection;
    private Properties props;


    public UserDbRepository(Validator<User> validator, Properties props) {
        this.validator = validator;
        this.props = props;
        try {
            connection = DriverManager.getConnection(
//                    "jdbc:postgresql://localhost:5432/socialnetwork",
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
//                    "postgres",
//                    "postgres"
            );
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new ApplicationFailureException("Unable to connect to database", e);
        }
    }

    public User fromRow(ResultSet row) throws SQLException {
        User user = new User(row.getString("first_name"), row.getString("last_name"));
        user.setId(row.getLong("id"));
        return user;
    }

    @Override
    public Optional<User> findOne(Long id) {
//        return null;
        if(id == null) {
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "SELECT id, first_name, last_name FROM users WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql); {
                statement.setLong(1, id);
                ResultSet resSet = statement.executeQuery();
                return resSet.next() ? Optional.of(fromRow(resSet)) : Optional.empty();
            }
        } catch(SQLException e) {
            throw new RepoException("Eroare la cautarea userului", e.getCause());
        }
    }



    @Override
    public Iterable<User> findAll() {
        List<User> users =new ArrayList<>();
        String sql = "SELECT id, first_name, last_name FROM users ORDER BY id";
        try{
            PreparedStatement statement = connection.prepareStatement(sql); {
                ResultSet resSet =statement.executeQuery();
                while(resSet.next()) {
                    users.add(fromRow(resSet));
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RepoException("Eroare la citirea userilor (findAll)", e.getCause());
        }
        //return null;
    }

    @Override
    public Optional<User> save(User entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity nu poate fi null");
        }
        validator.validate(entity);
//        if(entity.getId() == null) {
//            throw new IllegalArgumentException("Id nu poate fi null");
//        }
        String sql = "INSERT INTO users(first_name, last_name) VALUES (?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastname());

            statement.executeUpdate();

            ResultSet resSet = statement.getGeneratedKeys();
            if(!resSet.next()) {
                throw new IllegalArgumentException("Nu s-a generat id");
            }
            else{
                entity.setId(resSet.getLong(1));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RepoException("Eroare la save user", e.getCause());
        }
    }

    @Override
    public Optional<User> delete(Long id) {
        if(id == null){
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "DELETE FROM users WHERE id = ? RETURNING id, first_name, last_name";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resSet = statement.executeQuery();
            return resSet.next() ? Optional.of(fromRow(resSet)) : Optional.empty();
        } catch (SQLException e) {
            throw new RepoException("Eroare la delete user", e.getCause());
        }
    }


    /**
     *
     * @param entity entity must not be null
     * @return optional of the user
     */
    @Override
    public Optional<User> update(User entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity nu poate fi null");
        }
        validator.validate(entity);
        if(entity.getId() == null) {
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "UPDATE users SET first_name = ?, last_name = ? WHERE id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastname());
            statement.setLong(3, entity.getId());
            return statement.executeUpdate() == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RepoException("Eroare la update user", e.getCause());
        }
    }
}
