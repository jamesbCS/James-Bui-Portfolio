/* Author: James Bui
 * Date: 7/27/2024
 * Description: Main.java is a simple java program where its only functionality is running the driver-code of CountUp!. To start anything associated with Countup!, the user
 * must run Main.java.
 */

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.createAndShowGUI();
            }
        });
    }
}
