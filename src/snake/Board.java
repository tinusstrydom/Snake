package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//Board class (Extends JPanel )
public class Board extends JPanel implements Runnable{
	//Constants 
	//Width and height of panel and init spot for x and y
	private final int BRDWIDTH = 300;
	private final int BRDHEIGHT = 300;
	private final int INIT_X = 0;
	private final int INIT_Y = 0;
	//delay for the speed 
	private final int DELAY = 140;
	
	private Image head;
	private Thread animator;
	private int x, y;
	
	//Constructor Method
	public Board() {
		initBoard();
		
	}
	public void initBoard() {
		//set background to black
		setBackground(Color.lightGray);
		//Set the preferred size
		setPreferredSize(new Dimension(BRDWIDTH, BRDHEIGHT));
		loadImg();
		x = INIT_X;
		y = INIT_Y;
	}
	//load image
	private void loadImg() {
		ImageIcon headImg = new ImageIcon("src/img/apple.png");
		head = headImg.getImage();
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
	}
	private void drawHead(Graphics g) {
		g.drawImage(head, x, y, this);
		Toolkit.getDefaultToolkit().sync();
	}
	private void cycle() {
		x += 1;
		y += 1;
		if(y > BRDHEIGHT) {
			y = INIT_Y;
			x = INIT_X;
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
				sleep = 2;
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
}
