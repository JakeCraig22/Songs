package songpack;

import java.io.IOException;
import java.util.ArrayList;

public class Program6 {

    public static void main(String[] args) {
    	String filePath = args[0]; 

        try {
            long indexingStartTime = System.currentTimeMillis();
            ArrayList<Song> songs = MyDataReader.readAllSongs(filePath);
            MySearchEngine searchEngine = new MySearchEngine(songs);
            long indexingEndTime = System.currentTimeMillis();
            System.out.println((indexingEndTime - indexingStartTime) + " milliseconds to build the index");

            String[] queries = {
                "We are the Champions",
                "I will always love you",
                "walking on sunshine",
                "dancing in the rain",
                "put your hands in the jupiter sky"
            };

            for (String query : queries) {
                long searchStartTime = System.currentTimeMillis();
                searchEngine.search(query);
                long searchEndTime = System.currentTimeMillis();
                System.out.println((searchEndTime - searchStartTime) + " milliseconds to search for \"" + query + "\"");
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}