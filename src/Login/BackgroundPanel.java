package Login;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    public BackgroundPanel() {

        // Find our background image
        URL imageURL = getClass().getResource("/blue-marble.png");

        if (imageURL != null) {
            backgroundImage = new ImageIcon(imageURL).getImage();
        } else {
            System.out.println("Background image not found!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {

            // Draw the image to fill the panel
            g.drawImage(
                    backgroundImage,
                    0, 0,
                    getWidth(), getHeight(),
                    this
            );
        }
    }
}
