package jankcord.profiles;

import jankcord.Jankcord;
import jankcord.newclasses.ResourceLoader;
import jankcord.objects.User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChannelProfile extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private User friend;

    public ChannelProfile(User friend) {
        Image avatar = ResourceLoader.loader.getTempProfileIcon().getImage();

        try {
            URL url = new URL(friend.getAvatarURL());

            BufferedImage image = ImageIO.read(url);

            avatar = new ImageIcon(image).getImage();
        } catch (Exception e) {
            System.out.println("Error getting avatar");
        }

        // Init
        setLayout(null);
        setBackground(null);
        setPreferredSize(new Dimension(448, 92));

        // Icon
        Image scaledIcon = avatar.getScaledInstance(72, 72, Image.SCALE_DEFAULT);
        JLabel channelIcon = new JLabel();
        channelIcon.setSize(72, 72);
        channelIcon.setLocation(12, 8);
        channelIcon.setIcon(new ImageIcon(scaledIcon));

        add(channelIcon);

        // Username
        JLabel usernameLabel = new JLabel(friend.getUsername());
        usernameLabel.setSize(328, 40);
        usernameLabel.setLocation(100, 23);
        usernameLabel.setForeground(new Color(142, 146, 151));
        usernameLabel.setFont(new Font("Whitney", Font.PLAIN, 28));

        add(usernameLabel);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Jankcord.setOtherID(friend.getId());
                System.out.println("Viewing: " + friend.getUsername() + " [" + friend.getId() + "]");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(new Color(59, 60, 66));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(54, 55, 61));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(null);
            }
        });

        this.friend = friend;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
