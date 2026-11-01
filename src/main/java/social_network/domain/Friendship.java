package social_network.domain;

import java.time.LocalDateTime;

/*
* This class has 2 parameters and it forms a friendship between users with id1 and id2
* */
public class Friendship extends Entity<Long> {
    private long id1;
    private long id2;
    private LocalDateTime date;

    public Friendship(long id, long id1, long id2, LocalDateTime date) {
        super(id);
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
    }

    public Long getId() {
        return super.getId();
    }

    public Long getId1() {
        return id1;
    }

    public Long getId2() {
        return id2;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", date=" + date +
                '}';
    }
}
