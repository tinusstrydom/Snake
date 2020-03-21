package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Board class (Extends JPanel )
public class Board extends JPanel implements Runnable, ActionListener{
	//Constants 
	private Snake snake;
	private final int DELAY = 25;
	
	private Image head;
	private Thread animator;
	private int x, y;
	
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
	public void paint(Graphics g) {
		super.paint(g);
		drawHead(g);
		Toolkit.getDefaultToolkit().sync();
	}
	private void drawHead(Graphics g) {
		g.drawImage(snake.getImage(), snake.getX(), snake.getY(), this);
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
			repaint(snake.getX()-1, snake.getY()-1,snake.getWidth()+2, snake.getHeight()+2);
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
