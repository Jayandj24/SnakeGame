import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {

        public static void main(String[] args) {
                JFrame frame = new JFrame();
                frame.setSize(426, 449);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);

                Game game = new Game(frame);
                frame.add(game);

                frame.setVisible(true);
        }
}
