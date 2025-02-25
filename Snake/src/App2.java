import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class App extends JPanel implements KeyListener, ActionListener {

    private int playerX = 450;
    private int playerY = 450;
    private Timer timer;

    public App() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Color.GREEN);
        g.fillRect(playerX, playerY, 100, 100);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (playerY > 0) playerY -= 10;
                break;
            case KeyEvent.VK_DOWN:
                if (playerY < 900) playerY += 10;
                break;
            case KeyEvent.VK_LEFT:
                if (playerX > 0) playerX -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                if (playerX < 900) playerX += 10;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Snake duo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        App gamePanel = new App();
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}

