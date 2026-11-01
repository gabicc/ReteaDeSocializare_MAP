package social_network.observable;

import social_network.events.SocialEvent;
/*
    This class represents an observer in the social network.
    It is an abstract class that defines the notify method, which is called when a social event occurs.
 */
public abstract class SocialObserver {
    public abstract void notify(SocialEvent<?> event);
}
