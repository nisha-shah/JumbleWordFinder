package com.solution.jumblefinder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JumbleWordFinder {
	
	private static char[] convertToSortedChars(String str){
		char[] sortedArr = new char[str.length()];
		 
		for (int i=0; i<str.length(); i++){
			sortedArr[i] = str.toLowerCase().charAt(i);
		}
		 
		Arrays.sort(sortedArr);
		return sortedArr;
		 
	}
	
	public static HashMap<String, ArrayList<String>> readWords() throws FileNotFoundException, IOException{
		// Map used to maintain a list of all anagrams 
		// where key is a string of sorted characters in the anagram
		// and value is a list of all possible words using those characters
		// For eg : if key : "dgo" then value will be ["dog", "god"]
		HashMap<String, ArrayList<String>> wordsMap = new HashMap<String, ArrayList<String>>();
		
		// File containing all words obtained from 
		// http://svn.pietdepsi.com/repos/projects/zyzzyva/trunk/data/words/North-American/OWL2.txt
		try(BufferedReader br = new BufferedReader(new FileReader("english-words.txt"))) {
			 String line = br.readLine();
			 
			 while (line != null) {
				 char[] sortedArr = convertToSortedChars(line);
				 String key = new String(sortedArr);
				 
				 // build sorted map
				 ArrayList<String> stringList;
				 if (wordsMap.containsKey(key)){
					 stringList = wordsMap.get(key);
				 }
				 else{
					 stringList = new ArrayList<>();
				 }
				 stringList.add(line);
				 wordsMap.put(key, stringList);
				
				 line = br.readLine();
			 }
		 }
		return wordsMap;
		
	}
	
	public static void main(String[] args) {
		HashMap<String, ArrayList<String>> wordsMap = new HashMap<String, ArrayList<String>>();
		try { 
			wordsMap = readWords();
		} catch (IOException e) {
			System.out.println("Could not read file");
			e.printStackTrace();
		}
		
		String str = args[0];
		char[] sortedArr = convertToSortedChars(str); 
		
		 ArrayList<String> foundWords = new ArrayList<String>();
		 
		 System.out.println("List of possible words : ");
		 // Iterate through all possible combinations of sub-words possible from the give word
		 for (int len=2; len<=sortedArr.length; len++){
			 for (int i=0; i<sortedArr.length; i++){
				 for (int j=i+1; j<sortedArr.length - len + 2; j++){
					
				 	char[] subArr = new char[len];
				 	
				 	subArr[0] = sortedArr[i];
				 	subArr[1] = sortedArr[j];
				 	 
				 	for (int k=1; k <= len - 2; k++){
					 	subArr[k+1] = sortedArr[j+k];
				 	}

				 	String subStr = new String(subArr);
					
				 	// If sub-word present in the map
				 	// take all words possible using those characters
				 	
					if (wordsMap.containsKey(subStr)){
						for (String word : wordsMap.get(subStr)){
							foundWords.add(word);
							System.out.println(word);
						}
					}
					 
				 }
			 }
		 }
		 System.out.println("Total words Found :" + foundWords.size());
	}

}
