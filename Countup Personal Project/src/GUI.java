/* Author: James Bui
 * Date: 7/2/2024
 * Description: GUI.java acts as the main interface the user will interact with. Here, the file's contents revolves around using the Java Swing and Java Awt Libraries 
 * such as the window that the user interacts with, the buttons the user will click, how the user will interact with the overall program, etc.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    // These labels are specfically defined as class variables to make the logic of updating the pages through user input easier.
    private JLabel dynamicTextLabel;
    private JTextField chipsTextField;
    private JButton playButton;
    private JButton foldButton;
    private JButton drawHandButton;
    private JButton surveyButton;
    private JButton bribeButton;
    private GameController gameController;
    private boolean canDrawFlag = true;
    private boolean canPlayFlag = false;
    private boolean canTalkFlag = false;

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Countup!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Resolution or window size set to 800 x 600.
        frame.setSize(800, 600);
        
        // Create a panel with CardLayout.
        JPanel cardPanel = new JPanel(new CardLayout());

        // Add the main menu panel and the game panel to the card panel.
        cardPanel.add(createMainMenuPanel(frame, cardPanel), "Main Menu");
        cardPanel.add(createGamePanel(frame, cardPanel), "Game Panel");

        // Add the card panel to the frame.
        frame.add(cardPanel);

        // The current frame visibility.
        frame.setVisible(true);
    }

    
    private JPanel createMainMenuPanel(JFrame frame, JPanel cardPanel) {
        // Creates a panel with a vertical BoxLayout.
        JPanel mainMenuPanel = new JPanel();
        // Window background color set to black.
        mainMenuPanel.setBackground(Color.BLACK);
        mainMenuPanel.setLayout(new BoxLayout(mainMenuPanel, BoxLayout.Y_AXIS));

        // Load logo for Countup! User must replace file path with their own!
        ImageIcon logo = new ImageIcon("C:/Users/Countup/resources/logo.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates welcome label and defines appereance.
        JLabel welcomeLabel = new JLabel("Welcome to Countup!", JLabel.CENTER);
        // Font and text color set to white.
        Font font1 = new Font("Helvetica Neue", Font.BOLD, 24);
        welcomeLabel.setFont(font1);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates direction label and defines appereance.
        JLabel directionLabel = new JLabel("Click on the associated butttons to begin.", JLabel.CENTER);
        // Font and text color set to white.
        Font font2 = new Font("Arial", Font.BOLD, 16);
        directionLabel.setFont(font2);
        directionLabel.setForeground(Color.WHITE);
        directionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adds a button for the user to start the game.
        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adds a button for the user to click in order to exit to desktop.
        JButton exitButton = new JButton("Exit to Desktop");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add labels to panel, formatting the page.
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainMenuPanel.add(welcomeLabel);
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainMenuPanel.add(directionLabel);
        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(logoLabel);
        mainMenuPanel.add(Box.createVerticalGlue());
        mainMenuPanel.add(startButton);
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainMenuPanel.add(exitButton);
        mainMenuPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Adds an action listener to the start button.
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, "Game Panel");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Closes the window the button is clicked.
                frame.dispose();
            }
        });

        return mainMenuPanel;
    }

    private JPanel createGamePanel(JFrame frame, JPanel cardPanel) {
        // Creates the second panel or main "screen" for CountUp! using a background image. User must replace file path with their own!
        BackgroundImagePanel gamePanel = new BackgroundImagePanel("C:/Users/resources/poker_table.jpg");
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));

        // Creates welcome label and defines appereance. This is where text is changed through certain actions.
        dynamicTextLabel = new JLabel("Dealer: Welcome to table Player, please take a seat and feel free to begin.", JLabel.CENTER);
        // Font and text color set to white.
        Font font1 = new Font("Calibri", Font.BOLD, 20);
        dynamicTextLabel.setFont(font1);
        dynamicTextLabel.setForeground(Color.WHITE);
        dynamicTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Creates a panel for the user's input, which allows for the text field and buttons to wrap on the same line.
        JPanel playerInputPanel = new JPanel();
        // The current background color is translucent for this panel.
        playerInputPanel.setBackground(new Color(0, 0, 0, 0));
        playerInputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Adds a text field for the user to bet chips in the game.
        chipsTextField = new JTextField(15);
	    chipsTextField.setMaximumSize(new Dimension(200, 30));

        // Adds a Play button and Fold button.
        playButton = new JButton("Play");
        playButton.setEnabled(canPlayFlag);
        foldButton = new JButton("Fold");
        foldButton.setEnabled(canPlayFlag);

        // Adds a Survey button, which acts as a less reliable way to gather information from the Dealer but is completely free.
        // Adds a Bribe button, which acts as a guaranteed way to gather information from the Dealer but at the cost of chips.
        surveyButton = new JButton("Survey");
        surveyButton.setEnabled(canTalkFlag);
        bribeButton = new JButton("Bribe");
        bribeButton.setEnabled(canTalkFlag);

        // Add labels to the input panel, formatting the page.
        playerInputPanel.add(chipsTextField);
        playerInputPanel.add(playButton);
        playerInputPanel.add(foldButton);
        playerInputPanel.add(surveyButton);
        playerInputPanel.add(bribeButton);

        // Adds a button for the user to click to begin the game, by drawing a hand.
        drawHandButton = new JButton("Draw Hand");
        drawHandButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adds a button for the user to click to start the tutorial given by text.
        JButton tutorialButton = new JButton("Start Tutorial");
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Adds a button for the user to click in order to exit to desktop.
        JButton exitButton = new JButton("Exit to Desktop");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add labels to the main game panel, formatting the page.
        gamePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        gamePanel.add(dynamicTextLabel);
        gamePanel.add(Box.createVerticalGlue());
        gamePanel.add(playerInputPanel);
        gamePanel.add(drawHandButton);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 50)));
        gamePanel.add(tutorialButton);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        gamePanel.add(exitButton);
        gamePanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Setup instance of GameController which will help maintain the connection between the overall game logic and the GUI logic itself.
        gameController = new GameController(dynamicTextLabel, chipsTextField);

        // Next section of code provides the logic of how each button interacts with the user and what happens when the user clicks on said buttons.
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calls for tutorial messages to be displayed on the game menu page.
                gameController.startTutorials();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Closes the window the button is clicked.
                frame.dispose();
            }
        });

        drawHandButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deals the player a card when they click on the "Draw Hand" button.
                gameController.drawHand();
                canDrawFlag = false;
                canPlayFlag = true;
                canTalkFlag = true;
                drawHandButton.setEnabled(canDrawFlag);
                playButton.setEnabled(canPlayFlag);
                foldButton.setEnabled(canPlayFlag);
                surveyButton.setEnabled(canTalkFlag);
                bribeButton.setEnabled(canTalkFlag);
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calls method to check if player bet is legal.
                int playerBet = gameController.getPlayerInteger();
                // Plays the current hand drawn by the player with the current legal bet.
                if (playerBet != -1) {
                    if (canPlayFlag) {
                        gameController.playHand(playerBet);
                        canDrawFlag = true;
                        canPlayFlag = false;
                        canTalkFlag = false;
                        drawHandButton.setEnabled(canDrawFlag);
                        playButton.setEnabled(canPlayFlag);
                        foldButton.setEnabled(canPlayFlag);
                        surveyButton.setEnabled(canTalkFlag);
                        bribeButton.setEnabled(canTalkFlag);
                    } else {
                        dynamicTextLabel.setText("Dealer: You must draw a new hand before playing again.");
                    }
                }
            }
        });

        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Folds the current hand drawn by the player.
                if (canPlayFlag) {
                    gameController.foldHand();
                    canDrawFlag = true;
                    canPlayFlag = false;
                    canTalkFlag = false;
                    drawHandButton.setEnabled(canDrawFlag);
                    playButton.setEnabled(canPlayFlag);
                    foldButton.setEnabled(canPlayFlag);
                    surveyButton.setEnabled(canTalkFlag);
                    bribeButton.setEnabled(canTalkFlag);
                } else {
                    dynamicTextLabel.setText("Dealer: You must draw a new hand before playing again.");
                }
            }
        });

        surveyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tries to talk the Dealer for information.
                if (canTalkFlag) {
                    gameController.surveyHand();
                    canTalkFlag = false;
                    surveyButton.setEnabled(canTalkFlag);
                    bribeButton.setEnabled(canTalkFlag);
                } else {
                    dynamicTextLabel.setText("Trying this strategy again maybe dangerous, you should either play or fold your hand.");
                }
            }
        });

        bribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get information about the Dealer's hand, but uses a bribe in the process.
                if (canTalkFlag) {
                    gameController.bribeHand();
                    canTalkFlag = false;
                    surveyButton.setEnabled(canTalkFlag);
                    bribeButton.setEnabled(canTalkFlag);
                } else {
                    dynamicTextLabel.setText("Trying this strategy again maybe dangerous, you should either play or fold your hand.");
                }
            }
        });

        return gamePanel;
    }
}
