import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    private final int WIDTH = 400;  // Ширина игрового поля
    private final int HEIGHT = 400; // Высота игрового поля
    private final int TILE_SIZE = 20; // Размер одного сегмента змейки и еды
    private final int DELAY = 100; // Задержка таймера (скорость игры)

    private ArrayList<Point> snakeSegments; // Сегменты змейки
    private Point food; // Координаты еды
    private int direction; // Направление движения змейки
    private boolean gameOver; // Флаг окончания игры
    private Timer timer; // Таймер для обновления экрана
    private int score; // Счет игрока

    public SnakeGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        // Инициализация игры
        initGame();
    }

    private void initGame() {
        snakeSegments = new ArrayList<>();
        snakeSegments.add(new Point(100, 100)); // Голова змейки
        snakeSegments.add(new Point(80, 100));  // Первый сегмент
        snakeSegments.add(new Point(60, 100));  // Второй сегмент

        direction = KeyEvent.VK_RIGHT; // Начальное направление
        gameOver = false;
        score = 0;

        spawnFood(); // Создаем первую еду
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void spawnFood() {
        Random rand = new Random();
        int x = rand.nextInt(WIDTH / TILE_SIZE) * TILE_SIZE;
        int y = rand.nextInt(HEIGHT / TILE_SIZE) * TILE_SIZE;
        food = new Point(x, y);

        // Проверяем, чтобы еда не появилась на змейке
        for (Point segment : snakeSegments) {
            if (food.equals(segment)) {
                spawnFood(); // Если еда на змейке, генерируем заново
                break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Рисуем змейку
        g.setColor(Color.GREEN);
        for (Point segment : snakeSegments) {
            g.fillRect(segment.x, segment.y, TILE_SIZE, TILE_SIZE);
        }

        // Рисуем еду
        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, TILE_SIZE, TILE_SIZE);

        // Рисуем счет
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Счет: " + score, 10, 20);

        // Рисуем сообщение о конце игры
        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Конец игры!", WIDTH / 2 - 90, HEIGHT / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            checkCollision();
            checkFood();
            repaint();
        }
    }

    private void moveSnake() {
        Point head = snakeSegments.get(0);
        Point newHead = new Point(head);

        // Определяем новое положение головы
        switch (direction) {
            case KeyEvent.VK_UP:
                newHead.y -= TILE_SIZE;
                break;
            case KeyEvent.VK_DOWN:
                newHead.y += TILE_SIZE;
                break;
            case KeyEvent.VK_LEFT:
                newHead.x -= TILE_SIZE;
                break;
            case KeyEvent.VK_RIGHT:
                newHead.x += TILE_SIZE;
                break;
        }

        // Добавляем новую голову и удаляем хвост
        snakeSegments.add(0, newHead);
        snakeSegments.remove(snakeSegments.size() - 1);
    }

    private void checkCollision() {
        Point head = snakeSegments.get(0);

        // Проверка столкновения с границами
        if (head.x < 0 || head.x >= WIDTH || head.y < 0 || head.y >= HEIGHT) {
            gameOver = true;
        }

        // Проверка столкновения с собой
        for (int i = 1; i < snakeSegments.size(); i++) {
            if (head.equals(snakeSegments.get(i))) {
                gameOver = true;
                break;
            }
        }

        if (gameOver) {
            timer.stop(); // Останавливаем игру
        }
    }

    private void checkFood() {
        Point head = snakeSegments.get(0);

        // Если змейка съела еду
        if (head.equals(food)) {
            snakeSegments.add(new Point(-1, -1)); // Добавляем новый сегмент
            score++; // Увеличиваем счет
            spawnFood(); // Генерируем новую еду
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        // Изменяем направление змейки
        if ((key == KeyEvent.VK_LEFT) && (direction != KeyEvent.VK_RIGHT)) {
            direction = KeyEvent.VK_LEFT;
        }
        if ((key == KeyEvent.VK_RIGHT) && (direction != KeyEvent.VK_LEFT)) {
            direction = KeyEvent.VK_RIGHT;
        }
        if ((key == KeyEvent.VK_UP) && (direction != KeyEvent.VK_DOWN)) {
            direction = KeyEvent.VK_UP;
        }
        if ((key == KeyEvent.VK_DOWN) && (direction != KeyEvent.VK_UP)) {
            direction = KeyEvent.VK_DOWN;
        }

        // Перезапуск игры при нажатии пробела
        if (gameOver && key == KeyEvent.VK_SPACE) {
            initGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Змейка");
        SnakeGame game = new SnakeGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Центрируем окно
        frame.setVisible(true);
    }
}