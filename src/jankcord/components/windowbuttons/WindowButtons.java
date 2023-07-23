package jankcord.components.windowbuttons;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jankcord.Jankcord;
import jankcord.components.windowbuttons.windowbuttonlistener.WindowButtonListener;

public class WindowButtons extends JPanel {
    public WindowButtons() {
        // Init
        setName("WindowsButton");

        setLayout(null);
        setSize(186, 50);
        setBackground(new Color(0, 0, 0, 0));
        setLocation(Jankcord.getViewPanel().getWidth() - 186, 0);

        // Buttons
        Font windowButtonFont = new Font(null, Font.PLAIN, 28);
        JankCloseButton closeButton = new JankCloseButton(getWidth(), null);
        JLabel fullscreenButton = new JLabel("□", SwingConstants.CENTER);
        JLabel minimizeButton = new JLabel("—", SwingConstants.CENTER);

        // Buttons Init
        add(closeButton);


        fullscreenButton.setOpaque(true);
        fullscreenButton.setSize(62, 50);
        fullscreenButton.setFont(windowButtonFont);
        fullscreenButton.setLocation(closeButton.getX() - fullscreenButton.getWidth(), 0);
        fullscreenButton.addMouseListener(new WindowButtonListener(
                WindowButtonListener.FRAME_FULLSCREEN,
                new Color(40, 43, 46),
                new Color(190, 191, 193),
                new Color(43, 46, 50),
                new Color(220, 220, 221),
                new Color(32, 34, 37),
                new Color(160, 162, 164)));
        fullscreenButton.setBackground(new Color(32, 34, 37));
        fullscreenButton.setForeground(new Color(159, 161, 165));

        add(fullscreenButton);


        minimizeButton.setOpaque(true);
        minimizeButton.setSize(62, 50);
        minimizeButton.setFont(windowButtonFont);
        minimizeButton.setLocation(fullscreenButton.getX() - minimizeButton.getWidth(), 0);
        minimizeButton.addMouseListener(new WindowButtonListener(
                WindowButtonListener.FRAME_MINIMIZE,
                new Color(40, 43, 46),
                new Color(190, 191, 193),
                new Color(43, 46, 50),
                new Color(220, 220, 221),
                new Color(32, 34, 37),
                new Color(160, 162, 164)));
        minimizeButton.setBackground(new Color(32, 34, 37));
        minimizeButton.setForeground(new Color(159, 161, 165));

        add(minimizeButton);
    }
}
