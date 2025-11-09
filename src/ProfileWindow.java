import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

public class ProfileWindow extends JFrame {
    private static final Color HEADER_BG = new Color(18, 81, 87); // darker teal for header
    private static final Color PAGE_BG = new Color(230, 230, 230); // light gray page
    private static final Color CARD_BG = new Color(190, 190, 190); // profile card
    private static final Color ACCENT = new Color(106, 92, 255); // violet-blue
    private static final Color ACCENT_DIM = new Color(130, 120, 255);
    private static final Color OPEN_GREEN = new Color(10, 120, 40);

    public ProfileWindow() {
        super("Gate O - Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);
        setUndecorated(false);

        JPanel content = new JPanel(null);
        content.setBackground(PAGE_BG);

        // Header
        JPanel header = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(HEADER_BG);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Draw small logo (circle + bar)
                g2.setColor(ACCENT);
                g2.setStroke(new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                int lx = 18, ly = 12;
                g2.drawOval(lx, ly, 44, 44);
                g2.drawLine(lx + 28, ly + 28, lx + 60, ly + 28);
                // Title text
                g2.setFont(new Font("SansSerif", Font.BOLD, 28));
                g2.drawString("GATE O", lx + 76, ly + 34);
                g2.dispose();
            }
        };
        header.setBounds(0, 0, 980, 70);

        // Profile icon at header right
        JPanel userIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(220,220,220));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(HEADER_BG);
                g2.fillOval(getWidth()/6, getHeight()/8, getWidth()*2/3, getHeight()*2/3);
                g2.dispose();
            }
        };
        userIcon.setOpaque(false);
        userIcon.setBounds(900, 12, 44, 44);
        header.add(userIcon);

        content.add(header);

        // Back button (rounded) at top-left inside content area
        JButton back = new JButton("BACK");
        back.setFocusable(false);
        back.setFont(new Font("SansSerif", Font.BOLD, 14));
        back.setBackground(Color.WHITE);
        back.setForeground(new Color(70, 30, 30));
        back.setBounds(18, 86, 90, 36);
        back.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true));
        content.add(back);

        // Left profile card (rounded)
        JPanel leftCard = new RoundedPanel(18, CARD_BG);
        leftCard.setLayout(null);
        leftCard.setBounds(18, 132, 460, 420);

        // avatar circle
        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(28, 38, 42));
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(230,230,230));
                int w = getWidth(), h = getHeight();
                g2.fillOval(w/4, h/8, w/2, h/2);
                g2.dispose();
            }
        };
        avatar.setOpaque(false);
        avatar.setBounds(170, 28, 120, 120);
        leftCard.add(avatar);

        // Hi Username label
        JLabel hi = new JLabel("HI USERNAME!", SwingConstants.CENTER);
        hi.setFont(new Font("SansSerif", Font.BOLD, 32));
        hi.setForeground(new Color(70, 30, 30));
        hi.setBounds(0, 170, leftCard.getWidth(), 40);
        leftCard.add(hi);

        // Change username button
        JButton btnChangeUser = makeAccentButton("Change username");
        btnChangeUser.setBounds(120, 230, 220, 48);
        leftCard.add(btnChangeUser);

        // Change password button
        JButton btnChangePass = makeAccentButton("Change password");
        btnChangePass.setBounds(120, 300, 220, 48);
        leftCard.add(btnChangePass);

        content.add(leftCard);

        // Right panel
        JPanel rightOuter = new RoundedPanel(18, new Color(245,245,245));
        rightOuter.setLayout(null);
        rightOuter.setBounds(500, 132, 460, 420);

        // Files Created header pill
        JPanel filesHeader = new RoundedPanel(14, HEADER_BG);
        filesHeader.setBounds(20, 10, 420, 56);
        filesHeader.setLayout(null);
        JLabel fhLabel = new JLabel("FILES CREATED", SwingConstants.CENTER);
        fhLabel.setForeground(Color.WHITE);
        fhLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        fhLabel.setBounds(0, 0, filesHeader.getWidth(), filesHeader.getHeight());
        filesHeader.add(fhLabel);
        rightOuter.add(filesHeader);

        // Inner list box
        JPanel listBox = new RoundedPanel(10, new Color(240,240,240));
        listBox.setBounds(20, 80, 420, 320);
        listBox.setLayout(null);

        // Add three rows
        String[] dates = {"19-08-24", "19-09-24", "19-10-24"};
        for (int i = 0; i < dates.length; i++) {
            JPanel row = new JPanel(null);
            row.setOpaque(false);
            row.setBounds(12, 12 + i*94, 396, 72);

            JLabel dateLbl = new JLabel(dates[i], SwingConstants.CENTER);
            dateLbl.setBounds(0, 6, 300, 48);
            dateLbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            dateLbl.setForeground(new Color(70, 30, 30));
            dateLbl.setBorder(BorderFactory.createLineBorder(new Color(120,120,120), 2, true));
            dateLbl.setOpaque(false);
            row.add(dateLbl);

            JButton open = new JButton("OPEN");
            open.setForeground(Color.WHITE);
            open.setBackground(OPEN_GREEN);
            open.setBounds(312, 12, 72, 48);
            open.setFont(new Font("SansSerif", Font.BOLD, 14));
            open.setFocusable(false);
            row.add(open);

            listBox.add(row);
        }

        rightOuter.add(listBox);
        content.add(rightOuter);

        add(content);
    }

    private JButton makeAccentButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
        b.setBackground(ACCENT);
        b.setFocusable(false);
        b.setBorder(BorderFactory.createLineBorder(new Color(50,40,120), 2, true));
        return b;
    }

    // Small rounded panel helper
    static class RoundedPanel extends JPanel {
        private int radius;
        private Color bg;
        RoundedPanel(int r, Color bg) {
            super(null);
            this.radius = r;
            this.bg = bg;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius*2, radius*2);
            g2.setColor(new Color(100,100,100));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius*2, radius*2);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileWindow w = new ProfileWindow();
            w.setVisible(true);
        });
    }
}
