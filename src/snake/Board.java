package snake;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//Board class (Extends JPanel )
public class Board extends JPanel {
	//Constructor Method
	public Board() {
		initBoard();
		
	}
	public void initBoard() {
		setBackground(Color.red);
	}
	/*private void loadImage() {
		ImageIcon headimg = new ImageIcon(src/img/head.png);
	}*/
	
}
