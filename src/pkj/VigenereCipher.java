package pkj;

import java.util.Arrays;

public class VigenereCipher {
	
	private CaesarCipherOOP[] caesar;
	
	public VigenereCipher(int[] key) {
		caesar = new CaesarCipherOOP[key.length];
		for(int i = 0;i<caesar.length;i++) {
			caesar[i] = new CaesarCipherOOP(key[i]);
		}
	}
	
	public String encrypt(String input) {
		StringBuilder answer = new StringBuilder();
		for(int i = 0 ; i<input.length();i++) {
			int cipherindx = i % caesar.length;
			CaesarCipherOOP cipher = caesar[cipherindx];
			answer.append(cipher.encryptLetter(input.charAt(i)));
		}
		return answer.toString();
	}
	
	public String decrypt(String input) {
		StringBuilder answer = new StringBuilder();
		for(int i = 0 ; i<input.length();i++) {
			int cipherindx = i % caesar.length;
			CaesarCipherOOP cipher = caesar[cipherindx];
			answer.append(cipher.decryptLetter(input.charAt(i)));
		}
		return answer.toString();
	}
	
	public String toString() {
		return Arrays.toString(caesar);
	}
}
