/* Author: James Bui
 * Date: 7/23/2024
 * Description: Player.java mostly keeps track of the player's current chip count and the highest chip count the player reached in a instance of game.
 */

public class Player {
    private static int chipCount = 1000;
    public static int currentHighScore = ReadConfigFile.readCurrentHighScore();

    public static int getChipCount() {
        return chipCount;
    }

    public static void addChips(int amount) {
        chipCount += amount;
    }
}
