package social_network.events;

import java.time.Instant;
import java.time.LocalDateTime;
/*


    This class represents a social event that occurs in the social network. It is an abstract class that can be extended to create specific types of social events.

    @param <T> The type of data associated with the social event.
 */
public abstract class SocialEvent<T> {
    private LocalDateTime timestamp;
    private final T data;

    public SocialEvent(T data) {
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public SocialEvent(LocalDateTime timestamp, T data) {
        this.timestamp = timestamp;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String toString() {
        return "SocialEvent{" +
                "timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }

    public Instant getTimestamp() {
        return null;
    }
}
