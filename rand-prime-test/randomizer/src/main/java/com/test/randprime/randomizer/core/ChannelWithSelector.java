package com.test.randprime.randomizer.core;

import java.io.IOException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Objects;

public abstract class ChannelWithSelector {
	public static final int TIMEOUT = 2000;

	private final Selector selector;

	// package protected on purpose
	ChannelWithSelector(DatagramChannel channel, int ioOperation) throws IOException {
		Objects.requireNonNull(channel, "channel must not be null!");

		this.selector = Selector.open();
		channel.register(selector, ioOperation);
	}

	protected abstract boolean keyIsReady(SelectionKey key);

	protected final SelectionKey findReadyKey() throws IOException {
		SelectionKey keyForEvent = null;
		if (selector.select(TIMEOUT) > 0) {
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
			while (selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();

				if (key.isValid() && keyIsReady(key)) {
					keyForEvent = key;
				}
			}
		}
		return keyForEvent;
	}
}
