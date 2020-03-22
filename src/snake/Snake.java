package snake;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Snake {

	private int dx, dy, w, h;
	private int xApple = 30;
	private int yApple = 50;
	private int xDot = 20;
	private int yDot = 40;
	private Image head, dot, apple;
	
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getXA() {
		return xApple;
	}
	public int getYA() {
		return yApple;
	}
	public int getXD() {
		return xDot;
	}
	public int getYD() {
		return yDot;
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
	
}
