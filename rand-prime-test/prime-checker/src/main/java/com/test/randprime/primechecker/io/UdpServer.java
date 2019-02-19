package com.test.randprime.primechecker.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * This class is an attempt to mimic TCP style server socket, but for UDP.
 */
public class UdpServer {
	private final DatagramChannel serverChannel;
	private final Selector serverChannelSelector;
	private final int requestSize;

	public UdpServer(int port, int requestSize) throws IOException {
		if (port <= 0) {
			throw new IllegalArgumentException("port must be positive!");
		}

		if (requestSize <= 0) {
			throw new IllegalArgumentException("requestSize must be positive!");
		}

		this.serverChannel = createAndBindChannel(port);
		this.serverChannelSelector = bindToSelector();
		this.requestSize = requestSize;
	}

	/**
	 * This method waits for the given "timeout" (in milliseconds) for an
	 * incoming client request. Returns null if none received.
	 */
	public UdpClient waitForDataFromClient(int timeout) throws IOException {
		if (serverChannelSelector.select(timeout) > 0) {
			return processKeys(serverChannelSelector.selectedKeys().iterator());
		} else {
			return null;
		}
	}

	private UdpClient processKeys(Iterator<SelectionKey> selectedKeys) {
		UdpClient client = null;
		while (selectedKeys.hasNext()) {
			try {
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();

				if (key.isValid() && key.isReadable()) {
					client = read(key);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return client;
	}

	private UdpClient read(SelectionKey key) throws IOException {
		DatagramChannel channel = (DatagramChannel) key.channel();
		ByteBuffer request = ByteBuffer.allocate(requestSize);
		SocketAddress clientAddress = channel.receive(request);
		return new UdpClient(channel, clientAddress, request);
	}

	private Selector bindToSelector() throws IOException {
		Selector selector = Selector.open();
		serverChannel.register(selector, SelectionKey.OP_READ);
		return selector;
	}

	private static DatagramChannel createAndBindChannel(int port) throws IOException {
		DatagramChannel channel = DatagramChannel.open();
		InetSocketAddress isa = new InetSocketAddress(port);
		channel.socket().bind(isa);
		channel.configureBlocking(false);
		return channel;
	}
}
