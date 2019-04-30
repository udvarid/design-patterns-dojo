package dojo.patterns.pubsub.simple;

import java.lang.ref.WeakReference;
import java.util.*;

import dojo.patterns.pubsub.Message;
import dojo.patterns.pubsub.Subscriber;

/**
 * Ez az osztály {@link WeakReference} segítségével tárolja a feliratkozókat.
 * Így a java szemétgyűjtő automatikusan kidobálja azokat, ha már csak gyenge
 * hivatkozások vannak rájuk a rendszerben. Az osztály mindent tud, amit a
 * {@link SimplePublisherImpl}.
 */
public class WeakPublisherImpl implements SimplePublisher {

    Set<WeakReference<Subscriber>> subscribers = new HashSet<>();

    @Override
    public void subscribe(Subscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        Optional<WeakReference<Subscriber>> optinalSubscriber = subscribers.stream().filter(s -> Objects.equals(subscriber, s.get())).findAny();
        if (!optinalSubscriber.isPresent()) {
            subscribers.add(new WeakReference<>(subscriber));
        }
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        Iterator<WeakReference<Subscriber>> iterator = subscribers.iterator();
        while (iterator.hasNext()) {
            WeakReference<Subscriber> subcriberExamined = iterator.next();
            if (subcriberExamined.get() == subscriber) {
                iterator.remove();
            }
        }

    }

    @Override
    public void publish(Message message) {
        if (message == null) {
            throw new NullPointerException();
        }
        for (WeakReference<Subscriber> weakReference : subscribers) {
            if (weakReference.get() != null) {
                weakReference.get().receiveMessage(message);
            }
        }
    }

}
