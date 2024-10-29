/* Author: James Bui
 * Date: 7/2/2024
 * Description: ReadConfigFile.java reads the config.txt file for Countup!, which would have the high score for a user stored and is read locally. This is a simple read/write 
 * IO file, that is made seperate from the other logic for maintenance purposes.
 */

import java.io.*;

public class ReadConfigFile {
    // Running this file alone effectively resets the highscore to 0.
    public static void main(String [] args) {
        writeNewHighScore(0);
    }

    // Method reads the current high score from the config.txt file.
    public static int readCurrentHighScore() {
        //Path containing config.txt file. User must replace file path with their own!
        String filePath = "C:/Users/config.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            reader.close();

            
            // Parse the string and get the number value.
            String[] parts = line.split("=");

            // Code used for error testing.
            if (parts.length < 2) {
                System.err.println("Invalid format in the file. Expected 'High Score = <value>'.");
                return 0;
            }

            // Gets the old highscore.
            return Integer.parseInt(parts[1].trim());
        } catch (IOException e) {
            System.err.println("Error reading or writing to the file" + e.getMessage());
            return 0;
        }
    }

    public static void writeNewHighScore(int newHighScore) {
        //Path containing config.txt file. User must replace file path with their own!
        String filePath = "C:/Users/config.txt";

        try {
            String updatedLine = "High Score = " + newHighScore;

            // Write the updated score to the file.
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(updatedLine);
            writer.close();

            System.out.println("High score has been updated successfully.");

        } catch (IOException e) {
            System.err.println("Error reading or writing to the file" + e.getMessage());
        }
    }
}
