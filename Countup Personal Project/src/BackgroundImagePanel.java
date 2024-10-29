/* Author: James Bui
 * Date: 8/21/2024
 * Description: BackgroundImagePanel.java is used to create an object which acts as a JPanel but also allows for that JPanel's background to be an image file.
 */

import javax.swing.*;
import java.awt.*;

public class BackgroundImagePanel extends JPanel {
    private Image backgroundImage;

    // Constructor that loads the background image.
    public BackgroundImagePanel(String filePath) {
        try {
            backgroundImage = new ImageIcon(filePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
