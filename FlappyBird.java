import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class FlappyBird extends JPanel {
    int borderwidth = 360;
    int borderheight = 640;
    
    //Images variables
    Image backgroundImage;
    Image birdImage;
    Image toppipeImage;
    Image bottompipImage;

    //drawing the canvas
    FlappyBird()
    {
        setPreferredSize(new Dimension(borderwidth,borderheight));

        //load the images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        setBackground(Color.CYAN);
    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        g.drawImage(backgroundImage,0,0,borderwidth,borderheight,null);

    }
}
