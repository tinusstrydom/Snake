package snake;

import java.awt.Color;
import java.awt.Dimension;
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
	//Variable and Constants 
	//Set variable for the Snake class
	private Snake snake;
	
	//Set size for the board width and height
	private final int BRDWIDTH = 300;
	private final int BRDHEIGHT = 300;
	
	//Set the size of the snake body dot
	private final int DOT = 10;
	
	//Set the amount of dots on the board((300*300)/(10*10))
	private final int ALL_DOTS = 900;
	
	//Set a rand position for the apple
	private final int RAND_POS = 29;
	
	//Set the speed of the game
	private final int DELAY = 120;
	
	//Create 2 arrays for coordinates of all joints (x and y)
	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];
	
	private Image head;
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
		//Set the preferred size
		snake = new Snake();
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
		drawHead(g);
		drawApple(g);
		drawDot(g);
		Toolkit.getDefaultToolkit().sync();
	}
	private void drawHead(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(snake.getHead(), snake.getX(), snake.getY(), this);
	}
	private void drawApple(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(snake.getApple(), snake.getXA(), snake.getYA(), this);
	}
	private void drawDot(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(snake.getDot(), snake.getXD(), snake.getYD(), this);
	}
	private void cycle() {
		step();
	}
	private void step() {
		snake.move();	
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
		@Override
		public void keyReleased(KeyEvent e) {
			snake.keyReleased(e);
		}
		@Override
		public void keyPressed(KeyEvent e) {
			snake.keyPressed(e);
		}
	}

}
