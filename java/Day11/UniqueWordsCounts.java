import java.util.*;

/**
 * Identifies unique words in a document, and their numbers of occurrences, via a map
 *
 * @author Haris Baig and Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Tim Pierson, CS 10, Winter 2017 -- added comments
 * @author Tim Pierson, Dartmouth CS10, provided for Winter 2024
 */
public class UniqueWordsCounts {
	public static void main(String[] args) {
		String page = "Pretend that this string was loaded from a web page.  We won't go to all that trouble here.  This string contains multiple words. And multiple copies of multiple words.  And multiple words with multiple copies.  It is to be used as a test to demonstrate how sets work in removing redundancy by keeping only one copy of each thing. Is it very very redundant in having more than one copy of some words?";
		String[] allWords = page.split("[ .,?!]+");	
		// Declare new Map to hold count of each word
		Map<String,Integer> wordCounts = new TreeMap<String,Integer>(); // word -> count
		// Loop over all the words split out of the string, adding to map or incrementing count
		for (String s: allWords) {
			String word = s.toLowerCase();
			// Check to see if we have seen this word before, update wordCounts appropriately
			if (wordCounts.containsKey(word)) {
				// Have seen this word before, increment the count. Overwrites value of key word
				wordCounts.put(word, wordCounts.get(word)+1); // Cant just do this line because when you do get(word) will return null if it is not already in map
			}
			else {
				// Have not seen this word before, add the new word (Java returns null if item not in Map, so can't do get +1)
				wordCounts.put(word, 1);
			}
		}
		// Print word counts
		System.out.println(wordCounts);
	}
}
