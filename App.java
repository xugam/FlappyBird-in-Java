//Flappy Bird May 11th
import javax.swing.*;
public class App {

    public static void main(String[] args) throws Exception {
        int borderwidth = 700;
        int borderheight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        
        frame.setSize(borderwidth, borderheight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}