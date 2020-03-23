package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

//Board class (Extends JPanel )
public class Board extends JPanel implements ActionListener {
	//Set size for the board width and height
    private final int BRDWIDTH = 300;
    private final int BRDHEIGHT = 300;
    
    //Set the size of the snake body dot
    private final int DOT_SIZE = 10;
    
    //Set the amount of dots on the board((300*300)/(10*10))
    private final int ALL_DOTS = 900;
    
    //Set a rand position for the apple
    private final int RAND_POS = 30;
    
    //Set the speed of the game
    private final int DELAY = 100;

    //Create 2 arrays for coordinates of all joints (x and y)
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    //Set boolean values, directions
  	//Stop snake from going back on itself
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;
    
    private int dots, apple_x, apple_y, score;
    private Image dot, apple, head;
    private Timer timer;

    //Constructor Method
    public Board() {
        initBoard();
    }
    //initially board
    private void initBoard() {
    	//add key listener
        addKeyListener(new TAdapter());
        //set background todark gray
        setBackground(Color.DARK_GRAY);
        //Set the focusable state of component
        setFocusable(true);
        //Set the board dimensions
        setPreferredSize(new Dimension(BRDWIDTH, BRDHEIGHT));
        //Call load img method
        loadImg();
        //Call init game method
        initGame();
    }
    //load image
    private void loadImg() {
    	//load and get image
        ImageIcon dotImg = new ImageIcon("src/img/dot.png");
        dot = dotImg.getImage();
        ImageIcon appleImg = new ImageIcon("src/img/apple.png");
        apple = appleImg.getImage();
        ImageIcon headImg = new ImageIcon("src/img/head.png");
        head = headImg.getImage();
    }

  	//Set initial game settings
    private void initGame() {
    	//body of snake 3
  		dots = 3;
  		//position the body of snake behind each other initially
  		for(int i = 0; i < dots; i++) {
  			x[i] = 50 - i * 10;
  			y[i] = 50;
  		}
  		//call random apple
  		randomApple();
  		//start the timer class
  		timer = new Timer(DELAY, this);
  		timer.start();
    }
    //paint image to panel
  	@Override
  	public void paintComponent(Graphics g) {
  		super.paintComponent(g);
  		drawSnake(g);
  	}
  	//draw snake method draw random apple
  	private void drawSnake(Graphics g) {
  		if(inGame) {
  			//draw image with coordinates from random apple
  			g.drawImage(apple, apple_x, apple_y, this);
  			//for loop
  			for (int i = 0; i < dots; i++) {
  	             if (i == 0) {
  	            	 g.drawImage(head, x[i], y[i], this);   
  	             }else {
  	                 g.drawImage(dot, x[i], y[i], this);
  	             }
  			 }
  			Toolkit.getDefaultToolkit().sync();
  		}else {
  			//call game over 
  			gameOver(g);
  		}
  	}
  	//Game over graphics and final score
    private void gameOver(Graphics g) {
        String msg = "Game Over!";
        String msgScore = "Your Score: "+score;
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);
        //set color of text
        g.setColor(Color.white);
        //set font to small
        g.setFont(small);
        //draw string message in the center of the screen
        g.drawString(msg, (BRDWIDTH - metr.stringWidth(msg)) / 2, BRDHEIGHT / 2);
        g.drawString(msgScore, (BRDWIDTH - metr.stringWidth(msg)) / 2, BRDHEIGHT / 3);
    }
    //check apple checks if head has collide with apple 
  	//if true add dot to snake and call random apple
    private void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            score += 10;
            randomApple();
        }
    }

    private void move() {
        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        //
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    private void checkCollision() {
        for (int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }
        if (y[0] >= BRDHEIGHT) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (x[0] >= BRDWIDTH) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (!inGame) {
            timer.stop();
        }
    }
    //random apple 
    private void randomApple() {
    	//assign to r the value of random number times the random position number 
    	//assign to apple_x and apple_y the value of the r times the dot size (to make number bigger)
        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));
        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    //TAdapter class extends the KeyAdapter class 
    private class TAdapter extends KeyAdapter {
        @Override
        //used the keyPressed event 
        public void keyPressed(KeyEvent e) {
        	//assign to key the key pressed
            int key = e.getKeyCode();
            //If statements works if the key pressed && opposite direction from what it is travelling in 
            //can't Double back ,should be game over so to avoid it we use the !(not) variable 
            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }
            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }
            if ((key == KeyEvent.VK_UP) && (!down)) {
                up= true;
                right = false;
                left = false;
            }
            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down= true;
                right = false;
                left = false;
            }
        }
    }
}
