package pkj;

import java.io.FileNotFoundException;

public class Tester {
	
	public Tester() {
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		VigenereBreaker vb= new VigenereBreaker();
		vb.breakVigenere();
	}
}
