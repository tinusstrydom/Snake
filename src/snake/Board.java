package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Board class (Extends JPanel )
public class Board extends JPanel implements Runnable, ActionListener{
	
	//Set size for the board width and height
	private final int BRDWIDTH = 300;
	private final int BRDHEIGHT = 300;
	
	//Set the size of the snake body dot
	private final int DOT_SIZE = 10;
		
	//Set the amount of dots on the board((300*300)/(10*10))
	private final int ALL_DOTS = 900;
			
	//Set a rand position for the apple
	private final int RAND_POS = 50;
			
	//Set the speed of the game
	private final int DELAY = 140;
	
	//Create 2 arrays for coordinates of all joints (x and y)
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
		
	//Set boolean values
	//Stop snake from going back on itself
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = true;
	
	private boolean inGame = true; 
		
	private int dots, apple_x, apple_y;
	private Image head, dot, apple;
	
	private Thread animator;
	
	//Constructor Method
	public Board() {
		initBoard();
	}
	
	public void initBoard() {
		//add key listener
		addKeyListener(new TAdapter());
		//set background to black
		setBackground(Color.lightGray);
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
		ImageIcon headImg = new ImageIcon("src/img/head.png");
		head = headImg.getImage();
		ImageIcon dotImg = new ImageIcon("src/img/dot.png");
		dot =  dotImg.getImage();
		ImageIcon appleImg = new ImageIcon("src/img/apple.png");
		apple = appleImg.getImage();
	}

	//Set initial game settings
	private void initGame() {
		dots = 3;
		for(int i = 0; i < dots; i++) {
			x[i] = 50 - i * 10;
			y[i] = 50;
		}
		randomApple();
	}
	//called after jpanel has been added to jframe
	//used for various initialisation tasks
	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	//paint image to panel
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawSnake(g);
	}
	//draw snake method draw random apple
	//
	private void drawSnake(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if(inGame) {
			g2d.drawImage(apple, apple_x, apple_y, this);
			for (int i = 0; i < dots; i++) {
	             if (i == 0) {
	            	 g2d.drawImage(head, x[i], y[i], this);   
	             }else {
	                 g2d.drawImage(dot, x[i], y[i], this);
	             }
			 }
			Toolkit.getDefaultToolkit().sync();
		}else {
			gameOver(g);
		}
	}
	private void randomApple() {
		int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
	}
	//check apple checks id head has collide with apple 
	//if true add dot to snake and call random apple
	private void checkApple(){
		if ((x[0] == apple_x) && (y[0] == apple_y)) {
	        dots++;
	        randomApple();
	    }
	}
	private void checkCollision() {
        for (int i = dots; i > 0; i--) {
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
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
           
        }
    }
	//Game over method
	private void gameOver(Graphics g) {
        
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (BRDWIDTH - metr.stringWidth(msg)) / 2, BRDHEIGHT / 2);
    }
	private void cycle() {
		if(inGame) {
			checkApple();
			checkCollision();
			move();
		}
	}
	private void move() {
		for(int i = dots; i > 0 ; i--) {
			x[i] = x[(i-1)];
			y[i] = y[(i-1)];
		}
		if(left) {
			x[0] -= DOT_SIZE;
		}
		if(right) {
			x[0] += DOT_SIZE;
		}
		if(up) {
			y[0] -= DOT_SIZE;
		}
		if(down) {
			y[0] += DOT_SIZE;
		}
	}
	//execute in specific intervals, 
	//run called once reason for while loop in run method
	//from here we call cycle and repaint
	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		
		beforeTime = System.currentTimeMillis();
		
		while(true) {
			cycle();
			repaint();
			//compute system time for constant speed(game run smoothly)
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			if(sleep < 0) {
				sleep = 1;
			}
			try {
				Thread.sleep(sleep);
			}catch (InterruptedException e){
				String msg = String.format("Thread Interrupted: %s",e.getMessage());
				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}
			beforeTime = System.currentTimeMillis();
		}
	}
	
	private class TAdapter extends KeyAdapter{
		//Key pressed 
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();  
			
			if((key == KeyEvent.VK_LEFT) && (!right)) {
				left = true;
				up = false;
				down = false;
			}
			if((key == KeyEvent.VK_RIGHT) && (!left)) {
				right = true;
				up = false;
				down = false;
			}
			if((key == KeyEvent.VK_UP) && (!down)) {
				up = true;
				left = false;
				right = false;
			}
			if((key == KeyEvent.VK_DOWN) && (!up)){
				down = true;
				left = false;
				right = false;
			}
		}
	}

}
