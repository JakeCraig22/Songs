package songpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class MyDataReader {
	
    /**
     * Process a line from the TSV file and returns the corresponding song object
     * @param inputLine TSV line
     * @return Song object
     */
    private static Song lineToReport(String inputLine)
    {
        String[] items = inputLine.split("\t");
        String title= items[0];
        String tag= items[1];
        String artist= items[2];
        int year= Integer.parseInt(items[3]);
        int views= Integer.parseInt(items[4]);
        String lyrics= items[5];
        
        Song s = new Song(title, tag, artist, year, views, lyrics);
        return s;
    }
    
    /**
     * This method takes in the tsv file path and returns the binary search tree of songs with the given tag
     * @param tsvFilePath tsv file path
     * @param tag one of the six tags: rap, rb, pop, rock, misc, and country
     * @return binary search tree of songs
     * @throws IOException
     */
    public static BinarySearchTree readFileToBST(String tsvFilePath, String tag) throws IOException
    {
        BinarySearchTree songsBST= new BinarySearchTree();
        int counter = 0;
        BufferedReader TSVReader = new BufferedReader(new FileReader(tsvFilePath));
            String line = TSVReader.readLine();
            while ((line = TSVReader.readLine()) != null) {   
                Song song = MyDataReader.lineToReport(line);
                if(song.getTag().equals(tag))
                  songsBST.insert(song);
              counter+=1;
              
            }
        
        return songsBST;
    }
    
    /**
     * Reads all songs from the TSV file into an ArrayList without filtering by tag.
     * @param tsvFilePath the path of TSV file
     * @return an ArrayList containing all Song objects in the file
     * @throws IOException if an I/O error occurs
     */
    public static ArrayList<Song> readAllSongs(String tsvFilePath) throws IOException {
        ArrayList<Song> songs = new ArrayList<>();
        BufferedReader TSVReader = new BufferedReader(new FileReader(tsvFilePath));
        String line = TSVReader.readLine(); 
        while ((line = TSVReader.readLine()) != null) {
            Song song = lineToReport(line);
            songs.add(song);
        }
        TSVReader.close();
        return songs;
    }
    
    
}
