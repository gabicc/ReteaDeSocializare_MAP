package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Tuple;
import social_network.domain.Validator;
import social_network.exceptions.ApplicationFailureException;
import social_network.exceptions.RepoException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class FriendshipDbRepository implements Repository<Tuple<Long, Long>, Friendship> {
    private Validator<Friendship> validator;
    private Connection connection;
    private Properties props;

    public FriendshipDbRepository(Validator<Friendship> validator, Properties props) {
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

    public Friendship fromRow(ResultSet row) throws SQLException {
        Friendship fr = new Friendship(row.getLong("id1"), row.getLong("id2")
                , row.getTimestamp("friends_from").toLocalDateTime());
        return fr;
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> id) {
        if(id == null) {
            throw new IllegalArgumentException("ID nu poate fi null");
        }
        String sql = "SELECT id1, id2, friendships.friends_from FROM friendships WHERE id1 = ? AND id2 = ?";
        try{
          PreparedStatement statement = connection.prepareStatement(sql); {
              statement.setLong(1, id.getX());
              statement.setLong(2, id.getY());
              ResultSet resSet = statement.executeQuery();
              return resSet.next() ? Optional.of(fromRow(resSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new RepoException("Eroare la findOne la friendships", e.getCause());
        }
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendship_list = new ArrayList<>();
        String sql = "SELECT id1, id2, friends_from FROM friendships ORDER BY id1, id2";
        try{
            PreparedStatement statement = connection.prepareStatement(sql); {
                ResultSet resSet = statement.executeQuery();
                while(resSet.next()) {
                    friendship_list.add(fromRow(resSet));
                }
                return friendship_list;
            }
        } catch (SQLException e) {
            throw new RepoException("Eroare la findAll la frriendships", e.getCause());
        }
    }

    @Override
    /*
    TODO LocalDateTime -> Timestamp
    * */
    public Optional<Friendship> save(Friendship entity) {
        if(entity == null) {
            throw new  IllegalArgumentException("Entity nu poate fi null");
        }
        validator.validate(entity);
        if(entity.getId() == null) {
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "INSERT INTO friendships(id1, id2, friends_from) VALUES (?, ?, ?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            //statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));
            LocalDateTime date = entity.getDate();
            Timestamp timestamp = Timestamp.valueOf(date);
            statement.setTimestamp(3, timestamp);
            //!!!!!!!!!

            statement.executeUpdate();

            //entity.setId(new Tuple<>(entity.getId1(), entity.getId2()));

            return Optional.empty();

        } catch (SQLException e) {
            throw new RepoException("Eroare la save", e);
        }
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> id) {
        if(id == null) {
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "DELETE FROM friendships WHERE id1 = ? AND id2 = ? RETURNING id1, id2, friends_from";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id.getX());
            statement.setLong(2, id.getY());
            ResultSet resSet = statement.executeQuery();
            return resSet.next() ? Optional.of(fromRow(resSet)) : Optional.empty();

        } catch (SQLException e) {
            throw new RepoException("Eroare la delete friendship", e.getCause());
        }
    }

    @Override
    /*
    TODO LocalDateTime -> Timestamp
    * */
    public Optional<Friendship> update(Friendship entity) {
        if(entity == null) {
            throw new IllegalArgumentException("Entity nu poate fi null");
        }
        validator.validate(entity);
        if(entity.getId() == null) {
            throw new IllegalArgumentException("Id nu poate fi null");
        }
        String sql = "UPDATE friendships SET id1 = ?, id2 = ?, friends_from = ? WHERE id1 = ? AND id2 = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            statement.setTimestamp(3, Timestamp.valueOf(entity.getDate()));

            //!!!!!!!!!!

            return statement.executeUpdate() == 0 ? Optional.of(entity) : Optional.empty();
        } catch (SQLException e) {
            throw new RepoException("Eroare la update friendship", e.getCause());
        }
    }
}
