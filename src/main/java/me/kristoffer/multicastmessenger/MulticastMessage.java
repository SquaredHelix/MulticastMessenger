package me.kristoffer.multicastmessenger;

import java.net.InetAddress;

public class MulticastMessage {
	
	private String message;
	private InetAddress author;
	
	public MulticastMessage(String message, InetAddress author) {
		this.message = message;
		this.author = author;
	}
	
	public String getMessage() {
		return message;
	}
	
	public InetAddress getAuthor() {
		return author;
	}
	
}
