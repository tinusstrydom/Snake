package snake;

import java.awt.EventQueue;

//Imports
import javax.swing.JFrame;

public class Main extends JFrame{
	public Board board = new Board();
	
	//Constructor Method
	public Main() {
		initUI();
	}
	public void initUI() {
		//add board to the initial UI
		add(board);
		//set title for frame
		setTitle("Snake");
		//set size for frame
		setSize(300, 300);
		//set default for close button of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set location on screen(null centers it)
		setLocationRelativeTo(null);
	}
	//Main Method
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			Main main = new Main();
			main.setVisible(true);
		});

	}
}
