package snake;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Snake {
	private int dx, dy, w, h;
	private int x = 40;
	private int y = 60;
	private Image head;
	
	public Snake() {
		loadImg();
	}
	
	//load image
	private void loadImg() {
		//load and get image
		ImageIcon headImg = new ImageIcon("src/img/head.png");
		head = headImg.getImage();
		
		//Get width and height of image
		w = head.getWidth(null);
		h = head.getWidth(null);
	}
	public void move(){
		//set x and y to dx and dy  
		x += dx;
		y += dy;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	public Image getImage() {
		return head;
	}
	//Key pressed and key release
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();  
		if(key == KeyEvent.VK_LEFT) {
			dx = -2;
		}
		if(key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}
		if(key == KeyEvent.VK_UP) {
			dy = -2;
		}
		if(key == KeyEvent.VK_DOWN) {
			dy = 2;
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();  
		if(key == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		if(key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
		if(key == KeyEvent.VK_UP) {
			dy = 0;
		}
		if(key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
}
