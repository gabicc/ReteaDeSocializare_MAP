package social_network.observable;

import com.sun.source.doctree.EscapeTree;
import social_network.events.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
    This class represents the history of social events that occur in the social network.
    It extends the SocialObserver class and contains a list of social events that have occurred.
 */
public class EventHistory extends SocialObserver {
    List<SocialEvent<?>> events = new ArrayList<>();

    @Override
    public void notify(SocialEvent<?> event){
        if(event instanceof FriendshipAddEvent || event instanceof FriendshipRemoveEvent ||
        event instanceof MessageAddEvent || event instanceof MessageRemoveEvent){
            events.add(event);
        }
    }

    public List<String> getEventHistory(){
        List<String> history = new ArrayList<>();
        for(SocialEvent<?> event : events){
            history.add(event.toString());
        }
        Stream<SocialEvent<?>> eventStream = events.stream().filter(e -> history.add(e.toString()));
        return history;
    }

    public List<String> getEventHistoryBetween(
            LocalDateTime start,
            LocalDateTime end) {
        // VERIFIC CUM SCAD TIMESTAMP CU 3 ORE!!! ASTA E DE REZOLVAT

        ZoneId zone = ZoneId.systemDefault();

       // String startInstant = start;
        //Instant endInstant = end.atZone(zone).toInstant();

        List<String> history = new ArrayList<>();

//        for (SocialEvent<?> event : events) {
//            Instant timestamp = event.getTimestamp();
//
//            if (timestamp.isAfter(start.toInstant(zone.getRules().getOffset(start)))
//                    && timestamp.isBefore(end.toInstant(zone.getRules().getOffset(end)))) {
//                history.add(event.toString());
//            }
//        }
        //STREAM
        Instant startInstant = start.toInstant(zone.getRules().getOffset(start));
        Instant endInstant = end.toInstant(zone.getRules().getOffset(end));

        Stream<SocialEvent<?>>filter;
        filter = events.stream().filter(event -> {
           Instant timestamp = event.getTimestamp();

           return timestamp.isAfter(startInstant) && timestamp.isBefore(endInstant);
        });
        filter.forEach(event -> history.add(event.toString()));

        return history;
    }
}
