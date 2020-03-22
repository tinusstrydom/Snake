package snake;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Snake {
	private int dx, dy, w, h;
	private int x = 40;
	private int y = 60;
	private int xA = 30;
	private int yA = 50;
	private int xD = 20;
	private int yD = 40;
	private Image head, dot, apple;
	
	public Snake() {
		loadImg();
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
	public int getXA() {
		return xA;
	}
	public int getYA() {
		return yA;
	}
	public int getXD() {
		return xD;
	}
	public int getYD() {
		return yD;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	public Image getHead() {
		return head;
	}
	public Image getApple() {
		return apple;
	}
	public Image getDot() {
		return dot;
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
