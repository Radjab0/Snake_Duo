import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("Snake duo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*frame.setUndecorated(false);*/
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}

public class  SimpleGame extends JPanel implements ActionListener, KeyListener {
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 15;
}

public SimpleGame() {
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(100, this);  // Тут создаем таймер
    timer.start();  // В этой строчке его запускаем
}

public void paintComponent(Graphics g){
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 400, 600);
    g.setColor(Color.GREEN);
    g.fillRect(playerX, playerY, 50, 50);
}