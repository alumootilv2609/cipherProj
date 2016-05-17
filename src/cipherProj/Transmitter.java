package cipherProj;

import java.util.Arrays;

public class Transmitter {
	char[] m_Sbox;
	private final int NUMROUNDS = 32;
	char[] m_key;
	
	public Transmitter(char[] key)
	{
		m_Sbox = new char[256];
		for (char i = 0; i < 256; i++) {
			m_Sbox[i] = i;
		}
		m_key = key;
	}
	
	
	public char[] encrypt(String str)
	{
		char[] input = new char[8*(int)Math.ceil(str.length()/8.0)];
		
		for (int i = 0; i < str.length(); i++) {
			input[i] = str.charAt(i);
		}
		for (int i = str.length(); i < input.length; i++ ) {
			input[i] = 0;
		}
		
		int numBlocks = input.length/8; // number of blocks
		
		for (int i = 0; i < numBlocks; i++) {
			char[] block = new char[8];
			
			for (int j = 0; j < 8; j++) {
				block[j] = input[j + 8*i];
			}
			
			char[] encryptBlock = encryptBlock(block);
			
			for (int j = 0; j < 8; j++) {
				input[j + 8*i] = encryptBlock[j];
			}
		}
		
		return input;
		
	}
	
	
	private char[] encryptBlock(char[] block)
	{
		//the block is of length 8, and has 8bit things in it, thought within a char 
		//for (int j = 0; j < NUMROUNDS; j++) {
			for (int i = 0; i < 8; i++) {
				char temp = block[i%8];
				temp = (char) ((temp + block[(i+1)%8] + m_Sbox[(int)(m_key[i%8]%256)])%256);
				temp = (char) (((temp << 1) & 0xFF) | ((temp >>7) & 0xFF));
				block[i%8] = temp;
			}
		//}
		return block;
	}
	
}
