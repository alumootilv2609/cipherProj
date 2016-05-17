package cipherProj;

import java.util.Scanner;

public class TreyferTester {
	public static void main(String[] args)
	{
		char[] key = {0xFF, 0x36, 0x32, 0xDE, 0x6B, 0xE5, 0x9D, 0x0A};
		Transmitter transTest = new Transmitter(key);
		Receiver receiverTest = new Receiver(key);
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter Message: ");
		String str = keyboard.next();
		
		char[] encrypted = transTest.encrypt(str);
		
		for (char i : encrypted) {
			System.out.print(i+" "); 
		}
		System.out.println();
		
		char[] decrypted = receiverTest.decrypt(encrypted);
		
		for (char i: decrypted) {
			
		}
			
		
			
		
		
		
		
		
		
	}
}
