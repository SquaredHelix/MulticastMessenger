package me.kristoffer.multicastmessenger;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SeedUtil {
	
	// Testing method
	public static void main(String[] args) {
		SeedUtil seed = new SeedUtil();
		while(true) {
			seed.open();
		}
	}
	
	// Testing method
	public void open() {
		Scanner scanner = new Scanner(System.in);
		String inputSeed = "";
		while (scanner.hasNextLine()) {
			inputSeed = scanner.nextLine();
			break;
		}
		getHashedAddress(inputSeed);
		System.out.println(getHashedAddress(inputSeed).getHostAddress());
	}
	
	public static InetAddress getHashedAddress(String seed) {
		int seeds = new BigInteger(seed.getBytes()).intValue();
		if (seeds < 0) {
			seeds = seeds * -1; // Ensure above 0
		}
		byte a = (byte) 224;
		byte b = (byte) (seeds % 127);
		byte c = (byte) (seeds % (b + 33));
		byte d = (byte) (seeds % (b + 71));
		try {
			return InetAddress.getByAddress(new byte[] {a, b, c ,d});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
