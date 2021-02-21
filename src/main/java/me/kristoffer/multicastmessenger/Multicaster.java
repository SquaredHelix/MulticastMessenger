package me.kristoffer.multicastmessenger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.nio.charset.Charset;

public class Multicaster {

	private InetSocketAddress socketAddress;
	private InetAddress group;
	private int port;
	private MulticastSocket socket;
	private int bufferSize = 1024;
	private Charset charset = Charset.forName("UTF-8");
	
	public Multicaster(InetAddress address, int port) {
		this.socketAddress = new InetSocketAddress(address, port);
		this.group = socketAddress.getAddress();
		this.port = socketAddress.getPort();
		try {
			socket = new MulticastSocket(port);
			socket.joinGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			socket.leaveGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String string) {
		byte[] buffer = new byte[bufferSize];
		buffer = string.getBytes(charset);
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public MulticastMessage receiveMessage() {
		byte[] buffer = new byte[1028];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
		try {
			socket.receive(packet);
			String textMessage = new String(packet.getData(), 0, packet.getLength(), Charset.forName("UTF-8"));
			MulticastMessage message = new MulticastMessage(textMessage, packet.getAddress());
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
