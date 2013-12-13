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

        String filePath0 = args[0];
        String filePath1 = args[1];

        FileInputStream file_in0 = new FileInputStream(filePath0);
        FileInputStream file_in1 = new FileInputStream(filePath1);

        BufferedReader reader0  = new BufferedReader(new InputStreamReader( file_in0, Charset.forName("UTF-8")));
        BufferedReader reader1  = new BufferedReader(new InputStreamReader( file_in1, Charset.forName("UTF-8")));
        String line = "";
        
		// Create the first two entries (k:_start, k:_end)
		markovChain.put("_start", new Vector<String>());
		markovChain.put("_end", new Vector<String>());
		
        while ((line = reader0.readLine()) != null) {
            if (line.compareTo("")!=0) {
                addWords(line);
            }
		}
        while ((line = reader1.readLine()) != null) {
            if (line.compareTo("")!=0) {
                addWords(line);
            }
		}
        
        reader0.close();
        reader1.close();


        int sentences = Integer.parseInt(args[2]);

        while (sentences > 0 ) {
            generateSentence();
            sentences--;
        }
	}
	
	/*
	 * Add words
	 */
	public static void addWords(String phrase) {

        //System.out.println("Adding words...");

		// put each word into an array
		String[] words = phrase.split(" ");
				
		// Loop through each word, check if it's already added
		// if its added, then get the suffix vector and add the word
		// if it hasn't been added then add the word to the list
		// if its the first or last word then select the _start / _end key
		
		for (int i=0; i<words.length; i++) {
		
//debug//            System.out.println( "word = " + words[i] );

			// Add the start and end words to their own
			if (i == 0) {
 //debug//               System.out.println("Start words...");
				
                Vector<String> startWords = markovChain.get("_start");
				startWords.add(words[i]);
				
				Vector<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
 //debug//                   System.out.println("Suffix == null...");
					
                    if ( i+1 < words.length ) {
                        suffix = new Vector<String>();
                        suffix.add(words[i+1]);
                        markovChain.put(words[i], suffix);
                    }
				}
				
			} else if (i == words.length-1) {
 //debug//               System.out.println("End words...");
				Vector<String> endWords = markovChain.get("_end");
				endWords.add(words[i]);
				
			} else {	
 //debug//               System.out.println("Getting words...");
				Vector<String> suffix = markovChain.get(words[i]);
				if (suffix == null) {
 //debug//                   System.out.println("Suffix == null...");
					suffix = new Vector<String>();
					suffix.add(words[i+1]);
 //debug//                   System.out.println("Adding words...\n===" + words[i+1]);
					markovChain.put(words[i], suffix);
				} else {
 //debug//                   System.out.println("Suffix != null...");
					suffix.add(words[i+1]);
 //debug//                   System.out.println("Adding suffix...\n===" + suffix + "\n===words..." + words[i+1]);
					markovChain.put(words[i], suffix);
				}
			}
		}	


        //debug//System.out.println("\n\n\n=====\n\n\n");
        //debug//System.out.println(markovChain);
        //debug//System.out.println("\n\n\n=====\n\n\n");

        //debug//generateSentence();


	}
	
	
	/*
	 * Generate a markov phrase
	 */
	public static void generateSentence() {
        //debug//System.out.println("Generating sentence...");
		
		// Vector to hold the phrase
		Vector<String> newPhrase = new Vector<String>();
		
		// String for the next word
		String nextWord = "";
				
		// Select the first word
		Vector<String> startWords = markovChain.get("_start");
		int startWordsLen = startWords.size();

        //debug//System.out.println("startWordsLen = " + startWordsLen);


		nextWord = startWords.get(rnd.nextInt(startWordsLen));
		newPhrase.add(nextWord);
		
        //System.out.println("nextWord = " + nextWord);
		// Keep looping through the words until we've reached the end
		while (nextWord.charAt(nextWord.length()-1) != '.' ) {
			Vector<String> wordSelection = markovChain.get(nextWord);
             
            try {
                int wordSelectionLen = wordSelection.size();
                nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
                newPhrase.add(nextWord);
            } 
            catch (Exception e) {
                
            }
		}
		
		//debug//System.out.println("New phrase: " + newPhrase.toString());	
        String newPhraseString = newPhrase.toString();
        String strToOutput = newPhraseString.substring(1, newPhraseString.length()-1);
        strToOutput = strToOutput.replace(",", "");
		System.out.println(strToOutput);
	}
}
