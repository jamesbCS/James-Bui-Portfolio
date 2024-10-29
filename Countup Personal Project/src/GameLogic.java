/* Author: James Bui
 * Date: 7/23/2024
 * Description: GameLogic.java includes the code that has to deal with the actual dealing of cards in CountUp!, the calculation against the house on whether or not the player wins,
 * and how much chips the player has gained/loss and their overall score.
 */


import java.util.Random;

public class GameLogic {
    private static final int MIN_CARD_VALUE = 2;
    // Card values include: Jack = 11, Queen = 12, King = 13, Ace = 14, Joker = 15
    private static final int MAX_CARD_VALUE = 15;
    private static Random random = new Random();
    private static int blind;
    private static int playerCard;
    private static int houseCard;
    private static int bribeCounter = 0;

    // Blinds are calculated at 10% of the current amount of chips the Player has.
    public static int calculateBlind() {
        return (int) Math.round(Player.getChipCount() * 0.10);
    }

    // Bribes are calculated at 20% of the current amount of chips the Player has.
    public static int calculateBribe() {
        return (int) Math.round(Player.getChipCount() * 0.20);
    }

    // Method to draw a "card", written in a way to prevent overflow.
    public static int drawCard() {
        return random.nextInt(MAX_CARD_VALUE - MIN_CARD_VALUE + 1) + MIN_CARD_VALUE;
    }

    // Method to return a string depending on the result of the calcuation.
    public static String compareCards(int playerCard, int houseCard) {
        if (playerCard > houseCard) {
            return "The Player has won the hand.";
        } else if (playerCard < houseCard) {
            return "The House has won the hand."; 
        } else {
            return "It's a draw.";
        }
    }

    // Method to convert card value into actual name or string of card.
    public static String cardValueToString(int cardValue) {
        switch (cardValue) {
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            case 14: return "Ace";
            case 15: return "Joker";
            default: return Integer.toString(cardValue);
        }
    }

    // Provides the overall logic of the game, and calls certain methods dependent on action.
    public static String startGame() {
        playerCard = drawCard();
        houseCard = drawCard();

        blind = calculateBlind();
        Player.addChips(-blind);

        String question = "<html><div style='text-align: center;'>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
        return question;
    }

    // Provides the logic when the Player decides to play their hand, by clicking on the Play button.
    public static String finishGame(int bet) {
        String result = compareCards(playerCard, houseCard);

        if (result.equals("The Player has won the hand.")) {
            Player.addChips(bet);
        } else if (result.equals("The House has won the hand.")) {
            Player.addChips(-bet);
        }

        if (Player.getChipCount() > Player.currentHighScore) {
            Player.currentHighScore = Player.getChipCount();
            ReadConfigFile.writeNewHighScore(Player.currentHighScore);
        }

        String displayResult = "<html><div style='text-align: center;'>House's Card: " + cardValueToString(houseCard) + "<br>" + result + "<br>Player's current chip count: " + Player.getChipCount() + "<br>Player's current highscore: " + Player.currentHighScore + "</div></html>";
        return displayResult;
    }


    // Provides the logic when the Player decides to mitigate losses by simply folding their hand, if they click the Fold button.
    public static String foldGame() {
        int mitigatedLosses = (int) Math.round(0.5 * blind);
        Player.addChips(mitigatedLosses);

        String displayFoldedResult = "<html><div style='text-align: center;'>You saved " + mitigatedLosses + " by folding." + "<br>Player's current chip count: " + Player.getChipCount() + "<br>Player's current highscore: " + Player.currentHighScore + "</div></html>";
        return displayFoldedResult;
    }

    // Provides the logic when the Player decides to try to safely gather information, by clicking on the Survey button.
    public static String surveyAction() {
        // Currently, there is a 30% chance for success and a 70% chance for failure when using the Survey action.
        int chanceForSuccess = 30;
        String surveyResult = "";

        int randomValueRoll = random.nextInt(100) + 1;

        if (randomValueRoll <= chanceForSuccess) {
            if (playerCard < houseCard) {
                surveyResult = "<html><div style='text-align: center;'>After talking with the Dealer, you suspect your hand is weaker than the Dealer's by how confident he looks." + "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return surveyResult;
            } else if (playerCard == houseCard) {
                surveyResult = "<html><div style='text-align: center;'>After talking with the Dealer, you suspect your hand is roughly the same as the Dealer's." + "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return surveyResult;
            } else {
                surveyResult = "<html><div style='text-align: center;'>While talking with the Dealer, you are confident your hand is stronger than the Dealer's by his reserved demeanor." + "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return surveyResult;
            }

        } else {
            surveyResult = "<html><div style='text-align: center;'>Despite your attempts to glean information on the Dealer's Hand, you are no more confident then when you started." + "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
            return surveyResult;
        }
    }

    // Provides the logic when the Player decides to bribe the Dealer to learn about his hand.
    // This allows the Player to learn about the Dealer's hand guaranteed, but at the cost of their chips.
    public static String bribeAction() {
        // The Player can only bribe the Dealer 3 total times in a entire game.
        int bribeAmount = calculateBribe();
        bribeCounter++;
        String bribeResult = "";

        if (bribeCounter <= 3) {
            if (playerCard < houseCard) {
                Player.addChips(-bribeAmount);
                bribeResult = "<html><div style='text-align: center;'>After quietly handing the dealer " + bribeAmount + " chips, you learn that your current hand is weaker than the Dealer's."+ "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return bribeResult;
            } else if (playerCard == houseCard) {
                Player.addChips(-bribeAmount);
                bribeResult = "<html><div style='text-align: center;'>After quietly handing the dealer " + bribeAmount + " chips, you learn that your current hand is the same as the Dealer's."+ "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return bribeResult;
            } else {
                Player.addChips(-bribeAmount);
                bribeResult = "<html><div style='text-align: center;'>After quietly handing the dealer " + bribeAmount + " chips, you learn that your current hand is stronger than the Dealer's."+ "<br><br>Your card: " + cardValueToString(playerCard) + "<br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
                return bribeResult;
            }
        } else {
            bribeResult = "<html><div style='text-align: center;'>The Dealer refuses to accept anymore bribes from you." + "<br>Your card: " + cardValueToString(playerCard) + "<br><br>Your current chip count is: " + Player.getChipCount() + "<br>Your current blind is: " + blind + "<br>Do you want to bet?</div></html>";
            return bribeResult;
        }
    }
}
