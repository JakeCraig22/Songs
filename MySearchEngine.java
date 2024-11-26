package songpack;

import java.util.*;
import java.util.Map.Entry;

public class MySearchEngine {
	TreeMap<Song, TreeMap<String, Double>> tf = new TreeMap<>();
	TreeMap<String, Double> idf = new TreeMap<>();
	TreeMap<Song, Double> avgLength = new TreeMap<>();
	
	public MySearchEngine(ArrayList<Song> songs) {
		calculateTF(songs);
		calculateIDF(songs);
		calculateAvgLength(songs);
	}
	
	
	/**
	 * @param songs the songs to iterate through
	 * 
	 * Makes a hashmap of lyrics from words iterated by checking if the hashmap contains the word
	 * (if it does adds 1, if not, makes a new key) 
	 * Makes new treemap termFrequency which stores the song as the key and tfr as the value. 
	 * adds termFrequency map to the tf map
	 * 
	 */
	private void calculateTF(ArrayList<Song> songs) {
		
		for(Song s: songs) {
			String[] words = s.getLyrics().split(" ");
			HashMap<String, Integer> lyrics = new HashMap<>();
				for(String word: words) {
					if(lyrics.containsKey(word)) {
						lyrics.put(word, lyrics.get(word) + 1);
					} else {
						lyrics.put(word,  1);
					}
				}
				int totalWords = words.length;
				TreeMap<String, Double> termFrequency = new TreeMap<>();
				
				for (Map.Entry<String, Integer> entry : lyrics.entrySet()) {
			        String word = entry.getKey();
			        int count = entry.getValue();
			        double frequencyRatio = (double) count / totalWords;
			        
			        termFrequency.put(word, frequencyRatio);
				}
				tf.put(s, termFrequency);
		}
	}
	
	/**
	 * @param songs the sogns to iterate through
	 *calculates the IDF for each unique word across all songs, stores it in the idf treeMap.
	 *
	 *Iterates through each song and gets multiple unique words from each song and then counts how many 
	 *songs each word appears in.
	 * 
	 */
	private void calculateIDF(ArrayList<Song> songs) {
		//iterate on each song
		for(Song s: songs) {
			String[] words = s.getLyrics().split(" ");
			TreeSet<String> uniqueWords = new TreeSet<String>(Arrays.asList(words));
			
			for(String word: uniqueWords) {
				idf.put(word, idf.getOrDefault(word, 0.0) + 1);
			}
		}
		for(String word: idf.keySet()) {
			//n_x
			double n_x_value = idf.get(word);
			double idfValue = (songs.size() - n_x_value + 0.5)/(n_x_value + 0.5);
			idfValue += 1;
			idfValue = Math.log(idfValue);
			idf.put(word, idfValue);
		}
	}
	
	/*
	 * For songs, each song in the treemap, Length of a song/avgLength
	 * of all songs. Eg a song with 5 words would have length 5. Avg
	 * Length would be n1 + n2 + n3/AvgSongs
	 * 
	 */
	private void calculateAvgLength(ArrayList<Song> songs) {
			String[] numWords;
			double totalWords = 0;
			for(Song song: songs) {
				numWords = song.getLyrics().split("\\s+");
				totalWords += numWords.length;
			}
		 	
			for(Song song: songs) {
				double sum = totalWords/songs.size();
				avgLength.put(song, sum);
			}

	}
	
	
	/**
	 * @param song songs to get the avg length of
	 * @param query the search as a single string
	 * @return the relevance score based on the BM25 Formula
	 * 
	 * calculates the relevancy score of a song based on the BM25 ranking formula.
	 */
	private double calculateRelevance(Song song, String query) {
		double relevanceSc = 0.0;
		double k1 = 1.2;
		double b = 0.75;
		double avgSongLength = avgLength.get(song);
		
		String[] queryWords = query.toLowerCase().split(" ");
			for(String word : queryWords) {
				if(!idf.containsKey(word)) {
					continue;
				}
				double idfValue = idf.get(word);
				
				TreeMap<String, Double> songTermFrequency = tf.get(song);
				if(!songTermFrequency.containsKey(word)) {
						continue;
				}
				double tfValue = songTermFrequency.get(word);
				double songLength = songTermFrequency.size();
				
				//math equation for BM25
				double numerator = tfValue * (k1+1);
				double denominator = tfValue + k1 * (1 - b + b * songLength/ avgSongLength);
				double score = idfValue *(numerator/denominator);
				relevanceSc += score;
				
			}
			return relevanceSc;
	}
	
	/**
     * This method returns a sorted list of top k results as a list of entries
     * The return value is a list so that the order is not changed.
     */
    private List<Map.Entry<Song, Double>> sortedByValue(TreeMap<Song, Double> treeMap, int topK) {
        List<Map.Entry<Song, Double>> list = new ArrayList<>(treeMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Song, Double>>() {
            @Override
            public int compare(Map.Entry<Song, Double> o1, Map.Entry<Song, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
        int counter = 0;
        List<Map.Entry<Song, Double>> results = new ArrayList<Map.Entry<Song, Double>>();
        while (counter < topK) {
            results.add(Map.entry(list.get(counter).getKey(), list.get(counter).getValue()));
            counter += 1;
        }
        return results;
    }
	
	/**
     * Prints the search results in the specified format.
     * @param query the search query
     * @param results the list of top results to print
     */
    private void printSearchResults(String query, List<Map.Entry<Song, Double>> results) {
        System.out.println("Results for " + query);
        int rank = 1;
        for (Map.Entry<Song, Double> entry : results) {
            System.out.println(rank + ": " + entry.getKey().getTitle() + " by " 
                               + entry.getKey().getArtist() + "\t" + entry.getValue());
            rank += 1;
        }
    }
	
	/**
	 * @param query the search query as a string
	 * @return a list of the top 5 songs that are the most relevant to the search query
	 */
	public void search(String query) {
        TreeMap<Song, Double> scores = new TreeMap<>();
        for (Song song : tf.keySet()) {
            double relevance = calculateRelevance(song, query);
            scores.put(song, relevance);
        }

        // Use sortedByValue to get the top 5 results
        List<Map.Entry<Song, Double>> topResults = sortedByValue(scores, 5);

        // Print the search results in the required format
        printSearchResults(query, topResults);
    }

	
	
}


	