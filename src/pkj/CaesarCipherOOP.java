package pkj;

public class CaesarCipherOOP {
	private String alphabet;
	private String shiftedAlphabet;
	private int mainkey;
	
	public CaesarCipherOOP(int key) {
		this.mainkey = key;
		alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0,key);
		alphabet = alphabet + alphabet.toLowerCase(); 
		shiftedAlphabet = shiftedAlphabet + shiftedAlphabet.toLowerCase();
	}
	
	public String encrypt(String input){
		return transform(input, alphabet, shiftedAlphabet);
	}
	
	public String decrypt(String input) {
		return transform(input, shiftedAlphabet, alphabet);
	}
	
	private char transformLetter(char c,String from,String to) {
		int indx = from.indexOf(c);
		if(indx != -1) {
			return to.charAt(indx);
		}
		return c;	
	}
	
	public char encryptLetter(char c) {
		return transformLetter(c, alphabet, shiftedAlphabet);
	}
	
	public char decryptLetter(char c) {
		return transformLetter(c, shiftedAlphabet, alphabet);
	}
	
	private String transform(String input,String from,String to) {
		StringBuilder sb = new StringBuilder(input);
		for(int i = 0;i<sb.length();i++) {
			char c = sb.charAt(i);
			c = transformLetter(c, from, to);
			sb.setCharAt(i, c);
		}
		return sb.toString();
	}
	
	public String toString() {
		return "" + mainkey;
	}
}
