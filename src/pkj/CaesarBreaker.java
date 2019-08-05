package pkj;

public class CaesarBreaker {
	
	char mostCommon;
	
	public CaesarBreaker(){
		mostCommon = 'e';
	}
	
	public CaesarBreaker(char c){
		mostCommon = c;
	}
	
	public int[] countLetters(String phrase) {
		String alphabetic = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		int[] freq = new int[26];
		for(int i = 0; i < phrase.length() ; i++) {
			char test = Character.toUpperCase(phrase.charAt(i));
			
			int indx  = alphabetic.indexOf(test);
			if(indx != -1)
			{
				freq[indx]++;
			}
		}
		return freq;
	}
	
	public String decrypt(String encrypted) {
		int key = getKey(encrypted);
		CaesarCipherOOP cc = new CaesarCipherOOP(key);
		return cc.decrypt(encrypted);
	}
	
	public String halfOfString(String message,int start) {
		StringBuilder sb = new StringBuilder();
		for(int i = start ; i<message.length();i= i+2) {
			sb.append(message.charAt(i));
		}
		return sb.toString();
	}
	
	
	public int getKey(String s) {
		int[] freq = countLetters(s);
		int maxindx = indexOfMax(freq);
		int mostcommonpos = mostCommon - 'a';
		int dkey = maxindx - mostcommonpos;
		if(dkey < 0) {
			dkey = 26 - (mostcommonpos - maxindx);
		}
		return dkey;
	}
	
	public int  indexOfMax(int[] values) {
		int max = 0;
		for(int i = 1 ; i<values.length; i++) {
			if(values[i] > values[max]) {
				max = i;
			}
		}
		return max;
	}	
	
}
