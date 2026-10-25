package social_network.observable;

import social_network.events.SocialEvent;

import java.util.ArrayList;
import java.util.List;
/*
    This class represents an observable object in the social network. It maintains a list of observers that are notified when an event occurs.
    The addObserver() method adds an observer to the list, the removeObserver() method removes an observer from the list, and the notify() method notifies all observers of an event.
 */
public class SocialObservable {
    private final List<SocialObserver> observers = new ArrayList<>();

    public void addObserver(SocialObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(SocialObserver observer) {
        observers.remove(observer);
    }

    public void notify(SocialEvent<?> event) {
        for (SocialObserver observer : observers) {
            observer.notify(event);
        }
    }

}
