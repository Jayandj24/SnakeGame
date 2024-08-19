import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

enum Direction {
    up, down, left, right
}

public class Game extends JPanel implements KeyListener, ActionListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final int TILE_SIZE = 10;
    Random random;
    Frame frame;

    JPanel gameOverPanel;
    JLabel gameOverLabel;
    JLabel scoreLabel;
    JButton playAgainButton;

    ArrayList<Position> snake;
    Direction direction;
    Position apple;
    Timer timer;
    int delay = 100;
    int score = 0;
    boolean gameHasStarted;

    public Game(Frame frame) {
        this.frame = frame;
        frame.setTitle("Snake - Press any key to start...");
        this.setBackground(Color.black);
        random = new Random();
        apple = new Position(50, 50);
        snake = new ArrayList<Position>();
        snake.add(new Position(20, 20));
        direction = Direction.right;
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(delay, this);
        gameHasStarted = false;
    }

    @Override
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);

        // draw snake
        for(int i=0; i<snake.size(); i++) {
            if(i==0) {
                g.setColor(Color.green);
                g.fillRect(snake.get(i).x, snake.get(i).y, TILE_SIZE, TILE_SIZE);
            }
            
            // draw body
            else {
                g.setColor(Color.green);
                g.fillRect(snake.get(i).x, snake.get(i).y, TILE_SIZE, TILE_SIZE);
            }
        }
        
        // draw apple
        g.setColor(Color.red);
        g.fillRect(apple.x, apple.y, TILE_SIZE, TILE_SIZE);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // check apple collision
        if(snake.get(0).x == apple.x && snake.get(0).y == apple.y) {
            // add score
            score += 100;
            updateScore();

            // add snake
            snake.add(new Position(0, 0));

            // relocate apple
            apple.x = random.nextInt((int) WIDTH/TILE_SIZE) * TILE_SIZE;
            apple.y = random.nextInt((int) HEIGHT/TILE_SIZE) * TILE_SIZE;
            
        }

        // check border collision
        if(snake.get(0).x < 0) {
            gameOver();
        }
        else if(snake.get(0).x > WIDTH) {
            gameOver();
        }
        else if(snake.get(0).y < 0) {
            gameOver();
        }
        else if(snake.get(0).y > HEIGHT) {
            gameOver();
        }

        // check snake collison
        for(int i=snake.size()-1; i>0; i--) {
            if(snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver();
            }
        }

        move();
        repaint();
        
    }

    
    public void move() {

        // update body
        for(int i=snake.size()-1; i>0; i--) {
            snake.get(i).x = snake.get(i-1).x;
            snake.get(i).y = snake.get(i-1).y;
        }

        // update head
        if(direction == Direction.up) {
            snake.get(0).y -= TILE_SIZE;
        }
        else if(direction == Direction.down) {
            snake.get(0).y += TILE_SIZE;
        }
        else if(direction == Direction.left) {
            snake.get(0).x -= TILE_SIZE;
        }
        else if(direction == Direction.right) {
            snake.get(0).x += TILE_SIZE;
        }
        
    }

    public void gameOver() {
    	
        timer.stop();

        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setBackground(Color.black);

        gameOverLabel = new JLabel("Game Over");
        gameOverLabel.setForeground(Color.white);
        gameOverPanel.add(gameOverLabel);

        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setForeground(Color.white);
        gameOverPanel.add(scoreLabel);

        playAgainButton = new JButton("Play again");
        playAgainButton.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(frame);
                frame.add(game);
                frame.remove(gameOverPanel);
                frame.invalidate();
                frame.validate();
                frame.repaint();
            }
        });
        
        gameOverPanel.add(playAgainButton);
        frame.remove(this);
        frame.add(gameOverPanel);
        frame.invalidate();
        frame.validate();
        frame.repaint();

    }

    public void updateScore() {
        frame.setTitle("Snake - Score: " + score);
    }
    
    @Override
    public void keyPressed(KeyEvent e){

        if(gameHasStarted == false) {
            start();
        }

        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP && direction != Direction.down) {
            direction = Direction.up;
        }
        else if(code == KeyEvent.VK_DOWN && direction != Direction.up) {
            direction = Direction.down;
        }
        else if(code == KeyEvent.VK_RIGHT && direction != Direction.left) {
            direction = Direction.right;
        }
        else if(code == KeyEvent.VK_LEFT && direction != Direction.right) {
            direction = Direction.left;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public void start() {
    	
        timer.start();
        gameHasStarted = true;
        updateScore();
        
    }
}