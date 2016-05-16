package cipherProj;

import java.util.Arrays;

public class Transmitter {
	private byte[] m_key = {0x0F, 0x1D, 0x70, 0x34, 0x4D, 0x34, 0x2B, 0x6E};
	private final int numRounds = 32;
	
	private byte[] SBox;
	
	
	public Transmitter()
	{
		 SBox = new byte[256];
		 for (int i = 0; i < 256; i++) {
			 SBox[i] = (byte)i;
		 }
	}
	
	private byte[] encrypt(String input)
	{
		byte[] inputAsByte = input.getBytes();
		int size = inputAsByte.length;
		byte[] out = new byte[size];
		for (int i = 0; i < (int)Math.ceil(size/8.0); i++) {
			
			byte[] textBlock = new byte[8];
			
			for (int j = 0; j < 8; j++) {
				if (j + 8*i < size) {
					textBlock[j] = inputAsByte[j + 8*i];
				}
				else {
					textBlock[j] = 0;
				}
			}
			for (int k = 0; k < textBlock.length; k++) {
				System.out.print("c" +textBlock[k] +" ");
			}
			System.out.println();
			byte[] temp = treyferEncrypt(textBlock);
			
			for (int j = 0; j < 8; j++) {
				if (j + 8*i < size) {
					out[j+8*i] = temp[j];
				}
			}
		}
		
		return out;
		
	}
	
	private byte[] treyferEncrypt(byte[] text) // text is 8 length
	{
		/*byte t = text[0];
		for (int i = 0; i < 8*numRounds; i++) {
			t += m_key[i%8];
			if (t < 0) {
				t += 127;
			}
			t = (byte)(SBox[t] + text[(i+1)%8]);
			
			char temp = (char)t;

			temp = (char) (((temp << 1) & 0xFF) |((temp >> 7) & 0xFF ));
			t = (byte)temp;
			text[(i+1)%8] = t;
			
		}
		return text;	 */
		for (int i = 0; i < numRounds; i++) {
			for (int j = 0; j < 8; j++) {
				text[j] = (byte)(text[j] + text[(j+1)%8] + SBox[m_key[j]%256]);
				text[j] = (byte)((text[j] << 1) | (text[j] >> 7));
			}
		}
		/*for (int i = 0; i < text.length; i++) {
			System.out.print(text[i] +" ");
		} */
		return text;
	}
	
	private byte[] decrypt(byte[] text)
	{
		byte[] output = new byte[text.length];
		for (int i = 0; i < (int)Math.ceil(text.length/8.0); i++) {
			byte[] textBlock = new byte[8];
			for (int j = 0; j < 8; j++) {
				if (j + 8*i < text.length) {
					textBlock[j] = text[j + 8*i];
				}
				else {
					textBlock[j] = 0;
				}
			}
			byte[] temp = treyferDecrypt(textBlock);
			for (int j = 0; j < 8; j++) {
				if (j + 8*i < text.length) {
					output[j + 8*i] = temp[j];
				}
			}
		}
		return output;
	}
	
	private byte[] treyferDecrypt(byte[] text)
	{
		for (int r = 0; r < numRounds; r++)
		{

			for (int i = 7; i>-1; i--)
			{
				text[i] = (byte)((text[i] << 7) | (text[i] >> 1));
				text[i] = (byte)((text[i] + 512) - text[(i+1)%8] - SBox[(m_key[i]) % 256]); 

			}
		}
		return text;
	}
	
	public static void main(String[] args)
	{
		Transmitter test = new Transmitter();
		
		byte[] temp = test.encrypt("hello");
		for (byte i : temp) {
			System.out.print(i +" ");
		} 
		
		/*System.out.println();
		byte[] temp2 = test.decrypt(temp);
		for (byte i : temp2) {
			System.out.print(i + " ");
		}
		System.out.println();
		String str ="hello";
		byte[] temp3 = str.getBytes();
		for (byte i : temp3) {
			System.out.print(i +" ");
		} */
		
	}	
}
