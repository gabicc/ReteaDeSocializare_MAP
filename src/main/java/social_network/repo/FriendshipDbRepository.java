package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Tuple;
import social_network.domain.User;
import social_network.domain.validators.Validator;
import social_network.exceptions.ApplicationFailureException;
import social_network.exceptions.RepoException;
import social_network.repo.util.Page;
import social_network.repo.util.Pageable;
import social_network.repo.util.Sort;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class FriendshipDbRepository implements Repository<Tuple<Long, Long>, Friendship> {//PagingRepository<Tuple<Long, Long>, Friendship> {
    private Validator<Friendship> validator;
    private final Connection connection;
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

    private int count(Connection connection) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM friendships";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet result = statement.executeQuery()) {
            int totalNumberOfFriends = 0;
            if (result.next()) {
                totalNumberOfFriends = result.getInt("count");
            }
            return totalNumberOfFriends;
        }
    }

    public User fromRowUser(ResultSet row) throws SQLException {
        User user = new User(row.getString("first_name"), row.getString("last_name"));
        user.setId(row.getLong("id"));
        return user;
    }

    public Page<User> findFriendsOnPage(Long userId, Pageable pageable) {
        try{
            int totalNumberOfFriends = count(connection);
            List<User> friendsOnPage;
            if(totalNumberOfFriends > 0) {
                friendsOnPage = findAllOnPageFriends(userId, connection, pageable);
            }
            else{
                friendsOnPage = new ArrayList<>();
            }
            return new Page<>(friendsOnPage, totalNumberOfFriends);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAllOnPageFriends(Long userId, Connection connection, Pageable pageable) throws SQLException{
        List<User> friendsOnPage = new ArrayList<>();
        String sql = "SELECT u.id, u.first_name, u.last_name FROM friendships f" +
                " JOIN users u ON u.id = CASE" +
                " WHEN f.id1 = ? THEN f.id2 ELSE f.id1" +
                " END " +
                " WHERE f.id1 = ? OR f.id2 = ?" +
                " LIMIT ? OFFSET ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, userId);
        statement.setLong(2, userId);
        statement.setLong(3, userId);
        statement.setInt(4, pageable.getPageSize());
        statement.setInt(5, pageable.getPageNumber() * pageable.getPageSize());;

        ResultSet resSet = statement.executeQuery();

        while(resSet.next()) {
            friendsOnPage.add(fromRowUser(resSet));
        }
        return friendsOnPage;
    }

    //    public List<User> findAllOnPageFr(Connection connection, Pageable pageable) throws SQLException {
//        List<User> friendsOnPage = new ArrayList<>();
//        //TODO schimba SQL
//        String sql = "SELECT DISTINCT u.id, u.first_name, u.last_name" +
//                " FROM friendships f" +
//                " INNER JOIN users u ON u.id = f.id1" +
//                " ORDER BY u.id" +
//                " LIMIT ? OFFSET ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, pageable.getPageSize());
//        statement.setInt(2, pageable.getPageNumber() * pageable.getPageSize());
//        ResultSet resSet = statement.executeQuery();
//
//        while(resSet.next()) {
//            friendsOnPage.add(fromRowUser(resSet));
//        }
//        return friendsOnPage;
//    }

    //    @Override
//    public Page<User> findAllOnPage(Pageable pageable) {
//        try(connection) {
//            int totalNumberOfFriends = count(connection);
//            List<User> friendsOnPage = new ArrayList<>();
//            if(totalNumberOfFriends > 0) {
//                friendsOnPage = findAllOnPageFr(connection, pageable);
//            }
//            else{
//                friendsOnPage = new ArrayList<>();
//            }
//            return new Page<>(friendsOnPage, totalNumberOfFriends);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private static final Map<String, String> SORT_COLUMNS = Map.of(
            "first_name", "prenume",
            "last_name", "nume"
    );

//    @Override
//    public Page<Friendship> findAllOnPage(Pageable pageable, Sort sort) {
//        String field = sort.field() == null ? "" : sort.field().trim().toLowerCase();
//        String column = SORT_COLUMNS.get(field);
//        if(column == null) {
//            throw new IllegalArgumentException("Cannot sort by: " + sort.field() + ". Allowed: " + SORT_COLUMNS.keySet());
//        }
//
//        String sql = "";
//        try(connection) {
//            int totalNumberOfFriends = count(connection);
//            List<Friendship> friendsOnPage = new ArrayList<>();
//            try(PreparedStatement statement = connection.prepareStatement(sql)){
//                statement.setInt(1, pageable.getPageSize());
//                statement.setInt(2, pageable.getPageNumber() * pageable.getPageSize());
//                try(ResultSet resSet = statement.executeQuery()) {
//                    while (resSet.next()) {
//                        Friendship fr = new Friendship(resSet.getLong("id1"), resSet.getLong("id2"), resSet.getString("friends_from"));
//                        fr.setId(resSet.getLong("id"));
//                        friendsOnPage.add(fr);
//                    }
//                }
//            }
//            return new Page<>(friendsOnPage, totalNumberOfFriends);
//        } catch (SQLException e) {
//            throw new RepoException("Sorted pagination failed", e);
//        }
//    }
}
