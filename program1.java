package songpack;

import java.io.IOException;
import java.util.ArrayList;

public class program1 {

    public static void main(String[] args) {
            String filePath = args[0]; 
            String genre = args[1]; 

            try {
                // Step 1: Read songs into a BinarySearchTree
                long startTime = System.currentTimeMillis();
                BinarySearchTree bst = MyDataReader.readFileToBST(filePath, genre);
                long endTime = System.currentTimeMillis();
                long readAndInsertTime = endTime - startTime;
                System.out.println(readAndInsertTime + " milliseconds to read the " + genre + " songs into a binary search tree");
                startTime = System.currentTimeMillis();
                ArrayList<Song> sortedSongs = bst.toSortedArrayList();
                System.out.print("Top-10 titles of songs with the highest number of views:\n[");
                for (int i = sortedSongs.size() - 1; i >= sortedSongs.size() - 10 && i >= 0; i--) {
                    System.out.print(sortedSongs.get(i).getTitle());
                    if (i > sortedSongs.size() - 10 && i > 0) {
                        System.out.print(", ");
                    }
                }
                
                System.out.println("]");
                endTime = System.currentTimeMillis();
                long top10Time = endTime - startTime;
                System.out.println(top10Time + " milliseconds to find top-10 popular songs");
                startTime = System.currentTimeMillis();
                BinarySearchTree clonedTree = bst.clone();
                endTime = System.currentTimeMillis();
                long cloneTime = endTime - startTime;
                System.out.println(cloneTime + " milliseconds to clone the tree");
                clonedTree.filterByView(1000, 10000);
                boolean isValid = clonedTree.isBST();
                System.out.println("validation result of cloning and filtering: " + isValid);
                long filterAndValidateTime = System.currentTimeMillis() - endTime;
                System.out.println(filterAndValidateTime + " milliseconds to filter the tree and validate the binary search tree");
                startTime = System.currentTimeMillis();
                ArrayList<String> popularArtists = clonedTree.popularArtists();
                System.out.print("The Artist with the highest view in the given range:\n");
                if (!popularArtists.isEmpty()) {
                    System.out.println(String.join(", ", popularArtists));
                } else {
                    System.out.println("No artists found in the specified range.");
                }
                endTime = System.currentTimeMillis();
                long findPopularArtistsTime = endTime - startTime;
                System.out.println(findPopularArtistsTime + " milliseconds to find popular artists");
            } catch (IOException e) {
                System.out.println("Error reading the file: " + e.getMessage());
            }
        }
    }
