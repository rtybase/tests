package com.test.randprime.primechecker.io;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * This class is an attempt to mimic TCP style client socket, but for UDP.
 */
public class UdpClient {
	private final DatagramChannel serverChannel;
	private final ByteBuffer dataFromClient;
	private final SocketAddress clientAddress;

	UdpClient(DatagramChannel serverChannel, SocketAddress clientAddress, ByteBuffer dataFromClient) {
		this.serverChannel = serverChannel;
		this.clientAddress = clientAddress;
		this.dataFromClient = dataFromClient;
	}

	/**
	 * This isn't really a proper read. A received UDP databgram defines the
	 * client, so this method always returns the same byte buffer received from
	 * the client.
	 */
	public ByteBuffer read() {
		return dataFromClient;
	}

	/**
	 * Writes "data" back to the client. Keep "data" small enough to fit into a
	 * single UDP datagram, e.g. ~512 bytes. Otherwise, fragmentation may kick
	 * in and all the complexities associated with collecting the fragments.
	 */
	public int write(ByteBuffer data) throws IOException {
		return serverChannel.send(data, clientAddress);
	}
}
