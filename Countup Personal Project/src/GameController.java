/* Author: James Bui
 * Date: 7/24/2024
 * Description: GameController.java acts as the specific interface between the main game logic or anything associated with the actual CountUp! calcuations and the main GUI
 * for CountUp!. This includes specific buttons on GUI.java that prompts actions from the game itself or the field where the Player can type the chip amount.
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
    private JLabel dynamicTextLabel;
    private JTextField chipsTextField;

    public GameController(JLabel dynamicTextLabel, JTextField chipsTextField) {
        this.dynamicTextLabel = dynamicTextLabel;
        this.chipsTextField = chipsTextField;
    }

    public void updateDynamicText(String text) {
        dynamicTextLabel.setText(text);
    }

    // Method used to create a timer to display tutorial messages.
    private void setupTimer(int delay, String message, ActionListener nextTimer) {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDynamicText(message);
                if (nextTimer != null) {
                    nextTimer.actionPerformed(e);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    // Contains actual tutorial messages and timers associated with the messages.
    public void startTutorials() {
        setupTimer(0, "Dealer: To play, simply bet your chips if you think you have the higher scoring card.", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setupTimer(6000, "Dealer: Otherwise, fold to incur a lesser loss, but you'll be given a new hand.", new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setupTimer(6000, "<html>Dealer: However, there are also two other things you should consider doing<br>EVERY hand, surveying or bribing.</html>", new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                setupTimer(6000, "<html>Dealer: Surveying is a risk-free way to gather information<br>about your hand compared to mine.</html>", new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        setupTimer(6000, "<html>Dealer: Unfortunately, you may find that surveying is not<br>as effective as you would think.</html>", new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                setupTimer(6000, "Dealer: Bribing, on the other hand, will always give you accurate information.", new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                        setupTimer(6000, "<html>Dealer: This comes at a cost of 20% of your current chip total,<br>and you can only do this three total times.</html>", new ActionListener() {
                                                            @Override
                                                            public void actionPerformed(ActionEvent e) {
                                                                setupTimer(6000, "Dealer: You start off with 1000 chips to your name. To start, click Draw Hand.", null);
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                             }
                        });
                    }
                });
            }
        });
    }

    // Method to grab the player's bet from the JTextField.
    public int getPlayerInteger() {
            try {
                String playerInput = chipsTextField.getText();
                int inputtedChipAmount = Integer.parseInt(playerInput);

                if (inputtedChipAmount > Player.getChipCount()) {
                    dynamicTextLabel.setText("Error: You have tried to enter an amount that is either greater than your current chip count.");
                    return -1;
                } else {
                    return inputtedChipAmount;
                }
            } catch (NumberFormatException e) {
                dynamicTextLabel.setText("Error: Invalid input, please enter a valid number.");
                return -1;
            }
    }

    // Method starts the game and displays the hand. 
    public void drawHand() {
        dynamicTextLabel.setText(GameLogic.startGame());
    }

    // Method plays the hand with the inputted chip amount.
    public void playHand(int playerBet) {
        dynamicTextLabel.setText(GameLogic.finishGame(playerBet));
    }

    // Method folds the hand and recoups some chips.
    public void foldHand() {
        dynamicTextLabel.setText(GameLogic.foldGame());
    }

    // Method tries to learn information about the Dealer's hand.
    public void surveyHand() {
        dynamicTextLabel.setText(GameLogic.surveyAction());
    }

    // Method tries to bribe the Dealer, to learn information about the Dealer's hand.
    public void bribeHand() {
        dynamicTextLabel.setText(GameLogic.bribeAction());
    }
}
