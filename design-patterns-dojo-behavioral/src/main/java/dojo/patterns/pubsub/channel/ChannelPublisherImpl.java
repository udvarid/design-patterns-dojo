package dojo.patterns.pubsub.channel;

import dojo.patterns.pubsub.Message;
import dojo.patterns.pubsub.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelPublisherImpl implements ChannelPublisher {

	private Map<Channel, List<Subscriber>> subscribers = new HashMap<>();

	@Override
	public void subscribe(Channel channel, Subscriber subscriber) {
		if (channel == null || subscriber == null) {
			throw new NullPointerException();
		}
		subscribers.get(channel).add(subscriber);
	}

	@Override
	public void unsubscribe(Channel channel, Subscriber subscriber) {
		if (channel == null || subscriber == null) {
			throw new NullPointerException();
		}
		subscribers.get(channel).remove(subscriber);

	}

	@Override
	public void publish(Channel channel, Message message) {
		if (channel == null || message == null) {
			throw new NullPointerException();
		}
		for (Subscriber subscriber : subscribers.get(channel)) {
			subscriber.receiveMessage(message);
		}
	}

}
