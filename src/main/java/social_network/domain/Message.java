package social_network.domain;
/*
* Message sent from a user to another
* */
public class Message extends Entity<Integer> {
    private long fromId;
    private long toId;
    private String message;

    public Message(int id, long fromId, long toId, String message) {
        super(id);
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
    }

    public Long getFromId() {
        return fromId;
    }

    public Long getToId() {
        return toId;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return "fromId: " + fromId + ", toId: " + toId + ", message: " + message;
    }
}
