package cipherProj;

public class Receiver implements Encryptable {
	private char[] m_Sbox;
	private final int NUMROUNDS = 32;
	private char[] m_key;
	
	public Receiver(char[] key)
	{
		m_key = key;
		
		m_Sbox = new char[256];
		for (char i = 0; i < 256; i++) {
			m_Sbox[i] = i;
		}
	}
	
	
	public char[] decrypt(char[] input)
	{
		//input will be of length multiple of 8
		int numBlocks = input.length/8;
		
		for (int i = 0; i < numBlocks; i++) {
			char[] block = new char[8];
			
			for (int j = 0; j < 8; j++) {
				block[j] = input[j+8*i];
			}
			
			char[] decryptBlock = decryptBlock(block);
			
			for (int j = 0; j < 8; j++) {
				input[j+8*i] = decryptBlock[j];
			}
		}
		
		int size = input.length;
		while (input[size-1] == 0) {
			size--;
		}
		
		char[] output = new char[size];
		for (int i = 0; i < size; i++) {
			output[i] = input[i];
		}
		
		return output;
	}
	
	
	
	private char[] decryptBlock(char[] block)
	{
		//for (int j = 0; j < NUMROUNDS; j++) {
			for (int i = 7; i > -1; i--) {
				char temp = (char) (((block[i%8] << 7) & 0xFF) | ((block[i%8] >> 1) & 0xFF));
				temp = (char) (temp  - block[(i+1)%8] - m_Sbox[(int)(m_key[i%8]%256)]);
				block[i%8] = temp;
			}
		//}
			for (int i = 0; i < 8; i++) {
				block[i] = (char) (block[i]%256);
			}
		return block;
	}


	@Override
	public char[] encrypt(String str) {
		// TODO Auto-generated method stub
		return null;
	}
}
