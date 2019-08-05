package pkj;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Arrays;
import java.util.HashSet;

public class VigenereBreaker {
	
	public VigenereBreaker() {
		
	}
	
	public String sliceString(String message,int whichslice,int totalslices) {
		StringBuilder answer = new StringBuilder();
		for(int i = whichslice;i<message.length();i = i + totalslices) {
			answer.append(message.charAt(i));
		}
		return answer.toString();
	}
	
	public int[] tryKeyLength(String encrypted,int klength,char mostCommon) {
		int[] keys = new int[klength];
		for(int i = 0;i<klength;i++) {
			String s = sliceString(encrypted, i, klength);
			int key = new CaesarBreaker(mostCommon).getKey(s);
			keys[i] = key;
		}
		return keys;
	}
	
	public void breakVigenere() throws FileNotFoundException {
		HashMap<String,HashSet<String>> map = new HashMap<String,HashSet<String>>();
		StringBuilder sb = new StringBuilder();
		DirectoryResource dr = new DirectoryResource();
		for(File f : dr.selectedFiles()) {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(f).useDelimiter(" ");
			while(sc.hasNext()) {
			sb.append(sc.next());
			sb.append(" ");
			}
			sc.close();
		}
		
//		File f = new File("secretmessage4.txt");		
		
		File fd = new File("dictionaries");
		for(File fr: fd.listFiles()) {
			HashSet<String> hs = readDictionary(fr);
			map.put(fr.getName(), hs);
		}
		

		String encrypted = sb.toString();
		/***************************************************************/
		/*if you want to decrypt using a specific language*/
//		String decrypted = breakForLanguage(encrypted, map.get("German"));
		/**************************************************************/
		
		/*if you want to decrypt without knowing a language */
		String decrypted = breakForAllLangs(encrypted, map);
		
		System.out.println("the decrypted message: ");
		System.out.print(decrypted);

	}
	
	public HashSet<String> readDictionary(File f) throws FileNotFoundException{
		Scanner sc = new Scanner(f);
		HashSet<String> hs = new HashSet<String>();
		while(sc.hasNext()) {
			String word = sc.nextLine();
			hs.add(word.toLowerCase());
		}
		sc.close();
		return hs;
	}
	
	public int countWords(String message,HashSet<String> hs) {
		String[] st = message.split("\\W");
		int count = 0;
		for(String s: st) {
			if(hs.contains(s.toLowerCase())) {
				count++;
			}
		}
		return count;
	}
	
	public String breakForLanguage(String encrypted,HashSet<String> dictionary) {
		int max = 0;
		int keylength = 0;
		String decrypted = "";
		char mostcommon = mostCommonCharIn(dictionary);
		System.out.println("the most common char: " + mostcommon);
		for(int i = 1; i< 100 ; i++) {//can be from 1 to encrypted.length() but it will take more time
			int[] keys = tryKeyLength(encrypted, i, mostcommon);
			decrypted = new VigenereCipher(keys).decrypt(encrypted);
			int test = countWords(decrypted, dictionary);
			if(max < test){
				max = test;
				keylength = i;
			}
			
		}
		if(max == 0) {
		return decrypted;
		}
		System.out.println("the valid words: " + max + " out of "+ encrypted.split("\\W+").length);
		int[] keys = tryKeyLength(encrypted, keylength, mostcommon);
		System.out.println("The Keys: " + Arrays.toString(keys));
		System.out.println("The Keys length: " + keys.length);
		decrypted = new VigenereCipher(keys).decrypt(encrypted);

		
		return decrypted;
	}
	
	public char mostCommonCharIn(HashSet<String> dictionary) {
		String alphabetic = new String("abcdefghijklmnopqrstuvwxyz");
		int[] freq = new int[26];
		for(String s:dictionary) {
			freq = countLetters(s, freq);
		}
		int indxofmax = indexOfMax(freq);
		return alphabetic.charAt(indxofmax);
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
	
	public int[] countLetters(String phrase,int[] freq) {
		String alphabetic = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
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
	
	public String breakForAllLangs(String encrypted, HashMap< String, HashSet<String> > map){
		String answer = "";
		int max = 0;
		String language = "";
		for(String s:map.keySet()) {
			HashSet<String> lang = map.get(s);
			String decrypted = breakForLanguage(encrypted, lang);
			int count = countWords(decrypted, lang);
			if(max<count) {
				max = count;
				answer = decrypted.toString();
				language = s;
			}
			System.out.println(s);
			
		}
		System.out.println("the language is " + language);
		return answer;
	}
}
