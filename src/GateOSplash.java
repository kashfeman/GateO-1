import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GateOSplash extends JWindow {
    private static final Color BG = new Color(8, 38, 42); // dark teal
    private static final Color ACCENT = new Color(106, 92, 255); // violet-blue
    private static final Color ACCENT_DIM = new Color(130, 120, 255);

    private final String title = "GATE O";
    // NOTE: copying the text exactly from the provided image
    private final String subtitle = "BULDING THE CIRCUIT LOGIC FLOW";

    private int revealTitle = 0;
    private int revealSub = 0;
    private float alpha = 1f;

    public GateOSplash() {
        setSize(980, 420);
        setLocationRelativeTo(null);
        setBackground(new Color(0,0,0,0));

        // Typing timer (title then subtitle)
        Timer typing = new Timer(110, null);
        typing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (revealTitle < title.length()) {
                    revealTitle++;
                } else if (revealSub < subtitle.length()) {
                    revealSub++;
                } else {
                    // finished typing both
                    ((Timer)e.getSource()).stop();
                    // keep visible for 10 seconds then start fade
                    Timer visible = new Timer(10000, ev -> startFade());
                    visible.setRepeats(false);
                    visible.start();
                }
                repaint();
            }
        });
        typing.setInitialDelay(100);
        typing.start();
    }

    private void startFade() {
        Timer fade = new Timer(50, null);
        fade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha -= 0.04f;
                if (alpha <= 0f) {
                    alpha = 0f;
                    ((Timer)e.getSource()).stop();
                    SwingUtilities.invokeLater(() -> dispose());
                }
                repaint();
            }
        });
        fade.start();
    }

    @Override
    public void paint(Graphics g) {
        // Full-window double-buffered paint
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            // Apply global alpha for fade
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background
            g2.setColor(BG);
            g2.fillRect(0, 0, getWidth(), getHeight());

            int w = getWidth();
            int h = getHeight();

            // Draw logo on left
            int logoCenterX = 220;
            int logoCenterY = h/2 - 10;
            int outerR = 120;

            // Outer thick ring
            Stroke prev = g2.getStroke();
            g2.setStroke(new BasicStroke(20f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(ACCENT);
            Ellipse2D outer = new Ellipse2D.Double(logoCenterX - outerR, logoCenterY - outerR, outerR*2, outerR*2);
            g2.draw(outer);

            // Draw the 'gap' to form a stylized G: draw a background-filled wedge to mask part of ring
            g2.setColor(BG);
            Arc2D gap = new Arc2D.Double(logoCenterX - outerR - 10, logoCenterY - outerR - 10, (outerR+10)*2, (outerR+10)*2, -40, 90, Arc2D.PIE);
            g2.fill(gap);

            // Inner horizontal bar of G
            g2.setColor(ACCENT);
            g2.setStroke(new BasicStroke(18f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Line2D bar = new Line2D.Double(logoCenterX - 10, logoCenterY + 20, logoCenterX + 60, logoCenterY + 20);
            g2.draw(bar);

            // Small circle near top-right
            g2.fill(new Ellipse2D.Double(logoCenterX + outerR - 36, logoCenterY - outerR + 22, 28, 28));

            g2.setStroke(prev);

            // Draw title text on the right side
            g2.setColor(ACCENT);
            Font titleFont = new Font("SansSerif", Font.BOLD, 72);
            g2.setFont(titleFont);

            String shownTitle = title.substring(0, Math.min(revealTitle, title.length()));
            int titleX = logoCenterX + outerR + 30;
            int titleY = logoCenterY - 10;
            g2.drawString(shownTitle, titleX, titleY);

            // Subtitle
            Font subFont = new Font("SansSerif", Font.ITALIC, 20);
            g2.setFont(subFont);
            g2.setColor(ACCENT_DIM);
            String shownSub = subtitle.substring(0, Math.min(revealSub, subtitle.length()));
            int subX = titleX + 6;
            int subY = titleY + 36;
            g2.drawString(shownSub, subX, subY);

        } finally {
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GateOSplash s = new GateOSplash();
            s.setVisible(true);
        });
    }
}
