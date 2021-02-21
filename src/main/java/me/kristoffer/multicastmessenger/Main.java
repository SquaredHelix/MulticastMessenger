package me.kristoffer.multicastmessenger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		InetAddress address = null;
		System.out.print("Input connection seed: ");
		while (scanner.hasNextLine()) {
			address = SeedUtil.getHashedAddress(scanner.nextLine());
			break;
		}
		Multicaster multicaster = new Multicaster(address, 8888);
		System.out.println("Started multicaster at " + address.getHostAddress());
		Thread multicastListenerThread = new Thread(() -> {
			while (true) {
				try {
					MulticastMessage message = multicaster.receiveMessage();
					String author = message.getAuthor().getHostAddress();
					if (author.equals(InetAddress.getLocalHost().getHostAddress()))
						author = "LOCAL";
					System.out.println("[" + author + "] " + message.getMessage());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});
		multicastListenerThread.start();
		while (scanner.hasNextLine()) {
			String message = scanner.nextLine();
			if (message.equals("close")) {
				break;
			}
			multicaster.sendMessage(message);
		}
		scanner.close();
		multicaster.close();
	}

}
