
package dojo.patterns.pubsub.simple;

import java.util.HashSet;
import java.util.Set;

import dojo.patterns.pubsub.Message;
import dojo.patterns.pubsub.Subscriber;

/**
 * Egy egyszerű pub-sub implementáció, ami kollekcióban tárolja a feliratkozókat.
 * Tipp: használjunk {@link HashSet}-et, mivel azzal alapból kiszűrhetjük az ismételt
 * tárolást!
 */
public class SimplePublisherImpl implements SimplePublisher {

    private Set<Subscriber> subscribers = new HashSet<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        subscribers.remove(subscriber);
    }

    @Override
    public void publish(Message message) {
        if (message == null) {
            throw new NullPointerException();
        }
        subscribers.forEach(s -> s.receiveMessage(message));
    }

}
