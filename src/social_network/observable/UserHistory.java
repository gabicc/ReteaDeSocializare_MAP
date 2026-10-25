package social_network.observable;

import social_network.events.SocialEvent;
import social_network.events.UserAddEvent;
import social_network.events.UserRemoveEvent;
import social_network.events.UserUpdateEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
/**
 * This class is responsible for keeping track of user-related events.
 *
 *
 */
public class UserHistory extends SocialObserver{
    List<SocialEvent> events = new ArrayList<>();

    @Override
    public void notify(SocialEvent<?> event){
        if(event instanceof UserAddEvent || event instanceof UserRemoveEvent || event instanceof UserUpdateEvent){
            events.add(event);
        }
    }

    public List<String> getUserHistory(){
        List<String> history = new ArrayList<>();
        for(SocialEvent event : events){
            history.add(event.toString());
        }
        return history;
    }

    public List<String> getUserHistoryBetween(LocalDateTime start, LocalDateTime end){
        List<String> history = new ArrayList<>();
        for(SocialEvent event: events) {
            System.out.println("Event: " + event.toString());
            ZoneId zone = ZoneId.systemDefault();

            Instant startInstant = start.atZone(zone).toInstant();
            Instant endInstant = end.atZone(zone).toInstant();

            if (event.getTimestamp().isAfter(startInstant)
                    && event.getTimestamp().isBefore(endInstant)) {
                history.add(event.toString());
            }
        }
        return history;
    }
}
