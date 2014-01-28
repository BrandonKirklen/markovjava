import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import java.nio.charset.Charset;

public class Markov {
	// Hashmap
	public static Hashtable<String, Vector<String>> markovChain = new Hashtable<String, Vector<String>>();
	static Random rnd = new Random();
	
	public static void main(String[] args) throws IOException {
        
        
        int sentences = Integer.parseInt(args[0]);

        // Create the first two entries (k:_start, k:_end)
        markovChain.put("_start", new Vector<String>());
        markovChain.put("_end", new Vector<String>());
        
        for (int i=1; i<args.length; i++) {
        
            String filePath0 = args[i];
            FileInputStream file_in0 = new FileInputStream(filePath0);
            BufferedReader reader0  = new BufferedReader(new InputStreamReader( file_in0, Charset.forName("UTF-8")));
            String line = "";
            
            while ((line = reader0.readLine()) != null) {
                if (line.compareTo("")!=0) {
                    addWords(line);
                }
            }
        
            reader0.close();
        }


        while (sentences > 0 ) {
            generateSentence();
            sentences--;
        }
	}
	
	/*
	 * Add words
	 */
	public static void addWords(String phrase) {

		// put each word into an array
		String[] words = phrase.split(" ");
				
		// Loop through each word, check if it's already added
		// if its added, then get the suffix vector and add the word
		// if it hasn't been added then add the word to the list
		// if its the first or last word then select the _start / _end key
		
		for (int i=0; i<words.length; i++) {
		
			// Add the start and end words to their own
			if (i == 0) {
				
                Vector<String> startWords = markovChain.get("_start");
				startWords.add(words[i]);
				
				Vector<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
					
                    if ( i+1 < words.length ) {
                        suffix = new Vector<String>();
                        suffix.add(words[i+1]);
                        markovChain.put(words[i], suffix);
                    }
				}
				
			} else if (i == words.length-1) {
				Vector<String> endWords = markovChain.get("_end");
				endWords.add(words[i]);
				
			} else {	
				Vector<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
					suffix = new Vector<String>();
					suffix.add(words[i+1]);
					markovChain.put(words[i], suffix);
				} else {
					suffix.add(words[i+1]);
					markovChain.put(words[i], suffix);
				}
			}
		}	
	}
	
	
	/*
	 * Generate a markov phrase
	 */
	public static void generateSentence() {
		// Vector to hold the phrase
		Vector<String> newPhrase = new Vector<String>();
		
		// String for the next word
		String nextWord = "";
				
		// Select the first word
		Vector<String> startWords = markovChain.get("_start");
		int startWordsLen = startWords.size();

		nextWord = startWords.get(rnd.nextInt(startWordsLen));
		newPhrase.add(nextWord);
		
		// Keep looping through the words until we've reached the end
        try {
            while ( nextWord.charAt(nextWord.length()-1) != '.' 
                    && nextWord.charAt(nextWord.length()-1) != ','
                    && nextWord.charAt(nextWord.length()-1) != '?'
                    ) {

                if ( nextWord.compareTo("") != 0 ) {
                    Vector<String> wordSelection = markovChain.get(nextWord);
            
                    //try {
                    int wordSelectionLen = wordSelection.size();
                    nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));

                    if ( nextWord.compareTo("") != 0 ) {
                        newPhrase.add(nextWord);
                    }
                    else {
                        if ( wordSelectionLen <= 1 ) {
                            break;
                        }
                        while ( nextWord.compareTo("")==0 ) {
                            nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
                        }

                    }
                    //} 
                    //catch (Exception e) {
                    //}
                }
                else {
                    break;
                }
                
            }
        }
        catch (Exception e) {
            System.out.println("Exception caught: " + e);
            System.out.println("Next word was: [" + nextWord + "]");
        }

		
        String newPhraseString = newPhrase.toString();
        String strToOutput = newPhraseString.substring(1, newPhraseString.length()-1);
        strToOutput = strToOutput.replace(",", "");
		System.out.println(strToOutput);
	}
}
