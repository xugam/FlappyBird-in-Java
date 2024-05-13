import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int borderwidth = 700;
    int borderheight = 640;
    
    //Images variables
    Image backgroundImage;
    Image birdImage;
    Image toppipeImage;
    Image bottompipImage;

    //Bird 
    int birdX=borderwidth/8;
    int birdY=borderheight/2;
    int birdwidth =34;
    int birdheight =24;

    //Pipe
    int pipeX = borderwidth;
    int pipeY = 0;
    int pipewidth = 64;
    int pipeheight = 512;

    class Bird{
        int x = birdX;
        int y = birdY;
        int width = birdwidth;
        int height = birdheight;
        Image img;

        Bird (Image img){
            this.img = img;
        }
    }

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipewidth;
        int height = pipeheight;
        boolean passed = false;
        Image img;

        Pipe(Image img){
            this.img = img;
        }
    }

    //Bird class ko euta obj banako gonna use it in constructor
    Bird bird;
    //game logic
    
    int velocityX = -4;//for moving pipe to left
    int velocityY = 0;//for storing keypressed by user
    int gravity = 1;

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop;
    Timer placepipeTimer;

    boolean gameOver = false;
    double score=0;

    //drawing the canvas
    FlappyBird()
    {
        setPreferredSize(new Dimension(borderwidth,borderheight));
        setFocusable(true);
        addKeyListener(this);

        //load the images
        backgroundImage = new ImageIcon(getClass().getResource("./flappybirdbg1.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        toppipeImage = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottompipImage = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
        setBackground(Color.CYAN);

        //bird
        bird =new Bird(birdImage);
        pipes = new ArrayList<Pipe>();

        //place pipe timer
         placepipeTimer = new Timer(1500,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                    placePipes();
            }
        } );   

        placepipeTimer.start();
       
        //game timer    
        gameLoop = new Timer(1000/60,this);
        gameLoop.start();

    }

    public void placePipes(){
        int randompipeY = (int ) (pipeY - pipeheight/4 - Math.random()*(pipeheight/2));
        int openingSpace = borderheight/4;

        Pipe topPipe = new Pipe(toppipeImage);
        topPipe.y = randompipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottompipImage);
        bottomPipe.y = topPipe.y + pipeheight + openingSpace ;
        pipes.add(bottomPipe);

    }

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g)
    {
        //for background
        g.drawImage(backgroundImage,0,0,borderwidth,borderheight,null);
        //for bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height,null);
        //for pipe
        for(int i=0;i<pipes.size();i++)
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img,pipe.x,pipe.y,pipe.width,pipe.height,null);
        }
        
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Times New Roman",Font.PLAIN, 40));
        if(gameOver){
            g.drawString("Game Over: Your Score was "+ ( String.valueOf((int)score)),10,35);
            g.drawString("Press 'Enter' to restart the game",10,65);
        }
        else{
            g.drawString("Score: "+ ( String.valueOf((int)score)),10,35);
        }

    }
    public void move()
    {
        //bird
        velocityY+=gravity;
        bird.y+=velocityY;
        bird.y = Math.max (bird.y,0);

        //pipes
        for(int i=0;i<pipes.size();i++){

            Pipe pipe = pipes.get(i);
            pipe.x+= velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                    pipe.passed = true;
                    score += 0.5; //since 2 pipes
            }

            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        if(bird.y > borderheight){
            gameOver = true;
        }
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&   //a's top left corner doesn't reach b's top right corner
               a.x + a.width > b.x &&   //a's top right corner passes b's top left corner
               a.y < b.y + b.height &&  //a's top left corner doesn't reach b's bottom left corner
               a.y + a.height > b.y;    //a's bottom left corner passes b's top left corner
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
       repaint();
       if(gameOver){
            placepipeTimer.stop();
            gameLoop.stop();
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            velocityY=-10;
        }
       
        if(gameOver){
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
            //restart game by resetting all value
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            score = 0;
            gameOver = false;
            gameLoop.start();
            placepipeTimer.start();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
